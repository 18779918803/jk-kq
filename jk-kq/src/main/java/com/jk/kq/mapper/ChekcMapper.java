package com.jk.kq.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 审核用户申请流程记录
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月03日
 */
@Mapper
public interface ChekcMapper {

    /**
     * 添加申请流程记录
     *
     * @param pid
     * @param id
     * @param openUserId
     * @param checkState
     */
    void addCheckProcess(@Param("pid") Integer pid, @Param("id") Integer id,
                         @Param("openUserId") Long openUserId, @Param("checkState") Integer checkState,
                         @Param("insertTime") Date insertTime);

    /**
     * 获取单个记录的审批流程记录
     *
     * @param pid
     * @param id
     * @return
     */
    List getcurrentRecordDetail(Integer pid, Integer id);

    /**
     * 添加督办申请流程记录
     *
     * @param dbId
     * @param dbDelayId
     * @param dbFeedbackId
     * @param openUserId
     * @param checkState
     * @param date
     */
    void addCheckProcesses(@Param("dbId")Integer dbId, @Param("dbDelayId")Integer dbDelayId,
                           @Param("dbFeedbackId")Integer dbFeedbackId, @Param("id")Integer id,
                           @Param("openUserId")Integer openUserId, @Param("checkState")Integer checkState,
                           @Param("insertTime")Date date);


}
