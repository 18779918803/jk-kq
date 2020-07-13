package com.jk.xcx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.common.constant.GlobalConstant;
import com.jk.common.util.StringUtils;
import com.jk.xcx.entity.XcxRoleRes;
import com.jk.xcx.service.IXcxRoleResService;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 小程序页面角色关系表控制层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
@Controller
public class XcxRoleResController extends BaseController {

    @Autowired
    private IXcxRoleResService xcxRoleResService;

    /**
     * 小程序页面角色关系表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("xcx:xcxRoleRes:list")
    @GetMapping("xcx/xcxRoleRes/list")
    public String list() {
        return "xcx/xcxRoleRes/list";
    }

    /**
     * 小程序页面角色关系表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("xcx:xcxRoleRes:list")
    @GetMapping("xcx/xcxRoleRes/selectResList")
    public String selectResList() {
        return "xcx/xcxRoleRes/selectResList";
    }



    /**
     * 小程序页面角色关系表管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("xcx:xcxRoleRes:list")
    @PostMapping("xcx/xcxRoleRes/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = xcxRoleResService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增小程序页面角色关系表页
     *
     * @return {@link String}
     */
    @RequiresPermissions("xcx:xcxRoleRes:insert")
    @GetMapping("xcx/xcxRoleRes/insert")
    public String insert() {
        return "xcx/xcxRoleRes/insert";
    }

    /**
     * 添加小程序页面角色关系表
     *
     * @return {@link ReturnBean}
     */
    @KrtLog("添加小程序页面角色关系表")
    @RequiresPermissions("sys:role:xcxRes")
    @PostMapping("xcx/xcxRoleRes/insert")
    @ResponseBody
    public ReturnBean insert(String resIds, Integer roleId) {
        return xcxRoleResService.insertBatch(resIds, roleId);
    }

    /**
     * 根据角色ID查询小程序页面资源
     *
     * @return {@link ReturnBean}
     */
    @RequiresPermissions("sys:role:xcxRes")
    @PostMapping("xcx/xcxRoleRes/getResByRoleId")
    @ResponseBody
    public ReturnBean getResByRoleId(Integer roleId) {
        return xcxRoleResService.getResByRoleId(roleId);
    }
}
