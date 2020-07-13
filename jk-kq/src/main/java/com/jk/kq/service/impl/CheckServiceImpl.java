package com.jk.kq.service.impl;

import com.jk.activiti.domain.HistoricActivity;
import com.jk.kq.common.GeneralConvertor;
import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.entity.OvertimeRecord;
import com.jk.kq.entity.WorkType;
import com.jk.kq.entity.WriteoffRecord;
import com.jk.kq.entity.vo.LeaveRecordVO;
import com.jk.kq.entity.vo.OvertimeRecordVo;
import com.jk.kq.entity.vo.WriteoffRecordVo;
import com.jk.kq.enums.OffEnums;
import com.jk.kq.mapper.ChekcMapper;
import com.jk.kq.service.*;
import com.jk.kq.utils.TaskUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 审核用户申请
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月03日
 */
@Service
public class CheckServiceImpl implements ICheckService {

    @Autowired
    private ChekcMapper chekcMapper;

    @Autowired
    private ILeaveRecordService leaveRecordService;

    @Autowired
    private GeneralConvertor convertor;

    @Autowired
    private TaskUtilService taskUtilService;

    @Autowired
    private IWorkTypeService workTypeService;

    @Autowired
    private IWriteoffRecordService writeoffRecordService;


    @Autowired
    private IOvertimeRecordService overtimeRecordService;

    /**
     * 添加申请流程记录
     *
     * @param pid
     * @param id
     * @param openUserId
     * @param checkState
     */
    @Override
    public void addCheckProcess(Integer pid, Integer id, Long openUserId, Integer checkState, Date insertTime) {
        chekcMapper.addCheckProcess(pid, id, openUserId, checkState,insertTime);
    }

    /**
     * 获取单个记录的审批流程记录
     * @param pid
     * @param id
     * @return
     */
    @Override
    public LeaveRecordVO getcurrentRecordDetail(Integer pid, Integer id) {
            LeaveRecord leaveRecord = leaveRecordService.selectById(id);
            Integer typeId = leaveRecord.getTypeId();
            LeaveRecordVO result = this.convertor.convertor(leaveRecord, LeaveRecordVO.class);
            if(typeId.equals(OffEnums.THING_OFF.getTypeId())){
                result.setTypeName(OffEnums.THING_OFF.getTypeName());
            }else if(typeId.equals(OffEnums.YEAR_OFF.getTypeId())){
                result.setTypeName(OffEnums.YEAR_OFF.getTypeName());
            }
            Map<String, Integer> end = taskUtilService.isEnd(result.getInstanceId());
            result.setIsEnd(end.get("isEnd"));
            result.setIsPass(end.get("isPass"));
            List<HistoricActivity> approvalList = taskUtilService.getApprovalList(result.getInstanceId(),
                    null, null);
            result.setHis(approvalList);
            return result;
    }

    @Override
    public WriteoffRecordVo getWriteOffDetail(Integer pid, Integer id) {
        WriteoffRecord writeoffRecord = writeoffRecordService.selectById(id);
        Integer typeId = writeoffRecord.getTypeId();
        WorkType workType = workTypeService.selectById(typeId);
        WriteoffRecordVo result = this.convertor.convertor(writeoffRecord, WriteoffRecordVo.class);
        Map<String, Integer> end = taskUtilService.isEnd(result.getInstanceId());
        result.setIsEnd(end.get("isEnd"));
        result.setIsPass(end.get("isPass"));
        List<HistoricActivity> approvalList = taskUtilService.getApprovalList(result.getInstanceId(),
                null, null);
        result.setHis(approvalList);
        result.setTypeName(workType.getName());
        return result;
    }


    @Override
    public OvertimeRecordVo getOverTimeDetail(Integer pid, Integer id) {
        OvertimeRecord record = overtimeRecordService.selectById(id);
        Integer typeId = record.getTypeId();
        WorkType workType = workTypeService.selectById(typeId);
        OvertimeRecordVo result = this.convertor.convertor(record, OvertimeRecordVo.class);
        Map<String, Integer> end = taskUtilService.isEnd(result.getInstanceId());
        result.setIsEnd(end.get("isEnd"));
        result.setIsPass(end.get("isPass"));
        List<HistoricActivity> approvalList = taskUtilService.getApprovalList(result.getInstanceId(),
                null, null);
        result.setHis(approvalList);
        result.setTypeName(workType.getName());
        return result;
    }



}
