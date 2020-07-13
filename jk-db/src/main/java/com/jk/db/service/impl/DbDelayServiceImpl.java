package com.jk.db.service.impl;

import com.jk.activiti.service.IProcessService;
import com.jk.activiti.utils.StringUtils;
import com.jk.common.base.BaseServiceImpl;
import com.jk.kq.enums.TypeEnums;
import com.jk.sys.entity.User;
import com.jk.sys.service.IUserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.db.entity.DbDelay;
import com.jk.db.mapper.DbDelayMapper;
import com.jk.db.service.IDbDelayService;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 督查督办延期（撤销）申请表服务接口实现层
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月10日
 */
@Service
public class DbDelayServiceImpl extends BaseServiceImpl<DbDelayMapper, DbDelay> implements IDbDelayService {

    @Autowired
    private DbDelayMapper dbDelayMapper;

    @Autowired
    private IProcessService processService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IUserService userService;

    @Override
    public ProcessInstance submitApply(DbDelay entity, String applyUserId, String key, HashMap objectObjectHashMap)   {
        entity.setApplyUser(applyUserId);
        entity.setPid(6);
        dbDelayMapper.updateById(entity);
        String businessKey = entity.getId().toString(); // 实体类 ID，作为流程的业务 key
        String dbLevel = entity.getDbLevel();
        Integer level = null;
        if("A(非常重要)".equals(dbLevel)||"B(重要)".equals(dbLevel)){
            level = 1;
        }else{
            level = 3;
        }
        objectObjectHashMap.put("level",level);
        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, "建控集团督查督办事项延期（撤销）申请", entity.getDbContentBrief(), key, objectObjectHashMap);
        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        dbDelayMapper.updateById(entity);
        return processInstance;
    }

    @Override
    public List<DbDelay> findTodoTasks(String username, Map param) {
        List<DbDelay> results = new ArrayList<>();
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
            DbDelay dbDelay2 = dbDelayMapper.selectById(Integer.parseInt(businessKey));
            // 条件过滤 2
            //if (StringUtils.isNotBlank((String) param.get("type")) && !param.get("type").equals(leave2.getType())) {
            //    continue;
            //}
            dbDelay2.setTaskId(taskImpl.getId());
            if (taskImpl.getSuspensionState() == 2) {
                dbDelay2.setTaskName("已挂起");
            } else {
                dbDelay2.setTaskName(taskImpl.getName());
            }
            User user = userService.selectById(dbDelay2.getApplyUser());
            dbDelay2.setApplyUserName(user.getName());
            results.add(dbDelay2);
        }

        //page.setRecords(results);

        return results;
    }

    @Override
    public void approval(Integer id, User openUser, HttpServletRequest request) {
        DbDelay dbDelay = selectById(id);
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(dbDelay.getInstanceId())
                .list();    // 例如请假会签，会同时拥有多个任务
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("isPass",true);
        param.put("flag",true);
        param.put("comment","");
        param.put("userId",openUser.getId());
        //processService.complete(taskList.get(0).getId(), dbDelay.getInstanceId(), "建控集团督察督办事项延期（撤销）",dbDelay.getDbContentBrief()
        //        , TypeEnums.DBDELAY.name().toLowerCase()
        //        , param, request);
    }

    @Override
    public void reject(Integer id, User user, HttpServletRequest request) {
        DbDelay dbDelay = selectById(id);

        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(dbDelay.getInstanceId())
                .list();    // 例如请假会签，会同时拥有多个任务
        if (!CollectionUtils.isEmpty(taskList)) {
            TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
            HashMap<String, Object> param = new HashMap<>();
            param.put("isPass",false);
            param.put("flag",false);
            param.put("comment","");
            param.put("userId",user.getId());
            processService.complete(task.getId(), dbDelay.getInstanceId(), dbDelay.getDbContentBrief(), dbDelay.getDbSource()
                    , TypeEnums.DBDELAY.name().toLowerCase()
                    , param, request);
        }
    }
}
