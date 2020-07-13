package com.jk.kq.listen;


import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.enums.RoleEnums;
import com.jk.kq.mapper.LeaveRecordMapper;
import com.jk.sys.entity.User;
import com.jk.sys.mapper.RoleMapper;
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
public class KqLeaderListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    private  static LeaveRecordMapper leaveRecordMapper;

    @Autowired
    private RoleMapper  roleMapper;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        leaveRecordMapper = context.getBean(LeaveRecordMapper.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        LeaveRecord leaveRecord = leaveRecordMapper.selectById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Integer orgainId = leaveRecord.getOrganId();
        //Integer leadDepartmentId = dbApply.getLeadDepartmentId(); //表示的是责任部门的id

        List<String> userOrganByOrgan = leaveRecordMapper.getUserOrganByOrgan(orgainId,RoleEnums.DIS_GUIDE.getStatus());
        if(userOrganByOrgan==null||userOrganByOrgan.size()==0){
            List<User> users = leaveRecordMapper.getUserByRoleAndOrgan(RoleEnums.DIS_GUIDE.getStatus(), orgainId);  //表示的是借款方会计
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
