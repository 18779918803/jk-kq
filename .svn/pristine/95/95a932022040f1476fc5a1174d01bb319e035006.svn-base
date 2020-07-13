package com.jk.api.controller;

import com.jk.api.constant.ApiConst;
import com.jk.api.dto.CheckDTO;
import com.jk.api.enums.OffEnums;
import com.jk.api.enums.TypeEnums;
import com.jk.api.enums.WriteEnums;
import com.jk.api.service.ApiService;
import com.jk.api.util.PushMsg;
import com.jk.common.annotation.KrtIgnoreAuth;
import com.jk.common.base.BaseController;
import com.jk.common.bean.ReturnBean;
import com.jk.common.bean.ReturnCode;
import com.jk.kq.common.GeneralConvertor;
import com.jk.kq.entity.LeaveRecord;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.entity.OvertimeRecord;
import com.jk.kq.entity.WriteoffRecord;
import com.jk.kq.entity.vo.CheckVO;
import com.jk.kq.entity.vo.LeaveRecordVO;
import com.jk.kq.entity.vo.OvertimeRecordVo;
import com.jk.kq.entity.vo.WriteoffRecordVo;
import com.jk.kq.service.ICheckService;
import com.jk.kq.service.ILeaveRecordService;
import com.jk.kq.service.IOvertimeRecordService;
import com.jk.kq.service.IWriteoffRecordService;
import com.jk.kq.utils.DateUtil;
import com.jk.kq.utils.TaskUtilService;
import com.jk.sys.entity.User;
import com.jk.sys.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 和领导审核相关的微信小程序API接口
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月02日
 */
