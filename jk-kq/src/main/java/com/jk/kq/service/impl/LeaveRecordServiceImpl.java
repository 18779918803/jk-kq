package com.jk.kq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.jk.activiti.domain.BizLeaveVo;
import com.jk.activiti.page.PageDomain;
import com.jk.activiti.page.TableSupport;
import com.jk.activiti.service.IProcessService;
import com.jk.activiti.utils.DateUtils;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.bean.ReturnBean;
import com.jk.common.exception.KrtException;
import com.jk.kq.common.GeneralConvertor;
import com.jk.kq.constant.CommonConst;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.entity.WorkType;
import com.jk.kq.entity.WriteoffRecord;
import com.jk.kq.entity.vo.LeaveRecordVO;
import com.jk.kq.enums.TypeEnums;
import com.jk.kq.service.IWorkTypeService;
import com.jk.kq.utils.DateUtil;
import com.jk.sys.entity.User;
import com.jk.sys.service.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.mapper.LeaveRecordMapper;
import com.jk.kq.service.ILeaveRecordService;
import com.jk.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;


/**
 * 请假申请服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Service
public class LeaveRecordServiceImpl extends BaseServiceImpl<LeaveRecordMapper, LeaveRecord> implements ILeaveRecordService {

    @Autowired
    private LeaveRecordMapper leaveRecordMapper;


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IProcessService processService;

    @Autowired
    private GeneralConvertor convertor;

    @Autowired
    private IWorkTypeService workTypeService;

    @Autowired
    private IUserService userService;

    @Override
    public List selectHistory(Integer userId, String date) {
        return leaveRecordMapper.selectHistory(userId, date);
    }

    @Override
    public ProcessInstance submitApply(LeaveRecord entity, String applyUserId, String key, Map<String, Object> variables)   {
        entity.setApplyUser(applyUserId);
        entity.setApplyTime(DateUtils.getNowDate());
        String businessKey = entity.getId().toString(); // 实体类 ID，作为流程的业务 key
        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, entity.getTitle(), entity.getReason(), key, variables);
        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        leaveRecordMapper.updateById(entity);
        return processInstance;
    }

    @Override
    public List<LeaveRecord> findTodoTasks( String username,Map param) {

        List<LeaveRecord> results = new ArrayList<>();
        Date date =(Date) param.get("date");

        //Query query = new Query(param);
        //com.baomidou.mybatisplus.extension.plugins.pagination.Page page = query.getPage();
        //PageHelper.startPage(page);
        List<Task> tasks = processService.findTodoTasks(username, (String)param.get("type"));
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            TaskEntityImpl taskImpl = (TaskEntityImpl) task;
            String processInstanceId = taskImpl.getProcessInstanceId();
            // 条件过滤 1
            if (param.get("instanceId") != null && StringUtils.isNotBlank((CharSequence) param.get("instanceId")) && !param.get("instanceId").equals(processInstanceId)) {
                continue;
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            LeaveRecord leave2 = leaveRecordMapper.selectById(Integer.parseInt(businessKey));
            // 条件过滤 2
            if (!ObjectUtils.isEmpty(date)){
                if (!DateUtil.sameDate(leave2.getStartDate(),date)){
                    continue;
                }
            }
            leave2.setTaskId(taskImpl.getId());
            if (taskImpl.getSuspensionState() == 2) {
                leave2.setTaskName("已挂起");
            } else {
                leave2.setTaskName(taskImpl.getName());
            }
            User user = userService.selectById(leave2.getApplyUser());
            leave2.setApplyUserName(user.getName());
            results.add(leave2);
        }

        //page.setRecords(results);

        return results;
    }

    @Override
    @Transactional
    public void approval(Integer id, User user, HttpServletRequest request) {
        LeaveRecord leave = selectById(id);

        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(leave.getInstanceId())
                .list();    // 例如请假会签，会同时拥有多个任务
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("isPass",true);
        param.put("flag",true);
        param.put("comment","");
        param.put("userId",user.getId());
        processService.complete(taskList.get(0).getId(), leave.getInstanceId(), leave.getTitle(), leave.getReason()
                , TypeEnums.LEAVE.name().toLowerCase()
                , param, request);
    }

    @Override
    public List<LeaveRecordVO> findDoneTasks(Map para, String username) {

        Date date =(Date) para.get("date");

        // 手动分页
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<BizLeaveVo> list = new Page<>();

        List<LeaveRecord> results = new ArrayList<>();
        List<HistoricTaskInstance> hisList = processService.findDoneTasks(username, (String) para.get("type"));
        // 根据流程的业务ID查询实体并关联
        for (HistoricTaskInstance instance : hisList) {
            String processInstanceId = instance.getProcessInstanceId();
            Date claimTime = instance.getClaimTime();
            if (!DateUtils.isSameDay(claimTime, date)){
                continue;
            }
            // 条件过滤 1
            if (para.get("instanceId") != null && StringUtils.isNotBlank((CharSequence) para.get("instanceId")) && !para.get("instanceId").equals(processInstanceId)) {
                continue;
            }

            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            LeaveRecord leave2 = leaveRecordMapper.selectById(Integer.parseInt(businessKey));
            LeaveRecord newLeave = new LeaveRecord();
            BeanUtils.copyProperties(leave2, newLeave);
            // 条件过滤 2
            //if (StringUtils.isNotBlank((String) para.get("type")) && !para.get("type").equals(leave2.getType())) {
            //    continue;
            //}
            Task  task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
            newLeave.setTaskId(instance.getId());
            if(task==null){
                if(leave2.getIsPass()==1){
                    newLeave.setTaskName("审核通过");
                }else{
                    newLeave.setTaskName("审核拒绝");
                }
            }else{
                newLeave.setTaskName(task.getName());
            }
            newLeave.setDoneTime(instance.getEndTime());
            newLeave.setInsertTime(leave2.getInsertTime());
            User user = userService.selectById(leave2.getApplyUser());
            newLeave.setApplyUserName(user.getName());
            results.add(newLeave);
        }

//        Query query = new Query(para);
//        com.baomidou.mybatisplus.extension.plugins.pagination.Page page = query.getPage();
//        PageHelper.startPage(page);
//        page.setRecords(results);
        List<LeaveRecordVO> vos = this.convertor.convertor(results, LeaveRecordVO.class);
        for (LeaveRecordVO vo : vos) {
            vo.setPid(TypeEnums.LEAVE.getStatus());
        }
        return vos;
    }

    @Override
    @Transactional
    public void reject(Integer id, User openUser, HttpServletRequest request) {
        LeaveRecord leave = selectById(id);

        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(leave.getInstanceId())
                .list();    // 例如请假会签，会同时拥有多个任务
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
            HashMap<String, Object> param = new HashMap<>();
            param.put("isPass",false);
            param.put("flag",false);
            param.put("comment","");
            param.put("userId",openUser.getId());
            processService.complete(task.getId(), leave.getInstanceId(), leave.getTitle(), leave.getReason()
                    , TypeEnums.LEAVE.name().toLowerCase()
                    , param, request);
        }

    }


    @Override
    public void cancel(Integer id, User user, HttpServletRequest request) {
        LeaveRecord leaveRecord = leaveRecordMapper.selectById(id);
        if (leaveRecord.getIsEnd().equals(CommonConst.IS_END_YES)){
            throw new KrtException(ReturnBean.error("已经撤销"));
        }
        processService.cancelApply(leaveRecord.getInstanceId(),"用户撤销");
        leaveRecord.setIsEnd(CommonConst.IS_END_YES);
        leaveRecord.setIsPass(CommonConst.IS_PASS_SELF);
        leaveRecordMapper.updateById(leaveRecord);
    }
}
