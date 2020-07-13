package com.jk.db.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.db.entity.UserOrgans;
import com.jk.db.service.IUserOrgansService;
import com.jk.sys.entity.Organ;
import com.jk.sys.entity.User;
import com.jk.sys.service.IOrganService;
import com.jk.sys.service.IUserService;
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
 * 用户组织控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月21日
 */
@Controller
public class UserOrgansController extends BaseController {

    @Autowired
    private IUserOrgansService userOrgansService;

    @Autowired
    private IUserService  userService;


    @Autowired
    private IOrganService  organService;

    /**
     * 用户组织管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("UserOrgans:userOrgans:list")
    @GetMapping("db/userOrgans/list")
    public String list() {
        List<User> users = userService.selectList();
        request.setAttribute("users",users);
        List<Organ> organs = organService.selectList();
        request.setAttribute("organs",organs);
        return "db/userOrgans/list";
    }

    /**
     * 用户组织管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("UserOrgans:userOrgans:list")
    @PostMapping("db/userOrgans/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = userOrgansService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增用户组织页
     *
     * @return {@link String}
     */
    @RequiresPermissions("UserOrgans:userOrgans:insert")
    @GetMapping("db/userOrgans/insert")
    public String insert() {
        List<User> users = userService.selectList();
        request.setAttribute("users",users);
        List<Organ> organs = organService.selectList();
        request.setAttribute("organs",organs);
        return "db/userOrgans/insert";
    }

    /**
     * 添加用户组织
     *
     * @param userOrgans 用户组织
     * @return {@link ReturnBean}
     */
    @KrtLog("添加用户组织")
    @RequiresPermissions("UserOrgans:userOrgans:insert")
    @PostMapping("db/userOrgans/insert")
    @ResponseBody
    public ReturnBean insert(UserOrgans userOrgans) {
        userOrgansService.insert(userOrgans);
        return ReturnBean.ok();
    }

    /**
     * 修改用户组织页
     *
     * @param id 用户组织id
     * @return {@link String}
     */
    @RequiresPermissions("UserOrgans:userOrgans:update")
    @GetMapping("db/userOrgans/update")
    public String update(Integer id) {
        UserOrgans userOrgans = userOrgansService.selectById(id);
        request.setAttribute("userOrgans", userOrgans);
        List<Organ> organs = organService.selectList();
        request.setAttribute("organs",organs);
        List<User> users = userService.selectList();
        request.setAttribute("users",users);
        return "db/userOrgans/update";
    }

    /**
     * 修改用户组织
     *
     * @param userOrgans 用户组织
     * @return {@link ReturnBean}
     */
    @KrtLog("修改用户组织")
    @RequiresPermissions("UserOrgans:userOrgans:update")
    @PostMapping("db/userOrgans/update")
    @ResponseBody
    public ReturnBean update(UserOrgans userOrgans) {
        userOrgansService.updateById(userOrgans);
        return ReturnBean.ok();
    }

    /**
     * 删除用户组织
     *
     * @param id 用户组织id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除用户组织")
    @RequiresPermissions("UserOrgans:userOrgans:delete")
    @PostMapping("db/userOrgans/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        userOrgansService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除用户组织
     *
     * @param ids 用户组织ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除用户组织")
    @RequiresPermissions("UserOrgans:userOrgans:delete")
    @PostMapping("db/userOrgans/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        userOrgansService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
