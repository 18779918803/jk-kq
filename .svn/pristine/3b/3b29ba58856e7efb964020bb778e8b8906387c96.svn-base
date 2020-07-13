package com.jk.activiti.controller;

import com.jk.activiti.domain.ActIdGroup;
import com.jk.activiti.domain.ActIdUser;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.page.PageDomain;
//import com.ruoyi.common.core.page.TableDataInfo;
//import com.ruoyi.common.core.page.TableSupport;
//import com.ruoyi.activiti.domain.ActIdGroup;
//import com.ruoyi.common.utils.StringUtils;

/**
 * 流程用户组Controller
 *
 * @author Xianlu Tech
 * @date 2019-10-02
 */
@Controller
public class ActIdGroupController extends BaseController
{
    private String prefix = "group";

    @Autowired
    private IdentityService identityService;

    @GetMapping("group/list")
    public String group()
    {
        return prefix + "/list";
    }

    /**
     * 查询流程用户组列表
     */
    @PostMapping("group/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para)
    {
        GroupQuery groupQuery = identityService.createGroupQuery();
        if (para.get("id")!=null&&StringUtils.isNotBlank(para.get("id")+"")) {
            groupQuery.groupId(para.get("id")+"");
        }
        if (para.get("name")!=null&&StringUtils.isNotBlank(para.get("name")+"")) {
            groupQuery.groupId(para.get("name")+"");

        }
        List<Group> groupList = groupQuery.list();
        Query query = new Query(para);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page page = query.getPage();
        PageHelper.startPage(page);
        page.setSize(10);
        page.setTotal(groupList.size());
        List<ActIdGroup> list = new ArrayList<>();
        for (Group group: groupList) {
            ActIdGroup idGroup = new ActIdGroup();
            idGroup.setId(group.getId());
            idGroup.setName(group.getName());
            list.add(idGroup);
        }
        page.setRecords(list);
        return  DataTable.ok(page);
    }

}
