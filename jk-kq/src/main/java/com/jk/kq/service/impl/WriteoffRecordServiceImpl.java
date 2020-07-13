package com.jk.kq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.jk.activiti.domain.BizLeaveVo;
import com.jk.activiti.page.PageDomain;
import com.jk.activiti.page.TableSupport;
import com.jk.activiti.service.IProcessService;
import com.jk.activiti.utils.DateUtils;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.ReturnBean;
import com.jk.common.exception.KrtException;
import com.jk.kq.common.GeneralConvertor;
import com.jk.kq.constant.CommonConst;
import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.entity.WorkType;
import com.jk.kq.entity.WriteoffRecord;
import com.jk.kq.entity.vo.WriteoffRecordVo;
import com.jk.kq.enums.TypeEnums;
import com.jk.kq.mapper.WriteoffRecordMapper;
import com.jk.kq.service.IWorkTypeService;
import com.jk.kq.service.IWriteoffRecordService;
import com.jk.kq.utils.DateUtil;
import com.jk.kq.utils.TaskUtilService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 核销申请服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Service
public class WriteoffRecordServiceImpl extends BaseServiceImpl<WriteoffRecordMapper, WriteoffRecord> implements IWriteoffRecordService {

    @Autowired
    private WriteoffRecordMapper writeoffRecordMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IProcessService processService;


    @Autowired
    private IWriteoffRecordService writeoffRecordService;


    @Autowired
    private RuntimeService runtimeService;


    @Autowired
    private IUserService userService;


    @Autowired
    private GeneralConvertor convertor;

    @Autowired
    private IWorkTypeService workTypeService;

    @Autowired
    private TaskUtilService taskUtilService;


    @Override
    public List selectHistory(Integer userId, String date) {
        return writeoffRecordMapper.selectHistory(userId, date);
    }

    /**
     * 新增用户核销申请
     * @param openUser
     * @param writeoffRecord
     * @return 返回该申请需要推送的用户的集合
     */
    @Override
    public List<OpenUser> insert(OpenUser openUser, WriteoffRecord writeoffRecord) {
        writeoffRecord.setUserId(openUser.getId());
        writeoffRecord.setName(openUser.getName());
        writeoffRecord.setOrganId(openUser.getOrganId());
        writeoffRecord.setOrgan(openUser.getOrgan());
        //查找用户的职位信息
        writeoffRecordMapper.insert(writeoffRecord);
        return new ArrayList<>();
    }

    /**
     * 找该用户等级可以审核的核销申请
     * @param queryMap
     * @return
     */
    @Override
    public List selectToCheck(Map queryMap) {
        return writeoffRecordMapper.selectToCheck(queryMap);
    }

    @Override
    public ProcessInstance submitApply(WriteoffRecord entity, String applyUserId, String key, Map<String, Object> variables)   {
        entity.setApplyUser(applyUserId);
        entity.setApplyTime(DateUtils.getNowDate());
        String businessKey = entity.getId().toString(); // 实体类 ID，作为流程的业务 key
        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, entity.getTitle(), entity.getReason(), key, variables);
        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        writeoffRecordService.updateById(entity);
        return processInstance;
    }

    @Override
    public List<WriteoffRecord> findTodoTasks(String username, Map param) {
        List<WriteoffRecord> results = new ArrayList<>();
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
            WriteoffRecord leave2 = writeoffRecordService.selectById(Integer.parseInt(businessKey));
            // 条件过滤 2
            if (!ObjectUtils.isEmpty(date)){
                if (!DateUtil.sameDate(leave2.getDate(),date)){
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
    public void cancel(Integer id, User user, HttpServletRequest request) {
        WriteoffRecord writeoffRecord = writeoffRecordService.selectById(id);
        if (writeoffRecord.getIsEnd().equals(CommonConst.IS_END_YES)){
            throw new KrtException(ReturnBean.error("已经撤销"));
        }
        processService.cancelApply(writeoffRecord.getInstanceId(),"用户撤销");
        writeoffRecord.setIsEnd(CommonConst.IS_END_YES);
        writeoffRecord.setIsPass(CommonConst.IS_PASS_SELF);
        writeoffRecordService.updateById(writeoffRecord);
    }

    @Override
    @Transactional
    public void approval(Integer id, User user, HttpServletRequest request) {
        WriteoffRecord writeoffRecord = selectById(id);

        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(writeoffRecord.getInstanceId())
                .list();    // 例如请假会签，会同时拥有多个任务
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
            HashMap<String, Object> param = new HashMap<>();
            param.put("isPass",true);
            param.put("flag",true);
            param.put("comment","");
            param.put("userId",user.getId());
            processService.complete(task.getId(), writeoffRecord.getInstanceId(), writeoffRecord.getTitle(), writeoffRecord.getReason()
                    , TypeEnums.WRITEOFF.name().toLowerCase()
                    , param, request);
        }
    }

    @Override
    @Transactional
    public void reject(Integer id, User user, HttpServletRequest request) {
        WriteoffRecord writeoffRecord = selectById(id);

        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(writeoffRecord.getInstanceId())
                .list();    // 例如请假会签，会同时拥有多个任务
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
            HashMap<String, Object> param = new HashMap<>();
            param.put("isPass",false);
            param.put("flag",false);
            param.put("comment","");
            param.put("userId",user.getId());
            processService.complete(task.getId(), writeoffRecord.getInstanceId(), writeoffRecord.getTitle(), writeoffRecord.getReason()
                    , TypeEnums.WRITEOFF.name().toLowerCase()
                    , param, request);
        }
    }

    @Override
    public List<WriteoffRecordVo> findDoneTasks(Map para, String username) {

        Date date =(Date) para.get("date");

        // 手动分页
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<BizLeaveVo> list = new Page<>();

        List<WriteoffRecord> results = new ArrayList<>();
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
            WriteoffRecord leave2 = writeoffRecordService.selectById(Integer.parseInt(businessKey));
            WriteoffRecord newLeave = new WriteoffRecord();
            BeanUtils.copyProperties(leave2, newLeave);
            Task  task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
            if(task==null){
                if(leave2.getIsPass()==1){
                    newLeave.setTaskName("审核通过");
                }else{
                    newLeave.setTaskName("审核拒绝");
                }
            }else{
                newLeave.setTaskName(task.getName());
            }
            newLeave.setTaskId(instance.getId());
            newLeave.setInsertTime(leave2.getInsertTime());
            newLeave.setDoneTime(instance.getEndTime());
            User user = userService.selectById(leave2.getApplyUser());
            newLeave.setApplyUserName(user.getName());
            results.add(newLeave);
        }

        List<WriteoffRecordVo> vos = this.convertor.convertor(results, WriteoffRecordVo.class);
        for (WriteoffRecordVo vo : vos) {
            vo.setPid(TypeEnums.WRITEOFF.getStatus());
        }
        return vos;
    }
}
