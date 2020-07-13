package com.jk.kq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.config.WorkTypeConfig;
import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.entity.WorkType;
import com.jk.kq.service.ILeaveRecordService;
import com.jk.kq.service.IWorkTypeService;
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
 * 请假申请控制层
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Controller
public class LeaveRecordController extends BaseController {

    @Autowired
    private ILeaveRecordService leaveRecordService;

    @Autowired
    private IWorkTypeService workTypeService;

    /**
     * 请假申请管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("LeaveRecord:leaveRecord:list")
    @GetMapping("kq/leaveRecord/list")
    public String list() {
        List<WorkType> leaveTypes = workTypeService.selectByPid(WorkTypeConfig.LEAVE);
        request.setAttribute("leaveTypes",leaveTypes);
        return "kq/leaveRecord/list";
    }

    /**
     * 请假申请管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("LeaveRecord:leaveRecord:list")
    @PostMapping("kq/leaveRecord/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = leaveRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增请假申请页
     *
     * @return {@link String}
     */
    @RequiresPermissions("LeaveRecord:leaveRecord:insert")
    @GetMapping("kq/leaveRecord/insert")
    public String insert() {
        List<WorkType> leaveTypes = workTypeService.selectByPid(WorkTypeConfig.LEAVE);
        request.setAttribute("leaveTypes",leaveTypes);
        return "kq/leaveRecord/insert";
    }

    /**
     * 添加请假申请
     *
     * @param leaveRecord 请假申请
     * @return {@link ReturnBean}
     */
    @KrtLog("添加请假申请")
    @RequiresPermissions("LeaveRecord:leaveRecord:insert")
    @PostMapping("kq/leaveRecord/insert")
    @ResponseBody
    public ReturnBean insert(LeaveRecord leaveRecord) {
        leaveRecordService.insert(leaveRecord);
        return ReturnBean.ok();
    }

    /**
     * 修改请假申请页
     *
     * @param id 请假申请id
     * @return {@link String}
     */
    @RequiresPermissions("LeaveRecord:leaveRecord:update")
    @GetMapping("kq/leaveRecord/update")
    public String update(Integer id) {
        LeaveRecord leaveRecord = leaveRecordService.selectById(id);
        request.setAttribute("leaveRecord", leaveRecord);
        List<WorkType> leaveTypes = workTypeService.selectByPid(WorkTypeConfig.LEAVE);
        request.setAttribute("leaveTypes",leaveTypes);
        return "kq/leaveRecord/update";
    }

    /**
     * 修改请假申请
     *
     * @param leaveRecord 请假申请
     * @return {@link ReturnBean}
     */
    @KrtLog("修改请假申请")
    @RequiresPermissions("LeaveRecord:leaveRecord:update")
    @PostMapping("kq/leaveRecord/update")
    @ResponseBody
    public ReturnBean update(LeaveRecord leaveRecord) {
        leaveRecordService.updateById(leaveRecord);
        return ReturnBean.ok();
    }

    /**
     * 删除请假申请
     *
     * @param id 请假申请id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除请假申请")
    @RequiresPermissions("LeaveRecord:leaveRecord:delete")
    @PostMapping("kq/leaveRecord/delete")
    @ResponseBody
    public ReturnBean delete(Integer id)   {
        leaveRecordService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除请假申请
     *
     * @param ids 请假申请ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除请假申请")
    @RequiresPermissions("LeaveRecord:leaveRecord:delete")
    @PostMapping("kq/leaveRecord/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids)   {
        leaveRecordService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


        /**
        * 导出请假申请
        *
        * @param modelMap 返回模型
        * @param para 参数
        */
        @KrtLog("导出请假申请")
        @RequiresPermissions("LeaveRecord:leaveRecord:excelOut")
        @GetMapping("kq/leaveRecord/excelOut")
        public String excelOut(ModelMap modelMap,Map para) {
            List<ExcelExportEntity> entityList = new ArrayList<>();
                entityList.add(new ExcelExportEntity("ID", "id", 15));
                entityList.add(new ExcelExportEntity("open_user_id", "openUserId", 15));
                entityList.add(new ExcelExportEntity("姓名", "name", 15));
                entityList.add(new ExcelExportEntity("公司ID/部门ID", "organId", 15));
                entityList.add(new ExcelExportEntity("公司/部门", "organ", 15));
                entityList.add(new ExcelExportEntity("请假日期", "date", 15));
                entityList.add(new ExcelExportEntity("请假原因", "reason", 15));
                entityList.add(new ExcelExportEntity("请假天数", "day", 15));
                entityList.add(new ExcelExportEntity("申请状态", "state", 15));
                entityList.add(new ExcelExportEntity("请假类型", "typeId", 15));
                entityList.add(new ExcelExportEntity("申请提交时间", "insertTime", 15));
            List dataResult = leaveRecordService.selectExcelList(para);
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
            modelMap.put(MapExcelConstants.FILE_NAME, "请假申请");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("请假申请", "请假申请"));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
        }
}
