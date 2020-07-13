package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.entity.WriteoffRecord;
import com.jk.kq.entity.vo.WriteoffRecordVo;
import com.jk.sys.entity.User;
import org.activiti.engine.runtime.ProcessInstance;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 核销申请服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
public interface IWriteoffRecordService extends IBaseService<WriteoffRecord> {

    List selectHistory(Integer userId, String date);

    /**
     * 新增用户核销申请
     *
     * @param openUser
     * @param writeoffRecord
     * @return 返回该申请需要推送的用户的集合
     */
    List<OpenUser> insert(OpenUser openUser, WriteoffRecord writeoffRecord);

    /**
     * 找该用户等级可以审核的核销申请
     *
     * @param queryMap
     * @return
     */
    List selectToCheck(Map queryMap);

    ProcessInstance submitApply(WriteoffRecord entity, String applyUserId, String key, Map<String, Object> variables)  ;


    List<WriteoffRecord> findTodoTasks(String username, Map map);

    void approval(Integer id, User user, HttpServletRequest request);


    void cancel(Integer id, User user, HttpServletRequest request);



    void reject(Integer id, User user, HttpServletRequest request);


    /**
     * 暂时不做分页
     * @param para
     * @param username
     * @return
     */
    List<WriteoffRecordVo> findDoneTasks(Map para, String username);


}
