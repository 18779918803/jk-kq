package com.jk.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.sys.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 日志管理控制层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Slf4j
@Controller
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    /**
     * 日志管理页
     */
    @RequiresPermissions("sys:log:list")
    @GetMapping("sys/log/list")
    public String list() {
        return "sys/log/list";
    }

    /**
     * 日志管理
     */
    @RequiresPermissions("sys:log:list")
    @PostMapping("sys/log/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = logService.selectPageList(para);
        return DataTable.ok(page);
    }


    /**
     * 清空日志
     */
    @KrtLog("清空日志")
    @RequiresRoles(value = "admin")
    @PostMapping("sys/log/deleteAll")
    @ResponseBody
    public ReturnBean deleteAll() {
        logService.deleteAll();
        return ReturnBean.ok();
    }

}
