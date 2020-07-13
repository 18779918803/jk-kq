package com.jk.kq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.kq.entity.WorkType;
import com.jk.kq.service.IWorkTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 考勤工作类型控制层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月28日
 */
@Controller
public class WorkTypeController extends BaseController {

    @Autowired
    private IWorkTypeService workTypeService;

    /**
     * 考勤工作类型管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("WorkType:workType:list")
    @GetMapping("kq/workType/list")
    public String list() {
        return "kq/workType/list";
    }

    /**
     * 考勤工作类型管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("WorkType:workType:list")
    @PostMapping("kq/workType/list")
    @ResponseBody
    public ReturnBean list(@RequestParam(name = "pid",defaultValue = "0") Integer pid) {
        List<WorkType> workTypes = workTypeService.selectByPid(pid);
        return ReturnBean.ok(workTypes);
    }

    /**
     * 新增考勤工作类型页
     *
     * @return {@link String}
     */
    @RequiresPermissions("WorkType:workType:insert")
    @GetMapping("kq/workType/insert")
    public String insert(@RequestParam(name = "pid",defaultValue = "0") Integer pid) {
        request.setAttribute("pid", pid);
        request.setAttribute("workType", workTypeService.selectById(pid));
        return "kq/workType/insert";
    }

    /**
     * 添加考勤工作类型
     *
     * @param workType 考勤工作类型
     * @return {@link ReturnBean}
     */
    @KrtLog("添加考勤工作类型")
    @RequiresPermissions("WorkType:workType:insert")
    @PostMapping("kq/workType/insert")
    @ResponseBody
    public ReturnBean insert(WorkType workType) {
        workTypeService.insert(workType);
        return ReturnBean.ok();
    }

    /**
     * 修改考勤工作类型页
     *
     * @param id 考勤工作类型id
     * @return {@link String}
     */
    @RequiresPermissions("WorkType:workType:update")
    @GetMapping("kq/workType/update")
    public String update(Integer id, @RequestParam(name = "pid",defaultValue = "0") Integer pid) {
        request.setAttribute("workType", workTypeService.selectById(id));
        request.setAttribute("workTypeParent", workTypeService.selectByPid(pid));
        return "kq/workType/update";
    }

    /**
     * 修改考勤工作类型
     *
     * @param workType 考勤工作类型
     * @return {@link ReturnBean}
     */
    @KrtLog("修改考勤工作类型")
    @RequiresPermissions("WorkType:workType:update")
    @PostMapping("kq/workType/update")
    @ResponseBody
    public ReturnBean update(WorkType workType) {
        workTypeService.updateById(workType);
        return ReturnBean.ok();
    }

    /**
     * 删除考勤工作类型
     *
     * @param id 考勤工作类型id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除考勤工作类型")
    @RequiresPermissions("WorkType:workType:delete")
    @PostMapping("kq/workType/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        workTypeService.deleteById(id);
        workTypeService.deleteByPid(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除考勤工作类型
     *
     * @param ids 考勤工作类型ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除考勤工作类型")
    @RequiresPermissions("WorkType:workType:delete")
    @PostMapping("kq/workType/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        workTypeService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
