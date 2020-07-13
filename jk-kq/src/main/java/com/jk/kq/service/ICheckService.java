package com.jk.kq.service;

import com.jk.kq.entity.vo.LeaveRecordVO;
import com.jk.kq.entity.vo.OvertimeRecordVo;
import com.jk.kq.entity.vo.WriteoffRecordVo;

import java.util.Date;
import java.util.List;

/**
 * 审核用户申请
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月03日
 */
public interface ICheckService {

    /**
     * 添加申请流程记录
     *
     * @param pid
     * @param id
     * @param openUserId
     * @param checkState
     */
    void addCheckProcess(Integer pid, Integer id, Long openUserId, Integer checkState, Date insertTime);

    /**
     * 获取单个记录的审批流程记录
     *
     * @param pid
     * @param id
     * @return
     */
    LeaveRecordVO getcurrentRecordDetail(Integer pid, Integer id);

    WriteoffRecordVo getWriteOffDetail(Integer pid, Integer id);

    OvertimeRecordVo getOverTimeDetail(Integer pid, Integer id);
}
