package com.jk.db.listener;


import com.jk.db.entity.DbApply;
import com.jk.db.mapper.DbApplyMapper;

import com.jk.db.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 请假会签任务监听器，当会签任务完成时统计同意的数量
 *
 * @author henryyan
 */
@Component
public class DeptLeaderListener implements TaskListener {
    private static final long serialVersionUID = 1L;


    private  static DbApplyMapper dbApplyMapper;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        dbApplyMapper = context.getBean(DbApplyMapper.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        DbApply dbApply = dbApplyMapper.selectById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Integer leadDepartmentId = dbApply.getLeadDepartmentId(); //表示的是牵头部门的id
        List<User> users = dbApplyMapper.getUserByRoleAndOrgan(ConstUtils.DEPTLEADER, leadDepartmentId);  //表示的是借款方会计
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getId().toString());
        }
        delegateTask.addCandidateUsers(list);
    }

}
