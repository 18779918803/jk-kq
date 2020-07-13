package com.jk.activiti.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.activiti.domain.Leave;
import com.jk.common.base.IBaseService;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * 请假服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月03日
 */
public interface ILeaveService extends IBaseService<Leave> {

    /**
     * 启动流程
     * @param entity
     * @param applyUserId
     * @return
     */

    ProcessInstance submitApply(Leave entity, String applyUserId, String key, Map<String, Object> variables);


    IPage<Leave> findTodoTasks(String username, Map map);

    IPage findDoneTasks(Map para, String username);
}
