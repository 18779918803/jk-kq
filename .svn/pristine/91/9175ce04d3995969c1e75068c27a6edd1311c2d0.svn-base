package com.jk.activiti.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.jk.activiti.domain.ActIdUser;
import com.jk.activiti.page.TableDataInfo;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.sys.entity.User;
import com.jk.sys.service.IUserService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 流程用户Controller
 *
 * @author Xianlu Tech
 * @date 2019-10-02
 */
@Controller
public class ActIdUserController extends BaseController {

    private String prefix = "user";

    @Autowired
    private IdentityService identityService;


    @Autowired
    private IUserService userService;

    @GetMapping("user/list")
    public String user()
    {
        return prefix + "/list";
    }

    /**
     * 查询流程用户列表
     */
    @PostMapping("user/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para)
    {

        UserQuery userQuery = identityService.createUserQuery();
        if (para.get("id")!=null&&StringUtils.isNotBlank(para.get("id")+"")) {
            userQuery.userId(para.get("id")+"");
        }
        if (para.get("first")!=null&&StringUtils.isNotBlank(para.get("first")+"")) {
            userQuery.userFirstNameLike("%" + para.get("first") + "%");
        }
        if (para.get("email")!=null&&StringUtils.isNotBlank(para.get("email")+"")) {
            userQuery.userEmailLike("%" + para.get("email") + "%");
        }
       // List<org.activiti.engine.identity.User> userList = userQuery.listPage((pageNum - 1) * pageSize, pageSize);
        Query query = new Query(para);
        List<org.activiti.engine.identity.User> userList = userQuery.list();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page page = query.getPage();
        PageHelper.startPage(page);
        page.setSize(10);
        page.setTotal(userList.size());
        List<ActIdUser> list = new ArrayList<>();
        for (org.activiti.engine.identity.User user: userList) {
            ActIdUser idUser = new ActIdUser();
            idUser.setId(user.getId());
            idUser.setFirst(user.getFirstName());
            idUser.setEmail(user.getEmail());
            list.add(idUser);
        }
        page.setRecords(list);
        return DataTable.ok(page);
    }

    /**
     * 选择系统用户
     */
    @GetMapping("user/authUser/selectUser")
    public String selectUser(String taskId, ModelMap mmap) {
        mmap.put("taskId", taskId);
        return prefix + "/selectUser";
    }

    @PostMapping("user/systemUserList")
    @ResponseBody
    public DataTable systemUserList(@RequestParam Map  map) {
        IPage<User> list = userService.selectPageList(map);
        return DataTable.ok(list);
    }

}
