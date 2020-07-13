package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.entity.OvertimeRecord;
import com.jk.kq.entity.dto.OverTimeRecordDTO;
import com.jk.kq.entity.vo.OvertimeRecordVo;
import com.jk.sys.entity.User;
import org.activiti.engine.runtime.ProcessInstance;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 加班申请服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
public interface IOvertimeRecordService extends IBaseService<OvertimeRecord> {

    /**
     * 通过OpenUserId查找当月用户的加班记录
     *
     * @param userId
     * @param date
     * @return
     */
    List selectHistory(Integer userId, String date);

    /**
     * 新增加班申请记录
     * 在这里应该查找自己的上一级领导，将待审核的模板消息推送给他
     *
     * @param openUser
     * @param overtimeRecord
     * @return  返回该申请需要推送的用户的集合
     */
    List<OpenUser> insert(OpenUser openUser, OverTimeRecordDTO overtimeRecord);

    /**
     * 查找用户可以审核的加班申请
     *
     * @param queryMap
     */
    List selectToCheck(Map queryMap);


    ProcessInstance submitApply(OvertimeRecord entity, String applyUserId, String key, Map<String, Object> variables)  ;


    List<OvertimeRecord> findTodoTasks(String username, Map map);


    void approval(Integer id, User user, HttpServletRequest request);


    void cancel(Integer id, User user, HttpServletRequest request);

    void reject(Integer id, User user, HttpServletRequest request);

    /**
     * 暂时不做分页
     * @param para
     * @param username
     * @return
     */
    List<OvertimeRecordVo> findDoneTasks(Map para, String username);


}
