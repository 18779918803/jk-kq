package com.jk.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.activiti.mapper.BizTodoItemMapper;
import com.jk.api.constant.ApiConst;
import com.jk.api.dto.SessionDTO;
import com.jk.api.enums.RoleEnums;
import com.jk.api.service.ApiService;
import com.jk.kq.entity.Clock;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.mapper.ClockMapper;
import com.jk.kq.service.IOpenUserService;
import com.jk.kq.utils.DateUtil;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.User;
import com.jk.sys.entity.UserRole;
import com.jk.sys.service.IRoleService;
import com.jk.sys.service.IUserRoleService;
import com.jk.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月27日
 */
@Service
@Slf4j
public class ApiServiceImpl implements ApiService {


    @Autowired
    private IOpenUserService openUserService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleService roleService;


    @Autowired
    private BizTodoItemMapper bizTodoItemMapper;


    @Autowired
    private TaskService taskService;

    @Autowired
    private ClockMapper clockMapper;







    @Override
    public List<User> nextHandel(String instanceId){
        List<User> result = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).active().list();
        if (ObjectUtils.isEmpty(taskList)){
            return null;
        }
        for (Task task : taskList) {
            String assignee = task.getAssignee();
            if (StringUtils.isNotBlank(assignee)) {
                User user = userService.selectById(assignee);
                result.add(user);
            } else {
                // 查询候选用户组
                List<String> todoUserIdList = bizTodoItemMapper.selectTodoUserListByTaskId(task.getId());
                if (!CollectionUtils.isEmpty(todoUserIdList)) {
                    for (String todoUserId : todoUserIdList) {
                        User user = userService.selectById(todoUserId);
                        result.add(user);
                    }
                } else {
                    // 查询候选用户
                    List<String> list = bizTodoItemMapper.selectTodoUserByTaskId(task.getId());
                    for (String todoUserId : list) {
                        User user = userService.selectById(todoUserId);
                        result.add(user);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void updateClock(Date date, int flag,int userId) {

        log.error("系统正在更改考勤记录:{},{},{}",date,flag,userId);

        LocalDate localDate = DateUtil.date2LocalDate(date);
        LocalDateTime o1 = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime o2 = LocalDateTime.of(localDate, LocalTime.MAX);

        Date up = DateUtil.getDateDay(date,8, 30);
        Date late = DateUtil.getDateDay(date,17, 30);
        if (flag == ApiConst.MORNING_YES) {
            QueryWrapper<Clock> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("open_user_id", userId);
            queryWrapper.ge("time", o1);
            queryWrapper.le("time", o2);
            queryWrapper.eq("type", 1);
            Clock clock = clockMapper.selectOne(queryWrapper);
            if (!ObjectUtils.isEmpty(clock)) {
                clock.setType(ApiConst.TYPE_1);
                clock.setWay(ApiConst.WAY_2);
                clock.setState(ApiConst.STATE_0);
                clock.setTime(up);
                clockMapper.updateById(clock);
            }
        }
        if (flag == ApiConst.NOON_LATE) {
            QueryWrapper<Clock> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("open_user_id", userId);
            queryWrapper.ge("time", o1);
            queryWrapper.le("time", o2);
            queryWrapper.eq("type", 2);
            Clock clock = clockMapper.selectOne(queryWrapper);
            if (!ObjectUtils.isEmpty(clock)) {
                clock.setType(ApiConst.TYPE_2);
                clock.setWay(ApiConst.WAY_2);
                clock.setState(ApiConst.STATE_0);
                clock.setTime(late);
                clockMapper.updateById(clock);
            }
        }
        if (flag == ApiConst.ALL_YES){
                Clock upClock = new Clock();
                upClock.setOpenUserId(userId);
                upClock.setType(ApiConst.TYPE_1);
                upClock.setWay(ApiConst.WAY_2);
                upClock.setState(ApiConst.STATE_0);
                upClock.setTime(up);
                clockMapper.insert(upClock);
                Clock lateClock = new Clock();
                lateClock.setOpenUserId(userId);
                lateClock.setType(ApiConst.TYPE_2);
                lateClock.setWay(ApiConst.WAY_2);
                lateClock.setState(ApiConst.STATE_0);
                lateClock.setTime(late);
                clockMapper.insert(lateClock);
        }
    }


    @Override
    public String getThirdSession(HttpServletRequest request){
        String thirdSession = (String) request.getAttribute(ApiConst.THIRD_SESSION);
        return thirdSession;
    }

//    @Override
//    public SessionDTO getOpenUser(HttpServletRequest request)   {
//        SessionDTO sessionDTO = (SessionDTO) request.getAttribute(ApiConst.THIRD_SESSION);
//        if (!ObjectUtils.isEmpty(sessionDTO.getUser())){
//            User user = userService.selectById(sessionDTO.getUser().getId());
//            if (ObjectUtils.isEmpty(user)){
//                throw new KqException(ReturnBean.error(ReturnCode.NOT_LOGIN));
//            }
//            QueryWrapper<UserRole> qu = new QueryWrapper<>();
//            qu.eq("user_id",user.getId());
//            List<UserRole> userRoles = userRoleService.selectList(qu);
//            List<Role> roles = new ArrayList<>();
//            for (UserRole userRole : userRoles) {
//                Integer roleId = userRole.getRoleId();
//                Role role = roleService.selectById(roleId);
//                roles.add(role);
//            }
//            user.setRoles(roles);
//        }
//        return sessionDTO;
//    }

    @Override
    public OpenUser getOpenUser(HttpServletRequest request){
        SessionDTO sessionDTO = (SessionDTO) request.getAttribute(ApiConst.THIRD_SESSION);
        return sessionDTO.getOpenUser();
    }

    @Override
    public User getUser(HttpServletRequest request){
        SessionDTO sessionDTO = (SessionDTO) request.getAttribute(ApiConst.THIRD_SESSION);
        User result = null;
        if (ObjectUtils.isEmpty(sessionDTO.getUser())){
            OpenUser openUser = sessionDTO.getOpenUser();
            result= userService.selectById(openUser.getUserId());
            if (ObjectUtils.isEmpty(result)){
                return null;
            }
            QueryWrapper<UserRole> qu = new QueryWrapper<>();
            qu.eq("user_id",result.getId());
            List<UserRole> userRoles = userRoleService.selectList(qu);
            List<Role> roles = new ArrayList<>();
            for (UserRole userRole : userRoles) {
                Integer roleId = userRole.getRoleId();
                Role role = roleService.selectById(roleId);
                roles.add(role);
            }
            result.setRoles(roles);
        }
        return result;
    }

    @Override
    public SessionDTO selectSession(HttpServletRequest request, String thirdSession) {
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(thirdSession);
        if( sessionDTO == null ) {
            OpenUser openUser = openUserService.selectOne(new QueryWrapper<OpenUser>().eq("third_session", thirdSession));
            if( openUser == null ){
                return null;
            }
            sessionDTO = new SessionDTO();
            sessionDTO.setOpenUser(openUser);
            request.getSession().setAttribute(thirdSession, sessionDTO);
        }
        return sessionDTO;
    }

    /**
     * 返回审批等级
     * @param roles
     * @return
     */
    @Override
    public int selectRole(List<Role> roles){
        int level = 0;
        for (Role role : roles) {
            if (role.getName().equals(RoleEnums.PU_TONG.getStatus())){
                level = 2;
                break;
            }
            if (role.getName().equals(RoleEnums.DEPT_LEADER.getStatus())){
                level = 1;
                break;
            }
        }
        return level;
    }


    @Override
    public void cancel(Integer id, User user, HttpServletRequest request) {

    }
}
