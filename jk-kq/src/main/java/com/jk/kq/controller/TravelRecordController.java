package com.jk.kq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.config.WorkTypeConfig;
import com.jk.kq.entity.TravelRecord;
import com.jk.kq.entity.WorkType;
import com.jk.kq.service.ITravelRecordService;
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
 * 出差申请控制层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Controller
public class TravelRecordController extends BaseController {

    @Autowired
    private ITravelRecordService travelRecordService;

    @Autowired
    private IWorkTypeService workTypeService;

    /**
     * 出差申请管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("TravelRecord:travelRecord:list")
    @GetMapping("kq/travelRecord/list")
    public String list() {
        List<WorkType> travelTypes = workTypeService.selectByPid(WorkTypeConfig.TRAVEL);
        request.setAttribute("travelTypes",travelTypes);
        return "kq/travelRecord/list";
    }

    /**
     * 出差申请管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("TravelRecord:travelRecord:list")
    @PostMapping("kq/travelRecord/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = travelRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增出差申请页
     *
     * @return {@link String}
     */
    @RequiresPermissions("TravelRecord:travelRecord:insert")
    @GetMapping("kq/travelRecord/insert")
    public String insert() {
        List<WorkType> travelTypes = workTypeService.selectByPid(WorkTypeConfig.TRAVEL);
        request.setAttribute("travelTypes",travelTypes);
        return "kq/travelRecord/insert";
    }

    /**
     * 添加出差申请
     *
     * @param travelRecord 出差申请
     * @return {@link ReturnBean}
     */
    @KrtLog("添加出差申请")
    @RequiresPermissions("TravelRecord:travelRecord:insert")
    @PostMapping("kq/travelRecord/insert")
    @ResponseBody
    public ReturnBean insert(TravelRecord travelRecord) {
        travelRecordService.insert(travelRecord);
        return ReturnBean.ok();
    }

    /**
     * 修改出差申请页
     *
     * @param id 出差申请id
     * @return {@link String}
     */
    @RequiresPermissions("TravelRecord:travelRecord:update")
    @GetMapping("kq/travelRecord/update")
    public String update(Integer id) {
        TravelRecord travelRecord = travelRecordService.selectById(id);
        request.setAttribute("travelRecord", travelRecord);
        List<WorkType> travelTypes = workTypeService.selectByPid(WorkTypeConfig.TRAVEL);
        request.setAttribute("travelTypes",travelTypes);
        return "kq/travelRecord/update";
    }

    /**
     * 修改出差申请
     *
     * @param travelRecord 出差申请
     * @return {@link ReturnBean}
     */
    @KrtLog("修改出差申请")
    @RequiresPermissions("TravelRecord:travelRecord:update")
    @PostMapping("kq/travelRecord/update")
    @ResponseBody
    public ReturnBean update(TravelRecord travelRecord) {
        travelRecordService.updateById(travelRecord);
        return ReturnBean.ok();
    }

    /**
     * 删除出差申请
     *
     * @param id 出差申请id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除出差申请")
    @RequiresPermissions("TravelRecord:travelRecord:delete")
    @PostMapping("kq/travelRecord/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        travelRecordService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除出差申请
     *
     * @param ids 出差申请ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除出差申请")
    @RequiresPermissions("TravelRecord:travelRecord:delete")
    @PostMapping("kq/travelRecord/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        travelRecordService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


        /**
        * 导出出差申请
        *
        * @param modelMap 返回模型
        * @param para 参数
        */
        @KrtLog("导出出差申请")
        @RequiresPermissions("TravelRecord:travelRecord:excelOut")
        @GetMapping("kq/travelRecord/excelOut")
        public String excelOut(ModelMap modelMap,Map para) {
            List<ExcelExportEntity> entityList = new ArrayList<>();
                entityList.add(new ExcelExportEntity("ID", "id", 15));
                entityList.add(new ExcelExportEntity("open_user_id", "openUserId", 15));
                entityList.add(new ExcelExportEntity("姓名", "name", 15));
                entityList.add(new ExcelExportEntity("公司ID/部门ID", "organId", 15));
                entityList.add(new ExcelExportEntity("公司/部门", "organ", 15));
                entityList.add(new ExcelExportEntity("出差日期", "date", 15));
                entityList.add(new ExcelExportEntity("出差原因", "reason", 15));
                entityList.add(new ExcelExportEntity("出差天数", "day", 15));
                entityList.add(new ExcelExportEntity("申请状态", "state", 15));
                entityList.add(new ExcelExportEntity("出差类型", "typeId", 15));
                entityList.add(new ExcelExportEntity("申请提交时间", "insertTime", 15));
            List dataResult = travelRecordService.selectExcelList(para);
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
            modelMap.put(MapExcelConstants.FILE_NAME, "出差申请");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("出差申请", "出差申请"));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
        }
}
