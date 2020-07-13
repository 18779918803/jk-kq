package com.jk.kq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.entity.OndutyRecord;
import com.jk.kq.service.IOndutyRecordService;
import com.jk.kq.service.IOpenUserService;
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
 * 值班记录控制层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月26日
 */
@Controller
public class OndutyRecordController extends BaseController {

    @Autowired
    private IOndutyRecordService ondutyRecordService;

    @Autowired
    private IOpenUserService openUserService;

    /**
     * 值班记录管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("OndutyRecord:ondutyRecord:list")
    @GetMapping("kq/ondutyRecord/list")
    public String list() {
        return "kq/ondutyRecord/list";
    }

    /**
     * 值班记录管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("OndutyRecord:ondutyRecord:list")
    @PostMapping("kq/ondutyRecord/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = ondutyRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增值班记录页
     *
     * @return {@link String}
     */
    @RequiresPermissions("OndutyRecord:ondutyRecord:insert")
    @GetMapping("kq/ondutyRecord/insert")
    public String insert() {
        return "kq/ondutyRecord/insert";
    }

    /**
     * 添加值班记录
     *
     * @param ondutyRecord 值班记录
     * @return {@link ReturnBean}
     */
    @KrtLog("添加值班记录")
    @RequiresPermissions("OndutyRecord:ondutyRecord:insert")
    @PostMapping("kq/ondutyRecord/insert")
    @ResponseBody
    public ReturnBean insert(OndutyRecord ondutyRecord) {
        ondutyRecordService.insert(ondutyRecord);
        return ReturnBean.ok();
    }

    /**
     * 修改值班记录页
     *
     * @param id 值班记录id
     * @return {@link String}
     */
    @RequiresPermissions("OndutyRecord:ondutyRecord:update")
    @GetMapping("kq/ondutyRecord/update")
    public String update(Integer id,String name) {
        OndutyRecord ondutyRecord = ondutyRecordService.selectById(id);
        request.setAttribute("ondutyRecord", ondutyRecord);
        request.setAttribute("ondutyRecordName",name);
        return "kq/ondutyRecord/update";
    }

    /**
     * 修改值班记录
     *
     * @param ondutyRecord 值班记录
     * @return {@link ReturnBean}
     */
    @KrtLog("修改值班记录")
    @RequiresPermissions("OndutyRecord:ondutyRecord:update")
    @PostMapping("kq/ondutyRecord/update")
    @ResponseBody
    public ReturnBean update(OndutyRecord ondutyRecord) {
        ondutyRecordService.updateById(ondutyRecord);
        return ReturnBean.ok();
    }

    /**
     * 删除值班记录
     *
     * @param id 值班记录id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除值班记录")
    @RequiresPermissions("OndutyRecord:ondutyRecord:delete")
    @PostMapping("kq/ondutyRecord/delete")
    @ResponseBody
    public ReturnBean delete(Integer id)   {
        ondutyRecordService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除值班记录
     *
     * @param ids 值班记录ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除值班记录")
    @RequiresPermissions("OndutyRecord:ondutyRecord:delete")
    @PostMapping("kq/ondutyRecord/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids)   {
        ondutyRecordService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


        /**
        * 导出值班记录
        *
        * @param modelMap 返回模型
        * @param para 参数
        */
        @KrtLog("导出值班记录")
        @RequiresPermissions("OndutyRecord:ondutyRecord:excelOut")
        @GetMapping("kq/ondutyRecord/excelOut")
        public String excelOut(ModelMap modelMap,Map para) {
            List<ExcelExportEntity> entityList = new ArrayList<>();
                entityList.add(new ExcelExportEntity("ID", "id", 15));
                entityList.add(new ExcelExportEntity("姓名", "openUserId", 15));
                entityList.add(new ExcelExportEntity("公司/部门", "organ", 15));
                entityList.add(new ExcelExportEntity("值班日期", "date", 15));
                entityList.add(new ExcelExportEntity("状态", "state", 15));
            List dataResult = ondutyRecordService.selectExcelList(para);
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
            modelMap.put(MapExcelConstants.FILE_NAME, "值班记录");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("值班记录", "值班记录"));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
        }
}
