package com.jk.activiti.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.activiti.config.BusinessType;
import com.jk.activiti.config.Log;
import com.jk.activiti.domain.AjaxResult;
import com.jk.activiti.domain.BizTodoItem;
import com.jk.activiti.service.IBizTodoItemService;
import com.jk.activiti.utils.ExcelUtil;
import com.jk.common.bean.DataTable;
import com.jk.common.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办事项Controller
 *
 * @author Xianlu Tech
 * @date 2019-11-08
 */
@Controller
@RequestMapping("todoitem")
public class BizTodoItemController extends BaseController {
    private String prefix = "todoitem";

    @Autowired
    private IBizTodoItemService bizTodoItemService;

    @GetMapping("/todoListView")
    public String todoListView(ModelMap mmap) {
        BizTodoItem todoItem = new BizTodoItem();
        todoItem.setTodoUserId(ShiroUtils.getSessionUser().getUsername());
        todoItem.setIsHandle("0");
        Map  map=new HashMap();
        map.put("isHandle","0");
        map.put("todoUserId",ShiroUtils.getSessionUser().getUsername());
        List<BizTodoItem> todoItemList = bizTodoItemService.selectBizTodoItemByList(map);
        mmap.put("todoItemList", todoItemList);
        return prefix + "/todoList";
    }

    @GetMapping("/todoitem")
    public String todoitem(ModelMap mmap) {
        mmap.put("username", ShiroUtils.getSessionUser().getUsername());
        return prefix + "/todoitem";
    }

    /**
     * 查询待办事项列表
     */
    @PostMapping("/todoitem")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        para.put("isHandle","0");
        if (!ShiroUtils.getSessionUser().isAdmin()) {
            para.put("todoUserId",ShiroUtils.getSessionUser().getUsername());
        }
        IPage  page = bizTodoItemService.selectBizTodoItemList(para);
        return DataTable.ok(page);
    }

    @GetMapping("/doneitemView")
    public String doneitem() {
        return prefix + "/doneitem";
    }

    /**
     * 查询已办事项列表
     */
    @PostMapping("/doneList")
    @ResponseBody
    public DataTable doneList(@RequestParam Map para) {
        para.put("isHandle","1");
        if (!ShiroUtils.getSessionUser().isAdmin()) {
            para.put("todoUserId",ShiroUtils.getSessionUser().getUsername());
        }
        IPage<BizTodoItem> page = bizTodoItemService.selectBizTodoItemList(para);
        return DataTable.ok(page);
    }

    /**
     * 导出待办事项列表
     */

    @GetMapping("/export")
    @ResponseBody
    public AjaxResult export(ModelMap modelMap, Map para) {
//        bizTodoItem.setIsHandle("0");
//        bizTodoItem.setTodoUserId(ShiroUtils.getSessionUser().getUsername());
//        Map  map=new HashMap();
        para.put("isHandle","0");
        if (!ShiroUtils.getSessionUser().isAdmin()) {
            para.put("todoUserId",ShiroUtils.getSessionUser().getUsername());
        }
        List<BizTodoItem> list = bizTodoItemService.selectBizTodoItemByList(para);
        ExcelUtil<BizTodoItem> util = new ExcelUtil<BizTodoItem>(BizTodoItem.class);
        return util.exportExcel(list, "todoitem");
    }


//    /**
//     * 导出归还本金和利息
//     *
//     * @param modelMap 返回模型
//     * @param para     参数
//     */
//    @KrtLog("导出归还本金和利息")
//    @RequiresPermissions("LoanReturn:loanReturn:excelOut")
//    @GetMapping("gck/loanReturn/excelOut")
//    public String excelOut(ModelMap modelMap, Map para) {
//        List<ExcelExportEntity> entityList = new ArrayList<>();
//        entityList.add(new ExcelExportEntity("借款协议编号", "loanNumber", 15));
//        entityList.add(new ExcelExportEntity("应还本金", "shouldAmount", 15));
//        entityList.add(new ExcelExportEntity("已还本金", "amount", 15));
//        entityList.add(new ExcelExportEntity("应还利息", "shouldRate", 15));
//        entityList.add(new ExcelExportEntity("已还利息", "rate", 15));
//        entityList.add(new ExcelExportEntity("还款时间", "tradeTime", 15));
//        entityList.add(new ExcelExportEntity("备注", "remark", 15));
//        List dataResult = loanReturnService.selectExcelList(para);
//        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
//        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
//        modelMap.put(MapExcelConstants.FILE_NAME, "归还本金和利息");
//        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("归还本金和利息", "归还本金和利息"));
//        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
//    }

    /**
     * 导出已办事项列表
     */

    @GetMapping("/doneExport")
    @ResponseBody
    public AjaxResult doneExport(ModelMap modelMap, Map para) {
       // bizTodoItem.setIsHandle("1");
//        Map  map=new HashMap();
        para.put("isHandle","1");
        List<BizTodoItem> list = bizTodoItemService.selectBizTodoItemByList(para);
        ExcelUtil<BizTodoItem> util = new ExcelUtil<BizTodoItem>(BizTodoItem.class);
        return util.exportExcel(list, "todoitem");
    }

    /**
     * 新增待办事项
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存待办事项
     */
    @Log(title = "待办事项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizTodoItem bizTodoItem) {
        return toAjax(bizTodoItemService.insertBizTodoItem(bizTodoItem));
    }

    /**
     * 修改待办事项
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizTodoItem bizTodoItem = bizTodoItemService.selectBizTodoItemById(id);
        mmap.put("bizTodoItem", bizTodoItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存待办事项
     */
    @Log(title = "待办事项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizTodoItem bizTodoItem) {
        return toAjax(bizTodoItemService.updateBizTodoItem(bizTodoItem));
    }

    /**
     * 删除待办事项
     */
    @Log(title = "待办事项", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizTodoItemService.deleteBizTodoItemByIds(ids));
    }
}
