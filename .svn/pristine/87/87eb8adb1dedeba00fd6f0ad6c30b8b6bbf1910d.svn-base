package com.jk.xcx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.xcx.entity.XcxPageRes;
import com.jk.xcx.service.IXcxPageResService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Map;

/**
 * 小程序页面资源列表控制层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
@Controller
public class XcxPageResController extends BaseController {

    @Autowired
    private IXcxPageResService xcxPageResService;

    /**
     * 小程序页面资源列表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("xcx:xcxPageRes:list")
    @GetMapping("xcx/xcxPageRes/list")
    public String list() {
        return "xcx/xcxPageRes/list";
    }

    /**
     * 小程序页面资源列表管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("xcx:xcxPageRes:list")
    @PostMapping("xcx/xcxPageRes/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = xcxPageResService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增小程序页面资源列表页
     *
     * @return {@link String}
     */
    @RequiresPermissions("xcx:xcxPageRes:insert")
    @GetMapping("xcx/xcxPageRes/insert")
    public String insert() {
        return "xcx/xcxPageRes/insert";
    }

    /**
     * 添加小程序页面资源列表
     *
     * @param xcxPageRes 小程序页面资源列表
     * @return {@link ReturnBean}
     */
    @KrtLog("添加小程序页面资源列表")
    @RequiresPermissions("xcx:xcxPageRes:insert")
    @PostMapping("xcx/xcxPageRes/insert")
    @ResponseBody
    public ReturnBean insert(XcxPageRes xcxPageRes) {
        xcxPageResService.insert(xcxPageRes);
        return ReturnBean.ok();
    }

    /**
     * 修改小程序页面资源列表页
     *
     * @param id 小程序页面资源列表id
     * @return {@link String}
     */
    @RequiresPermissions("xcx:xcxPageRes:update")
    @GetMapping("xcx/xcxPageRes/update")
    public String update(Integer id) {
        XcxPageRes xcxPageRes = xcxPageResService.selectById(id);
        request.setAttribute("xcxPageRes", xcxPageRes);
        return "xcx/xcxPageRes/update";
    }

    /**
     * 修改小程序页面资源列表
     *
     * @param xcxPageRes 小程序页面资源列表
     * @return {@link ReturnBean}
     */
    @KrtLog("修改小程序页面资源列表")
    @RequiresPermissions("xcx:xcxPageRes:update")
    @PostMapping("xcx/xcxPageRes/update")
    @ResponseBody
    public ReturnBean update(XcxPageRes xcxPageRes) {
        xcxPageResService.updateById(xcxPageRes);
        return ReturnBean.ok();
    }

    /**
     * 删除小程序页面资源列表
     *
     * @param id 小程序页面资源列表id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除小程序页面资源列表")
    @RequiresPermissions("xcx:xcxPageRes:delete")
    @PostMapping("xcx/xcxPageRes/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        xcxPageResService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除小程序页面资源列表
     *
     * @param ids 小程序页面资源列表ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除小程序页面资源列表")
    @RequiresPermissions("xcx:xcxPageRes:delete")
    @PostMapping("xcx/xcxPageRes/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        xcxPageResService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
