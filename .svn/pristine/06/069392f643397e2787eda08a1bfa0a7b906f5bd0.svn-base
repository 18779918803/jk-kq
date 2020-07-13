package com.jk.db.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.common.util.ShiroUtils;
import com.jk.db.entity.DbFeedback;
import com.jk.db.service.IDbFeedbackService;
import com.jk.sys.entity.User;
import com.jk.sys.service.IOrganService;
import com.jk.sys.service.IUserService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.Task;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 督查督办情况反馈表控制层
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月08日
 */
@Controller
public class DbFeedbackController extends BaseController {

    @Autowired
    private IDbFeedbackService dbFeedbackService;
    @Autowired
    private IUserService userService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private IOrganService organService;
    /**
     * 督查督办情况反馈表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("feedback:dbFeedback:list")
    @GetMapping("db/dbFeedback/list")
    public String list() {
        request.setAttribute("currentUser", ShiroUtils.getSessionUser());
        return "db/dbFeedback/list";
    }

    /**
     * 督查督办情况反馈表管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("feedback:dbFeedback:list")
    @PostMapping("db/dbFeedback/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        if (!ShiroUtils.getSessionUser().isAdmin()) {
            para.put("createBy", ShiroUtils.getSessionUser().getUsername());

        }
        para.put("type", "DbFeedback");
        IPage page = dbFeedbackService.selectPageList(para);
        List<DbFeedback> dbFeedbacks = page.getRecords();
        for (DbFeedback dbFeedback : dbFeedbacks) {

            Integer userId = userService.getUserId(dbFeedback.getCreateUserName());
            User sysUser = userService.selectById(userId);
            if (sysUser != null) {
                dbFeedback.setCreateUserName(sysUser.getName());
            }
            User sysUser2 = userService.selectById(dbFeedback.getApplyUser());
//            SysUser sysUser2 = userMapper.selectUserByLoginName(leave.getApplyUser());
            if (sysUser2 != null) {
                dbFeedback.setApplyUserName(sysUser2.getName());
            }
            // 当前环节
            if (StringUtils.isNotBlank(dbFeedback.getInstanceId())) {
                List<Task> taskList = taskService.createTaskQuery()
                        .processInstanceId(dbFeedback.getInstanceId())
//                        .singleResult();
                        .list();    // 例如请假会签，会同时拥有多个任务
                if (!CollectionUtils.isEmpty(taskList)) {
                    TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
                    dbFeedback.setTaskId(task.getId());
                    if (task.getSuspensionState() == 2) {
                        dbFeedback.setTaskName("已挂起");
                        dbFeedback.setSuspendState("2");
                    } else {
                        dbFeedback.setTaskName(task.getName());
                        dbFeedback.setSuspendState("1");
                    }
                } else {
                    // 已办结或者已撤销
                    dbFeedback.setTaskName("已结束");
                }
            } else {
                dbFeedback.setTaskName("未启动");
            }
        }
        page.setRecords(dbFeedbacks);
        return DataTable.ok(page);
    }

    /**
     * 新增督查督办情况反馈表页
     *
     * @return {@link String}
     */
    @RequiresPermissions("feedback:dbFeedback:insert")
    @GetMapping("db/dbFeedback/insert")
    public String insert() {
        return "db/dbFeedback/insert";
    }

    /**
     * 添加督查督办情况反馈表
     *
     * @param dbFeedback 督查督办情况反馈表
     * @return {@link ReturnBean}
     */
    @KrtLog("添加督查督办情况反馈表")
    @RequiresPermissions("feedback:dbFeedback:insert")
    @PostMapping("db/dbFeedback/insert")
    @ResponseBody
    public ReturnBean insert(DbFeedback dbFeedback) {
        dbFeedback.setLeadDepartment(organService.selectById(dbFeedback.getLeadDepartmentId()).getName());
        dbFeedback.setResponsibilityDepartment(organService.selectById(dbFeedback.getResponsibilityDepartmentId()).getName());
        dbFeedbackService.insert(dbFeedback);
        return ReturnBean.ok();
    }

    /**
     * 修改督查督办情况反馈表页
     *
     * @param id 督查督办情况反馈表id
     * @return {@link String}
     */
    @RequiresPermissions("feedback:dbFeedback:update")
    @GetMapping("db/dbFeedback/update")
    public String update(Integer id) {
        DbFeedback dbFeedback = dbFeedbackService.selectById(id);
        dbFeedback.setLeadDepartment(organService.selectById(dbFeedback.getLeadDepartmentId()).getName());
        dbFeedback.setResponsibilityDepartment(organService.selectById(dbFeedback.getResponsibilityDepartmentId()).getName());
        request.setAttribute("dbFeedback", dbFeedback);
        return "db/dbFeedback/update";
    }

    /**
     * 修改督查督办情况反馈表
     *
     * @param dbFeedback 督查督办情况反馈表
     * @return {@link ReturnBean}
     */
    @KrtLog("修改督查督办情况反馈表")
    @RequiresPermissions("feedback:dbFeedback:update")
    @PostMapping("db/dbFeedback/update")
    @ResponseBody
    public ReturnBean update(DbFeedback dbFeedback) {
        dbFeedbackService.updateById(dbFeedback);
        return ReturnBean.ok();
    }

    /**
     * 删除督查督办情况反馈表
     *
     * @param id 督查督办情况反馈表id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除督查督办情况反馈表")
    @RequiresPermissions("feedback:dbFeedback:delete")
    @PostMapping("db/dbFeedback/delete")
    @ResponseBody
    public ReturnBean delete(Integer id)   {
        dbFeedbackService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除督查督办情况反馈表
     *
     * @param ids 督查督办情况反馈表ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除督查督办情况反馈表")
    @RequiresPermissions("feedback:dbFeedback:delete")
    @PostMapping("db/dbFeedback/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        dbFeedbackService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导出办结反馈表
     *
     * @param id 选中id
     */
    @KrtLog("导出办结反馈表")
    @RequiresPermissions("feedback:dbFeedback:excelOut")
    @GetMapping("db/dbFeedback/excelOut")
    @ResponseBody
    public String excelOut(Integer id) throws IOException {
        JSONObject retval = new JSONObject();
        TemplateExportParams params = new TemplateExportParams("excel/db/dbFeedback.xlsx");
        params.setSheetName("建控集团督查督办事项办结情况反馈表");
        Map map = new HashMap<>();
        DbFeedback outExcel = dbFeedbackService.selectById(id);
        ArrayList<DbFeedback> dbFeedbacks = new ArrayList<>();
        dbFeedbacks.add(outExcel);
        map.put("dbList", dbFeedbacks);
        map.put("title", "建控集团督查督办事项办结情况反馈表");
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        Sheet sheet = workbook.getSheetAt(0);
        // 替换title
        // 下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("建控集团督查督办事项办结情况反馈表.xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
        return retval.toString();
    }

    /**
     * 提交申请
     */
    @KrtLog("提交申请")
    @PostMapping("db/dbFeedback/submitApply")
    @ResponseBody
    public ReturnBean submitApply(Long id)   {
        DbFeedback dbFeedback = dbFeedbackService.selectById(id);
        String applyUserId = ShiroUtils.getSessionUser().getId().toString();
        dbFeedbackService.submitApply(dbFeedback, applyUserId, "dbfeedback", new HashMap<>());
        return ReturnBean.ok();
    }
}
