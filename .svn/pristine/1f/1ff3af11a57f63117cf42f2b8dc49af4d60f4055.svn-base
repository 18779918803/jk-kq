package com.jk.activiti.modeler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jk.activiti.config.BusinessType;
import com.jk.activiti.config.Log;
import com.jk.activiti.controller.BaseController;
import com.jk.activiti.domain.AjaxResult;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.annotation.KrtLog;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.common.bean.ReturnBean;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

//import com.github.pagehelper.Page;
//import com.ruoyi.common.annotation.Log;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.common.core.page.PageDomain;
//import com.ruoyi.common.core.page.TableDataInfo;
//import com.ruoyi.common.core.page.TableSupport;
//import com.ruoyi.common.enums.BusinessType;

@Controller
public class ModelerController extends BaseController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ModelEditorJsonRestResource.class);
    private static final String PREFIX = "modeler";

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("modeler/modelList")
    public String modelList(HttpServletRequest request) {
        return PREFIX + "/list";
    }


    @PostMapping("modeler/modelList")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();

        // 条件过滤
        if (para.get("key")!=null&&StringUtils.isNotBlank(para.get("key")+"")) {
            modelQuery.modelKey(para.get("key")+"");
        }
        if (para.get("name")!=null&&StringUtils.isNotBlank(para.get("name")+"")) {
            modelQuery.modelNameLike("%" +para.get("name") + "%");
        }

        Query query = new Query(para);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page page = query.getPage();
        PageHelper.startPage(page);
        List<Model> resultList = modelQuery.list();
        page.setRecords(resultList);
        return DataTable.ok(page);
    }

    @GetMapping("modeler/addModal")
    public String addModal() {
        return PREFIX + "/insert";
    }

    /**
     * 创建模型
     */
    @RequestMapping(value = "modeler/create")
    @ResponseBody
    public AjaxResult create(@RequestParam("name") String name, @RequestParam("key") String key,
                             @RequestParam(value = "description", required = false) String description) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(MODEL_DESCRIPTION, description);

            Model newModel = repositoryService.newModel();
            newModel.setMetaInfo(modelObjectNode.toString());
            newModel.setName(name);
            newModel.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(newModel);
            repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));

            return  new  AjaxResult(AjaxResult.Type.SUCCESS, "创建模型成功", newModel.getId());
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
        }
        return error();
    }

    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "modeler/deploy/{modelId}")
    @ResponseBody
    public AjaxResult deploy(@PathVariable("modelId") String modelId, RedirectAttributes redirectAttributes) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            LOGGER.info("部署成功，部署ID=" + deployment.getId());
            return success("部署成功");
        } catch (Exception e) {
            LOGGER.error("根据模型部署流程失败：modelId={}", modelId, e);

        }
        return error("部署失败");
    }

    /**
     * 导出model的xml文件
     */
    @RequestMapping(value = "modeler/export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 流程非空判断
            if (!CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

                ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
                String filename = bpmnModel.getMainProcess().getId() + ".bpmn";
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                IOUtils.copy(in, response.getOutputStream());
                response.flushBuffer();
            } else {
                try {
                    response.sendRedirect("/modeler/modelList");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            LOGGER.error("导出model的xml文件失败：modelId={}", modelId, e);
            try {
                response.sendRedirect("/modeler/modelList");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    @Log(title = "流程模型", businessType = BusinessType.DELETE)
//    @PostMapping("modeler/remove")
//    @ResponseBody
//    public AjaxResult remove(String ids) {
//        try {
//            repositoryService.deleteModel(ids);
//            return toAjax(true);
//        }
//        catch (Exception e) {
//            return error(e.getMessage());
//        }
//    }


    /**
     * 删除组织
     *
     * @param id 组织id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除组织")
    @RequiresPermissions("model:model:delete")
    @PostMapping("modeler/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        try {
            repositoryService.deleteModel(id+"");
             return ReturnBean.ok();
        }
        catch (Exception e) {
            return ReturnBean.error(e.getMessage());
        }
    }

    /**
     * 批量删除组织
     *
     * @param ids 组织ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除组织")
    @RequiresPermissions("model:model:delete")
    @PostMapping("modeler/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        for(Integer id:ids){
            try {
                repositoryService.deleteModel(id+"");

            }
            catch (Exception e) {
                return ReturnBean.error(e.getMessage());
            }
        }
        return ReturnBean.ok();
    }

}
