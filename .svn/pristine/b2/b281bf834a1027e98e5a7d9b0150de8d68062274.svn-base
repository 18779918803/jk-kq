package com.jk.db.listener;

import com.jk.db.entity.DbDelay;
import com.jk.db.mapper.DbDelayMapper;
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
 * {{任务监听器，当会签任务完成时统计同意的数量}}
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月28日
 */
@Component
public class DbDelayDeptLeaderListener implements TaskListener {

    private static final long serialVersionUID = 1L;


    private  static DbDelayMapper dbDelayMapper;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        dbDelayMapper = context.getBean(DbDelayMapper.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        DbDelay dbDelay = dbDelayMapper.selectById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Integer leadDepartmentId = dbDelay.getLeadDepartmentId(); //表示的是牵头部门的id
        List<User> users = dbDelayMapper.getUserByRoleAndOrgan(ConstUtils.DEPTLEADER, leadDepartmentId);  //表示的是借款方会计
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getId().toString());
        }
        delegateTask.addCandidateUsers(list);
    }

}
