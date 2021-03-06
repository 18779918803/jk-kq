package com.jk.kq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.config.WorkTypeConfig;
import com.jk.kq.entity.WorkType;
import com.jk.kq.entity.WriteoffRecord;
import com.jk.kq.service.IWorkTypeService;
import com.jk.kq.service.IWriteoffRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.ModelMap;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;

/**
 * 核销申请控制层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Controller
public class WriteoffRecordController extends BaseController {

    @Autowired
    private IWriteoffRecordService writeoffRecordService;

    @Autowired
    private IWorkTypeService workTypeService;

    /**
     * 核销申请管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("WriteoffRecord:writeoffRecord:list")
    @GetMapping("kq/writeoffRecord/list")
    public String list() {
        List<WorkType> writeoffTypes = workTypeService.selectByPid(WorkTypeConfig.WRITEOFF);
        request.setAttribute("writeoffTypes",writeoffTypes);
        return "kq/writeoffRecord/list";
    }

    /**
     * 核销申请管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("WriteoffRecord:writeoffRecord:list")
    @PostMapping("kq/writeoffRecord/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = writeoffRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增核销申请页
     *
     * @return {@link String}
     */
    @RequiresPermissions("WriteoffRecord:writeoffRecord:insert")
    @GetMapping("kq/writeoffRecord/insert")
    public String insert() {
        List<WorkType> writeoffTypes = workTypeService.selectByPid(WorkTypeConfig.WRITEOFF);
        request.setAttribute("writeoffTypes",writeoffTypes);
        return "kq/writeoffRecord/insert";
    }

    /**
     * 添加核销申请
     *
     * @param writeoffRecord 核销申请
     * @return {@link ReturnBean}
     */
    @KrtLog("添加核销申请")
    @RequiresPermissions("WriteoffRecord:writeoffRecord:insert")
    @PostMapping("kq/writeoffRecord/insert")
    @ResponseBody
    public ReturnBean insert(WriteoffRecord writeoffRecord) {
        writeoffRecordService.insert(writeoffRecord);
        return ReturnBean.ok();
    }

    /**
     * 修改核销申请页
     *
     * @param id 核销申请id
     * @return {@link String}
     */
    @RequiresPermissions("WriteoffRecord:writeoffRecord:update")
    @GetMapping("kq/writeoffRecord/update")
    public String update(Integer id) {
        WriteoffRecord writeoffRecord = writeoffRecordService.selectById(id);
        request.setAttribute("writeoffRecord", writeoffRecord);
        List<WorkType> writeoffTypes = workTypeService.selectByPid(WorkTypeConfig.WRITEOFF);
        request.setAttribute("writeoffTypes",writeoffTypes);
        return "kq/writeoffRecord/update";
    }

    /**
     * 修改核销申请
     *
     * @param writeoffRecord 核销申请
     * @return {@link ReturnBean}
     */
    @KrtLog("修改核销申请")
    @RequiresPermissions("WriteoffRecord:writeoffRecord:update")
    @PostMapping("kq/writeoffRecord/update")
    @ResponseBody
    public ReturnBean update(WriteoffRecord writeoffRecord) {
        writeoffRecordService.updateById(writeoffRecord);
        return ReturnBean.ok();
    }

    /**
     * 删除核销申请
     *
     * @param id 核销申请id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除核销申请")
    @RequiresPermissions("WriteoffRecord:writeoffRecord:delete")
    @PostMapping("kq/writeoffRecord/delete")
    @ResponseBody
    public ReturnBean delete(Integer id)   {
        writeoffRecordService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除核销申请
     *
     * @param ids 核销申请ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除核销申请")
    @RequiresPermissions("WriteoffRecord:writeoffRecord:delete")
    @PostMapping("kq/writeoffRecord/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids)   {
        writeoffRecordService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


        /**
        * 导出核销申请
        *
        * @param modelMap 返回模型
        * @param para 参数
        */
        @KrtLog("导出核销申请")
        @RequiresPermissions("WriteoffRecord:writeoffRecord:excelOut")
        @GetMapping("kq/writeoffRecord/excelOut")
        public String excelOut(ModelMap modelMap,Map para) {
            List<ExcelExportEntity> entityList = new ArrayList<>();
                entityList.add(new ExcelExportEntity("ID", "id", 15));
                entityList.add(new ExcelExportEntity("open_user_id", "openUserId", 15));
                entityList.add(new ExcelExportEntity("姓名", "name", 15));
                entityList.add(new ExcelExportEntity("公司ID/部门ID", "organId", 15));
                entityList.add(new ExcelExportEntity("公司/部门", "organ", 15));
                entityList.add(new ExcelExportEntity("核销日期", "date", 15));
                entityList.add(new ExcelExportEntity("核销原因", "reason", 15));
                entityList.add(new ExcelExportEntity("申请状态", "state", 15));
                entityList.add(new ExcelExportEntity("核销类型", "typeId", 15));
                entityList.add(new ExcelExportEntity("申请提交时间", "insertTime", 15));
            List dataResult = writeoffRecordService.selectExcelList(para);
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
            modelMap.put(MapExcelConstants.FILE_NAME, "核销申请");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("核销申请", "核销申请"));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
        }
}
