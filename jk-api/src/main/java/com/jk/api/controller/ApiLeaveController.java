package com.jk.api.controller;

import com.jk.api.enums.TypeEnums;
import com.jk.api.service.ApiService;
import com.jk.api.util.PushMsg;
import com.jk.common.annotation.KrtIgnoreAuth;
import com.jk.common.base.BaseController;
import com.jk.common.bean.ReturnBean;
import com.jk.common.bean.ReturnCode;
import com.jk.common.exception.KrtException;
import com.jk.kq.common.GeneralConvertor;
import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.service.ILeaveRecordService;
import com.jk.kq.service.IWorkTypeService;
import com.jk.kq.utils.TaskUtilService;
import com.jk.sys.entity.Organ;
import com.jk.sys.entity.User;
import com.jk.sys.service.IOrganService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请假记录相关的微信小程序API接口
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@RestController
@RequestMapping("api/leave")
@Slf4j
public class ApiLeaveController extends BaseController {

    @Autowired
    private ILeaveRecordService leaveRecordService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private IWorkTypeService workTypeService;

    @Autowired
    private  ILeaveRecordService  recordService;

    @Autowired
    private GeneralConvertor convertor;

    @Autowired
    private IOrganService organService;

    @Autowired
    TaskUtilService taskUtilService;

    @Autowired
    private PushMsg pushMsg;


    /**
     * 查找所有的请假记录，可以按照月份查找
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("list")
    public ReturnBean list(@RequestParam("date") String date)   {
        User user = apiService.getUser(request);
        List<Map> leaveRecords = leaveRecordService.selectHistory(user.getId(), date);
        for (Map leaveRecord : leaveRecords) {
            String instanceId = (String)leaveRecord.get("instanceId");
            Map<String, Integer> end = taskUtilService.isEnd(instanceId);
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            Integer typeId = (Integer) leaveRecord.get("typeId");
            if (isEnd.equals(2)){
                leaveRecord.put("statusName", "等待审批");
            }else {
                if( isPass.equals(1) ) {
                    leaveRecord.put("statusName", "审批通过");
                }else{
                    leaveRecord.put("statusName", "审批拒绝");
                }
            }
            if( typeId == 1 ){
                leaveRecord.put("typeName", "事假");
            }else if( typeId == 2 ){
                leaveRecord.put("typeName", "年假");
            }
            leaveRecord.put("pid",TypeEnums.LEAVE.getStatus());
            leaveRecord.remove("instanceId");
        }
        return ReturnBean.ok(leaveRecords);
    }

//    /**
//     * 查找所有的请假记录类型
//     * @return
//     */
//    @KrtIgnoreAuth
//    @GetMapping("type")
//    public ReturnBean types() {
//        List<WorkType> leaveTypes = workTypeService.selectByPid(WorkTypeConfig.LEAVE);
//        return ReturnBean.ok(leaveTypes);
//    }

    /**
     * 新增用户请假申请
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("insert")
    @Transactional
    public ReturnBean insert(
                             @RequestParam("typeId") Integer typeId,
                             @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                             @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                             @RequestParam("day") BigDecimal day,
                             @RequestParam("time") Integer timeId,
                             @RequestParam("reason") String reason)   {
        User user = apiService.getUser(request);
        OpenUser openUser = apiService.getOpenUser(request);
        int leave = apiService.selectRole(user.getRoles());
        //判断角色
        if (leave == 0){
            throw new KrtException(ReturnBean.error(ReturnCode.INVALID_REQUEST.getCode(),
                    "发起申请人角色无定义"));
        }
        //判断当前用户的角色
        Map<String,Object> map =new HashMap<>();
        map.put("leave",leave);
        map.put("day",day);
        LeaveRecord leaveRecord = new LeaveRecord();
        Organ organ = organService.selectById(user.getOrganId());
        if(organ.getOrganType()==1){
            map.put("isDept",true);
        }else{
            map.put("isDept",false);
        }
        leaveRecord.setUserId(user.getId());
        leaveRecord.setName(user.getName());
        leaveRecord.setOrgan(organ.getName());
        leaveRecord.setOrganId(user.getOrganId());
        leaveRecord.setTypeId(typeId);
        leaveRecord.setStartDate(startDate);
        leaveRecord.setEndDate(endDate);
        leaveRecord.setDay(day);
        leaveRecord.setTimeId(timeId);
        leaveRecord.setReason(reason);
        leaveRecord.setInserter(openUser.getId());
        recordService.insert(leaveRecord);
        recordService.submitApply(leaveRecord, user.getId().toString(),"leave", map);
        LeaveRecord existLeaveRecord = recordService.selectById(leaveRecord.getId());
        List<User> ids = apiService.nextHandel(existLeaveRecord.getInstanceId());
        pushMsg.push(ids,"请假申请", reason);
        return ReturnBean.ok();
    }
}
