package com.jk.sw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.service.ISwUsableService;
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
 * 文件可用范围控制层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月16日
 */
@Controller
public class SwUsableController extends BaseController {

    @Autowired
    private ISwUsableService swUsableService;

    /**
     * 文件可用范围管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sw:swUsable:list")
    @GetMapping("sw/swUsable/list")
    public String list() {
        return "sw/swUsable/list";
    }

    /**
     * 文件可用范围管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("sw:swUsable:list")
    @PostMapping("sw/swUsable/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = swUsableService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增文件可用范围页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sw:swUsable:insert")
    @GetMapping("sw/swUsable/insert")
    public String insert() {
        return "sw/swUsable/insert";
    }

    /**
     * 添加文件可用范围
     *
     * @param swUsable 文件可用范围
     * @return {@link ReturnBean}
     */
    @KrtLog("添加文件可用范围")
    @RequiresPermissions("sw:swUsable:insert")
    @PostMapping("sw/swUsable/insert")
    @ResponseBody
    public ReturnBean insert(SwUsable swUsable) {
        swUsableService.insert(swUsable);
        return ReturnBean.ok();
    }

    /**
     * 修改文件可用范围页
     *
     * @param id 文件可用范围id
     * @return {@link String}
     */
    @RequiresPermissions("sw:swUsable:update")
    @GetMapping("sw/swUsable/update")
    public String update(Integer id) {
        SwUsable swUsable = swUsableService.selectById(id);
        request.setAttribute("swUsable", swUsable);
        return "sw/swUsable/update";
    }

    /**
     * 修改文件可用范围
     *
     * @param swUsable 文件可用范围
     * @return {@link ReturnBean}
     */
    @KrtLog("修改文件可用范围")
    @RequiresPermissions("sw:swUsable:update")
    @PostMapping("sw/swUsable/update")
    @ResponseBody
    public ReturnBean update(SwUsable swUsable) {
        swUsableService.updateById(swUsable);
        return ReturnBean.ok();
    }

    /**
     * 删除文件可用范围
     *
     * @param id 文件可用范围id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除文件可用范围")
    @RequiresPermissions("sw:swUsable:delete")
    @PostMapping("sw/swUsable/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        swUsableService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除文件可用范围
     *
     * @param ids 文件可用范围ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除文件可用范围")
    @RequiresPermissions("sw:swUsable:delete")
    @PostMapping("sw/swUsable/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        swUsableService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
