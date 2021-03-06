package com.jk.db.listener;

import com.jk.db.entity.DbDelay;
import com.jk.db.entity.DbFeedback;
import com.jk.db.mapper.DbDelayMapper;
import com.jk.db.mapper.DbFeedbackMapper;
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
 * {{文件描述}}
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月28日
 */
@Component
public class DbFeedbackLeaderListener implements TaskListener {
    private static final long serialVersionUID = 1L;


    private  static DbFeedbackMapper dbFeedbackMapper;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        dbFeedbackMapper = context.getBean(DbFeedbackMapper.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        DbFeedback dbFeedback = dbFeedbackMapper.selectById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Integer leadDepartmentId = dbFeedback.getLeadDepartmentId(); //表示的是牵头部门的id
        List<User> users = dbFeedbackMapper.getUserByRoleAndOrgan(ConstUtils.DEPTLEADER, leadDepartmentId);  //表示的是借款方会计
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getId().toString());
        }
        delegateTask.addCandidateUsers(list);
    }
}
