package com.jk.db.service;

import com.jk.common.base.IBaseService;
import com.jk.db.entity.DbDelay;
import com.jk.sys.entity.User;
import org.activiti.engine.runtime.ProcessInstance;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 督查督办延期（撤销）申请表服务接口层
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月10日
 */
public interface IDbDelayService extends IBaseService<DbDelay> {


    ProcessInstance submitApply(DbDelay dbDelay, String applyUserId, String key, HashMap<Object, Object> objectObjectHashMap)  ;

    List<DbDelay> findTodoTasks(String username, Map map);

    void approval(Integer id, User openUser, HttpServletRequest request);

    void reject(Integer id, User openUser, HttpServletRequest request);
}
