package com.jk.activiti.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jk.activiti.config.BusinessType;
import com.jk.activiti.config.Log;
import com.jk.activiti.domain.AjaxResult;
import com.jk.activiti.domain.ProcessDefinition;
import com.jk.activiti.service.impl.ProcessDefinitionService;
import com.jk.activiti.utils.Constants;
import com.jk.activiti.utils.ExcelUtil;
import com.jk.activiti.utils.FileUploadUtils;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jk.activiti.domain.AjaxResult.error;
import static com.jk.activiti.domain.AjaxResult.success;


@Controller
@Component
public class ProcessDefinitionController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ProcessDefinitionController.class);

    private String prefix = "definition";

    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @Autowired
    private RepositoryService repositoryService;

    @Value("${web.path:#{null}}")
    private String uploadPath;

    @GetMapping("definition/list")
    public String processDefinition() {
        return prefix + "/list";
    }

    @PostMapping("definition/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {

        IPage page = processDefinitionService.listProcessDefinition(para);
        return DataTable.ok(page);
    }

    /**
     * 部署流程定义
     */
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping("definition/upload")
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String extensionName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
                if (!"bpmn".equalsIgnoreCase(extensionName)
                        && !"zip".equalsIgnoreCase(extensionName)
                        && !"bar".equalsIgnoreCase(extensionName)) {
                    return error("流程定义文件仅支持 bpmn, zip 和 bar 格式！");
                }
                // p.s. 此时 FileUploadUtils.upload() 返回字符串 fileName 前缀为 Constants.RESOURCE_PREFIX，需剔除
                // 详见: FileUploadUtils.getPathFileName(...)
                String fileName = FileUploadUtils.upload(uploadPath + "/processDefiniton", file);
                if (StringUtils.isNotBlank(fileName)) {
                    String realFilePath = uploadPath + fileName.substring(Constants.RESOURCE_PREFIX.length());
                    processDefinitionService.deployProcessDefinition(realFilePath);
                    return success();
                }
            }
            return error("不允许上传空文件！");
        }
        catch (Exception e) {
            log.error("上传流程定义文件失败！", e);
            return error(e.getMessage());
        }
    }

    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @PostMapping("definition/delete")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
           int row=  processDefinitionService.deleteProcessDeploymentByIds(ids);
           if(row>0){
               return success();
           }else{
               return error();
           }
        }
        catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("definition/export")
    @ResponseBody
    public AjaxResult export() {
        List<ProcessDefinition> list = processDefinitionService.getProcessDefinition(new HashMap());
        ExcelUtil<ProcessDefinition> util = new ExcelUtil<>(ProcessDefinition.class);
        return util.exportExcel(list, "流程定义数据");
    }

    @PostMapping( "definition/suspendOrActiveApply")
    @ResponseBody
    public AjaxResult suspendOrActiveApply(String id, String suspendState) {
        processDefinitionService.suspendOrActiveApply(id, suspendState);
        return success();
    }

    /**
     * 读取流程资源
     *
     * @param processDefinitionId 流程定义ID
     * @param resourceName        资源名称
     */
    @RequestMapping(value = "definition/readResource")
    public void readResource(@RequestParam("pdid") String processDefinitionId, @RequestParam("resourceName") String resourceName, HttpServletResponse response)
            throws Exception {
        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
        org.activiti.engine.repository.ProcessDefinition pd = pdq.processDefinitionId(processDefinitionId).singleResult();

        // 通过接口读取
        InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 转换流程定义为模型
     * @param processDefinitionId
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @PostMapping(value = "definition/convert2Model")
    @ResponseBody
    public AjaxResult convertToModel(@Param("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return success();
    }

}