@RestController
@RequestMapping("api/apply")
@Slf4j
public class CheckApi extends BaseController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private IOvertimeRecordService overtimeRecordService;

    @Autowired
    private ILeaveRecordService leaveRecordService;

    @Autowired
    private IWriteoffRecordService writeoffRecordService;

    @Autowired
    private ICheckService checkService;

    @Autowired
    private GeneralConvertor convertor;

    @Autowired
    private PushMsg pushMsg;

    @Autowired
    private TaskUtilService taskUtilService;

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param request
     * @param typeId 类型主键
     * @param id 业务表主键
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("cancel")

    public ReturnBean cancel(HttpServletRequest request, Integer typeId,Integer id){
        User user = apiService.getUser(request);
        if (ObjectUtils.isEmpty(typeId) || ObjectUtils.isEmpty(id)){
            return ReturnBean.error(ReturnCode.INVALID_REQUEST);
        }


        cancelActiviti(typeId,id,request,user);

        return ReturnBean.ok();
    }



    private void cancelActiviti(Integer typeId, Integer id, HttpServletRequest request, User user){
        if (TypeEnums.WRITEOFF.getStatus().equals(typeId)) {
            writeoffRecordService.cancel(id, user, request);
        } else if (TypeEnums.LEAVE.getStatus().equals(typeId)) {
            leaveRecordService.cancel(id,user,request);
        } else if (TypeEnums.OVERTIME.getStatus().equals(typeId)) {
            overtimeRecordService.cancel(id,user,request);
        }
    }


    /**
     * 获取所有的审核列表按照日期和搜索条件
     *
     * @return
     */
    @KrtIgnoreAuth
    @GetMapping("list")
    public ReturnBean list(HttpServletRequest request, CheckDTO dto)   {
        //默认typeId 等于0

        List<CheckVO> result = new ArrayList<>();
        User openUser = apiService.getUser(request);
        if (ObjectUtils.isEmpty(dto.getTypePId())) {
            return ReturnBean.error(
                    ReturnCode.INVALID_REQUEST.getCode(), "typeId为空"
            );
        }
        if (ObjectUtils.isEmpty(dto.getDate())) {
            return ReturnBean.error(
                    ReturnCode.INVALID_REQUEST.getCode(), "日期为空"
            );
        }
        Map<String, Object> map = new HashMap<>();
        //查询请假
        if (TypeEnums.LEAVE.getStatus().equals(dto.getTypePId())) {
            map.put("type", TypeEnums.LEAVE.name().toLowerCase());
            //map.put("date", dto.getDate());
            
            List<LeaveRecord> page = leaveRecordService.findTodoTasks(openUser.getId().toString(), map);
            List<CheckVO> convertor = this.convertor.convertor(page, CheckVO.class);
            convertor.forEach(
                    vo -> {
                        if(vo.getTypeId().equals(OffEnums.THING_OFF.getTypeId())){
                            vo.setTypeName(OffEnums.THING_OFF.getTypeName());
                        }else if(vo.getTypeId().equals(OffEnums.YEAR_OFF.getTypeId())){
                            vo.setTypeName(OffEnums.YEAR_OFF.getTypeName());
                        }
                        if( vo.getTimeId() == 1 ){
                            vo.setTimeName("上午");
                        }else if( vo.getTimeId() == 2 ){
                            vo.setTimeName("下午");
                        }
                        vo.setPid(TypeEnums.LEAVE.getStatus());
                        vo.setApplyUser(vo.getName());
                        vo.setStartDate(vo.getStartDate().substring(0, 10));
                        vo.setEndDate(vo.getEndDate().substring(0, 10));
                    }
            );
            result.addAll(convertor);
            ListSort(result);
            return ReturnBean.ok(result);
        } else if (TypeEnums.WRITEOFF.getStatus().equals(dto.getTypePId())) {
            map.put("type", TypeEnums.WRITEOFF.name().toLowerCase());
            //map.put("date", dto.getDate());
            List<WriteoffRecord> todoTasks = writeoffRecordService.findTodoTasks(openUser.getId().toString(), map);
            List<CheckVO> convertor = this.convertor.convertor(todoTasks, CheckVO.class);
            convertor.forEach(
                    vo -> {
                        if(vo.getTypeId().equals(WriteEnums.MORNING_FREE.getTypeId())){
                            vo.setTypeName(WriteEnums.MORNING_FREE.getTypeName());
                        }else if(vo.getTypeId().equals(WriteEnums.NOON_FREE.getTypeId())){
                            vo.setTypeName(WriteEnums.NOON_FREE.getTypeName());
                        }
                        vo.setPid(TypeEnums.WRITEOFF.getStatus());
                        vo.setApplyUser(vo.getName());
                        vo.setDate(vo.getDate().substring(0, 10));
                    }
            );
            result.addAll(convertor);
//            ListSort(result);
            return ReturnBean.ok(result);
        }else if (TypeEnums.OVERTIME.getStatus().equals(dto.getTypePId())){
            map.put("type", TypeEnums.OVERTIME.name().toLowerCase());
            //map.put("date", dto.getDate());
            List<OvertimeRecord> todoTasks = overtimeRecordService.findTodoTasks(openUser.getId().toString(), map);
            List<CheckVO> convertor = this.convertor.convertor(todoTasks, CheckVO.class);
            convertor.forEach(
                    vo -> {
                        vo.setPid(TypeEnums.OVERTIME.getStatus());
                        vo.setApplyUser(vo.getName());
                        vo.setDate(vo.getDate().substring(0, 10));
                    }
            );
            result.addAll(convertor);
            ListSort(result);
            return ReturnBean.ok(result);
        }else if (TypeEnums.ALL.getStatus().equals(dto.getTypePId())){
            map.put("type", TypeEnums.LEAVE.name().toLowerCase());
            List<LeaveRecord> page = leaveRecordService.findTodoTasks(openUser.getId().toString(), map);
            List<CheckVO> convertor = this.convertor.convertor(page, CheckVO.class);
            convertor.forEach(
                    vo -> {
                        if(vo.getTypeId().equals(OffEnums.THING_OFF.getTypeId())){
                            vo.setTypeName(OffEnums.THING_OFF.getTypeName());
                        }else if(vo.getTypeId().equals(OffEnums.YEAR_OFF.getTypeId())){
                            vo.setTypeName(OffEnums.YEAR_OFF.getTypeName());
                        }
                        if( vo.getTimeId() == 1 ){
                            vo.setTimeName("上午");
                        }else if( vo.getTimeId() == 2 ){
                            vo.setTimeName("下午");
                        }
                        vo.setPid(TypeEnums.LEAVE.getStatus());
                        vo.setApplyUser(vo.getName());
                        vo.setStartDate(vo.getStartDate().substring(0, 10));
                        vo.setEndDate(vo.getEndDate().substring(0, 10));
                    }
            );
            result.addAll(convertor);
            map.put("type", TypeEnums.WRITEOFF.name().toLowerCase());
            List<WriteoffRecord> todoTasks = writeoffRecordService.findTodoTasks(openUser.getId().toString(), map);
            List<CheckVO> write = this.convertor.convertor(todoTasks, CheckVO.class);
            write.forEach(
                    vo -> {
                        if(vo.getTypeId().equals(WriteEnums.MORNING_FREE.getTypeId())){
                            vo.setTypeName(WriteEnums.MORNING_FREE.getTypeName());
                        }else if(vo.getTypeId().equals(WriteEnums.NOON_FREE.getTypeId())){
                            vo.setTypeName(WriteEnums.NOON_FREE.getTypeName());
                        }
                        vo.setPid(TypeEnums.WRITEOFF.getStatus());
                        vo.setApplyUser(vo.getName());
                        vo.setDate(vo.getDate().substring(0, 10));
                    }
            );
            result.addAll(write);

            map.put("type", TypeEnums.OVERTIME.name().toLowerCase());
            List<OvertimeRecord> overtimeRecords = overtimeRecordService.findTodoTasks(openUser.getId().toString(), map);
            List<CheckVO> overtime = this.convertor.convertor(overtimeRecords, CheckVO.class);
            overtime.forEach(
                    vo -> {
                        vo.setPid(TypeEnums.OVERTIME.getStatus());
                        vo.setApplyUser(vo.getName());
                        vo.setDate(vo.getDate().substring(0, 10));
                    }
            );
            result.addAll(overtime);

            ListSort(result);
            return ReturnBean.ok(result);
        }



        return ReturnBean.ok();

    }

    /**
     * 获取单个记录的审批流程记录
     *
     * @param pid 类型id typeenums
     * @param id  业务主键
     * @return
     */
    @KrtIgnoreAuth
    @GetMapping("detail")
    public ReturnBean detail(@RequestParam("id") Integer id, @RequestParam("pid") Integer pid) {
        if (TypeEnums.LEAVE.getStatus().equals(pid)) {
            LeaveRecordVO leaveRecordVO = checkService.getcurrentRecordDetail(pid, id);
            return ReturnBean.ok(leaveRecordVO);
        } else if (TypeEnums.WRITEOFF.getStatus().equals(pid)) {
            WriteoffRecordVo writeOffDetail = checkService.getWriteOffDetail(pid, id);
            return ReturnBean.ok(writeOffDetail);
        }else if (TypeEnums.OVERTIME.getStatus().equals(pid)) {
            OvertimeRecordVo overTimeDetail = checkService.getOverTimeDetail(pid, id);
            return ReturnBean.ok(overTimeDetail);
        }
        return ReturnBean.ok();
    }

    @KrtIgnoreAuth
    @PostMapping("passBatch")
    public ReturnBean passBatch(@RequestParam("ids") String ids, @RequestParam("typeIds") String typeIds) {
        User user = apiService.getUser(request);
        OpenUser openUser = apiService.getOpenUser(request);

        String[] idArray = ids.split(",");
        String[] typeIdArray = typeIds.split(",");

        for (int i = 0; i < idArray.length; i++) {
            Integer id = Integer.valueOf(idArray[i]);
            Integer typeId = Integer.valueOf(typeIdArray[i]);
            passOne(typeId, id, user, request,openUser);
        }

        return ReturnBean.ok();
    }

    /**
     * 审批单一通过
     * @param typeId 类型
     * @param id
     * @param user
     * @param request
     */
    @Transactional
    void passOne(Integer typeId, Integer id, User user, HttpServletRequest request,OpenUser openUser){
        if (TypeEnums.WRITEOFF.getStatus().equals(typeId)) {
            writeoffRecordService.approval(id, user, request);
            WriteoffRecord writeoffRecord = writeoffRecordService.selectById(id);
            Map<String, Integer> end = taskUtilService.isEnd(writeoffRecord.getInstanceId());
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            writeoffRecord.setIsPass(isPass);
            writeoffRecord.setIsEnd(isEnd);
            writeoffRecordService.updateById(writeoffRecord);
            List<User> ids = apiService.nextHandel(writeoffRecord.getInstanceId());
            log.error("{}进行核销审批,当前状态:{},{}",user.getName(),isEnd,isPass);
            pushMsg.push(ids,"核销申请","通过");
            if (isEnd.equals(ApiConst.APP_YES) && isPass.equals(ApiConst.APP_YES)){
                Integer userId = writeoffRecord.getUserId();
                List<User> users = Arrays.asList(userMapper.selectById(userId));
                log.error("{核销审批通过,当前id{},当前状态{},{}",userId,isEnd,isPass);
                pushMsg.finish(users,"加班申请");

                if(writeoffRecord.getTypeId().equals(WriteEnums.MORNING_FREE.getTypeId())){
                    apiService.updateClock(writeoffRecord.getDate(),ApiConst.MORNING_YES,openUser.getId());
                }else if(writeoffRecord.getTypeId().equals(WriteEnums.NOON_FREE.getTypeId())){
                    apiService.updateClock(writeoffRecord.getDate(),ApiConst.NOON_LATE,openUser.getId());
                }

            }

        } else if (TypeEnums.LEAVE.getStatus().equals(typeId)) {
            leaveRecordService.approval(id, user, request);
            LeaveRecord leaveRecord = leaveRecordService.selectById(id);
            Map<String, Integer> end = taskUtilService.isEnd(leaveRecord.getInstanceId());
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            leaveRecord.setIsPass(isPass);
            leaveRecord.setIsEnd(isEnd);
            leaveRecordService.updateById(leaveRecord);
            List<User> ids = apiService.nextHandel(leaveRecord.getInstanceId());
            pushMsg.push(ids,"请假申请","通过");
            if (isEnd.equals(ApiConst.APP_YES) && isPass.equals(ApiConst.APP_YES)){
                Integer userId = leaveRecord.getUserId();
                List<User> users = Arrays.asList(userMapper.selectById(userId));
                log.error("{请假审批通过,当前id{},当前状态{},{}",userId,isEnd,isPass);
                pushMsg.finish(users,"请假申请");

                Date startDate = leaveRecord.getStartDate();
                Date endDate = leaveRecord.getEndDate();
                int i = DateUtil.differentDays(startDate, endDate);
                while (i >= 0){
                    apiService.updateClock(startDate,ApiConst.ALL_YES,openUser.getId());
                    i = i -1;
                    startDate = DateUtil.dateAdd("day",1,startDate);
                }
            }


        } else if (TypeEnums.OVERTIME.getStatus().equals(typeId)) {
            overtimeRecordService.approval(id, user, request);
            OvertimeRecord overtimeRecord = overtimeRecordService.selectById(id);
            Map<String, Integer> end = taskUtilService.isEnd(overtimeRecord.getInstanceId());
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            overtimeRecord.setIsPass(isPass);
            overtimeRecord.setIsEnd(isEnd);
            overtimeRecordService.updateById(overtimeRecord);
            List<User> ids = apiService.nextHandel(overtimeRecord.getInstanceId());
            pushMsg.push(ids,"加班申请","通过");
            if (isEnd.equals(ApiConst.APP_YES) && isPass.equals(ApiConst.APP_YES)){
                Integer userId = overtimeRecord.getUserId();
                List<User> users = Arrays.asList(userMapper.selectById(userId));
                log.error("{加班审批通过,当前id{},当前状态{},{}",userId,isEnd,isPass);
                pushMsg.finish(users,"加班申请");
            }
        }
    }

    /**
     * 通过申请，也要推送用户，如果审批结束，推送给申请人，审批未结束，推送给下一个审批人
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("pass")
    public ReturnBean pass(@RequestBody Map map) {
        User user = apiService.getUser(request);
        OpenUser openUser = apiService.getOpenUser(request);
        Integer pid = (Integer) map.get("pid");
        Integer id = (Integer) map.get("id");
        if (ObjectUtils.isEmpty(map.get("id"))) {
            log.error("id为空");
            return ReturnBean.error(ReturnCode.INVALID_REQUEST);
        }
        if (ObjectUtils.isEmpty(map.get("pid"))) {
            log.error("pid为空");
            return ReturnBean.error(ReturnCode.INVALID_REQUEST);
        }
        passOne(pid,id,user,request,openUser);
        return ReturnBean.ok();
    }

    /**
     * 拒绝申请，也要推送用户，申请拒绝，直接结束审批流程，推送消息给申请人
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("reject")
    public ReturnBean reject(@RequestBody Map map)   {
        User openUser = apiService.getUser(request);
        Integer pid = (Integer) map.get("pid");
        Integer id = (Integer) map.get("id");
        if (ObjectUtils.isEmpty(map.get("id"))) {
            log.error("id为空");
            return ReturnBean.error(ReturnCode.INVALID_REQUEST);
        }
        if (ObjectUtils.isEmpty(map.get("pid"))) {
            log.error("pid为空");
            return ReturnBean.error(ReturnCode.INVALID_REQUEST);
        }
        if (TypeEnums.WRITEOFF.getStatus().equals(pid)) {
            writeoffRecordService.reject(id, openUser, request);
            WriteoffRecord writeoffRecord = writeoffRecordService.selectById(id);
            Map<String, Integer> end = taskUtilService.isEnd(writeoffRecord.getInstanceId());
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            writeoffRecord.setIsPass(isPass);
            writeoffRecord.setIsEnd(isEnd);
            writeoffRecordService.updateById(writeoffRecord);
            log.error("isEnd:{},isPass:{}",isEnd,isPass);
            if (isEnd.equals(ApiConst.APP_YES) && isPass.equals(ApiConst.REJECT_NO)){
                Integer userId = writeoffRecord.getUserId();
                List<User> users = Arrays.asList(userMapper.selectById(userId));
                log.error("核销拒绝,isEnd:{},isPass:{}",isEnd,isPass);
                pushMsg.reject(users,"核销申请");
            }

        } else if (TypeEnums.LEAVE.getStatus().equals(pid)) {
            leaveRecordService.reject(id, openUser, request);
            LeaveRecord leaveRecord = leaveRecordService.selectById(id);
            Map<String, Integer> end = taskUtilService.isEnd(leaveRecord.getInstanceId());
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            leaveRecord.setIsPass(isPass);
            leaveRecord.setIsEnd(isEnd);
            log.error("isEnd:{},isPass:{}",isEnd,isPass);

            leaveRecordService.updateById(leaveRecord);
            if (isEnd.equals(ApiConst.APP_YES) && isPass.equals(ApiConst.REJECT_NO)){
                Integer userId = leaveRecord.getUserId();
                List<User> users = Arrays.asList(userMapper.selectById(userId));
                log.error("请假拒绝,isEnd:{},isPass:{}",isEnd,isPass);

                pushMsg.reject(users,"请假申请");
            }
        } else if (TypeEnums.OVERTIME.getStatus().equals(pid)) {
            overtimeRecordService.reject(id, openUser, request);
            OvertimeRecord overtimeRecord = overtimeRecordService.selectById(id);
            Map<String, Integer> end = taskUtilService.isEnd(overtimeRecord.getInstanceId());
            Integer isEnd = end.get("isEnd");
            Integer isPass = end.get("isPass");
            overtimeRecord.setIsPass(isPass);
            overtimeRecord.setIsEnd(isEnd);
            log.error("isEnd:{},isPass:{}",isEnd,isPass);
            overtimeRecordService.updateById(overtimeRecord);
            if (isEnd.equals(ApiConst.APP_YES) && isPass.equals(ApiConst.REJECT_NO)){
                Integer userId = overtimeRecord.getUserId();
                List<User> users = Arrays.asList(userMapper.selectById(userId));

                log.error("加班拒绝,isEnd:{},isPass:{}",isEnd,isPass);

                pushMsg.reject(users,"加班申请");
            }
        }
        return ReturnBean.ok();
    }

    private static void ListSort(List<CheckVO> list) {
        //定义一个比较器
        list.sort((o1, o2) -> {
            try {
                Date dt1 = o1.getInsertTime();
                Date dt2 = o2.getInsertTime();
                return Long.compare(dt1.getTime(), dt2.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });
    }
}
