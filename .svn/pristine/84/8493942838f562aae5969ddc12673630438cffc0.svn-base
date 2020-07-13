package com.jk.kq.listen;


import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.enums.RoleEnums;
import com.jk.kq.mapper.LeaveRecordMapper;
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
public class KqDeptLeaderListener implements TaskListener {

    private static final long serialVersionUID = 1L;


    @Autowired
    private ApplicationContext context;


    private static LeaveRecordMapper leaveRecordMapper;


    @PostConstruct
    public void init() {
        leaveRecordMapper = context.getBean(LeaveRecordMapper.class);

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        LeaveRecord leaveRecord = leaveRecordMapper.selectById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Integer orgainId = leaveRecord.getOrganId();

        List<String> userOrganByOrgan = leaveRecordMapper.getUserOrganByOrgan(orgainId,RoleEnums.DEPT_LEADER.getStatus());

        if(userOrganByOrgan==null||userOrganByOrgan.size()==0){
            //Integer leadDepartmentId = dbApply.getLeadDepartmentId(); //表示的是责任部门的id
            List<User> users = leaveRecordMapper.getUserByRoleAndOrgan(RoleEnums.DEPT_LEADER.getStatus(), orgainId);  //表示的是借款方会计
            List<String> list = new ArrayList<>();
            for (User user : users) {
                list.add(user.getId().toString());
            }
            delegateTask.addCandidateUsers(list);
        }else{
            delegateTask.addCandidateUsers(userOrganByOrgan);
        }


    }

}
