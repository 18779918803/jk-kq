package com.jk.sw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.common.bean.ReturnBean;
import com.jk.common.exception.KrtException;
import com.jk.common.util.ShiroUtils;
import com.jk.common.util.StringUtils;
import com.jk.kq.enums.RoleEnums;
import com.jk.kq.mapper.LeaveRecordMapper;
import com.jk.sw.constant.SwConst;
import com.jk.sw.entity.SwProcess;
import com.jk.sw.entity.SwTask;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.entity.dto.SendGZHMsgDTO;
import com.jk.sw.mapper.SwCommonMapper;
import com.jk.sw.mapper.SwTaskMapper;
import com.jk.sw.mapper.SwUsableMapper;
import com.jk.sw.service.*;
import com.jk.sys.entity.Organ;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.User;
import com.jk.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.sw.entity.ReceivingRecords;
import com.jk.sw.mapper.ReceivingRecordsMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.*;


/**
 * 收文记录服务接口实现层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月06日
 */
@Slf4j
@Service
public class ReceivingRecordsServiceImpl extends BaseServiceImpl<ReceivingRecordsMapper, ReceivingRecords> implements IReceivingRecordsService {
    @Autowired
    private IReceivingRecordsService receivingRecordsService;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private ISwTaskService swTaskService;
    @Autowired
    private ISwProcessService swProcessService;
    @Autowired
    private ReceivingRecordsMapper receivingRecordsMapper;
    @Autowired
    private ISwUsableService swUsableService;
    @Autowired
    private SwTaskMapper swTaskMapper;
    @Autowired
    private SwCommonMapper swCommonMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Override
    public IPage selectUsableList(Serializable usable, Serializable uid, Map para) {
        if (!para.containsKey("length") || !para.containsKey("start")){
            log.warn("没有设置开始页或每页显示条数,系统设置为0,10");
            para.put("length", 10);
            para.put("start", 0);
        }
        List<ReceivingRecords> list = null;
        List<Integer> swAdminList = swCommonMapper.selectUserIdsByRoleName("收文管理员");
        boolean isSwAdmin = false;
        if (!CollectionUtils.isEmpty(swAdminList)){
            for (Integer id : swAdminList){
                if(ObjectUtils.nullSafeEquals(id, ShiroUtils.getSessionUser().getId())){
                    isSwAdmin = true;
                    break;
                }
            }
        }
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        if (isSwAdmin) {
            list = receivingRecordsMapper.selectPageList(para);
        }else {
            para.put("usable", usable);
            para.put("uid", uid);
            list = receivingRecordsMapper.selectUsableList(para);
        }

        page.setRecords(list);
        return page;
    }

    /**审批页面数据请求*/
    @Override
    public ReturnBean instructionPage(Integer id) {
        List<SwTask> swTasks = swTaskService.selectList(new QueryWrapper<SwTask>()
                .eq("swid", id).eq("status", SwConst.TaskStatus.getValue("进行中")));
        if (CollectionUtils.isEmpty(swTasks) || swTasks.get(0) == null
                || swTasks.get(0).getId() == null){
            return ReturnBean.error("任务不存在！");
        }
        if (swTasks.size() > 1){
            return ReturnBean.error("任务异常，本收文含一个以上正在进行的任务");
        }
        ReceivingRecords receivingRecords = super.selectById(id);
        List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", swTasks.get(0).getId())
                .eq("approver", ShiroUtils.getSessionUser().getId()));
        if (CollectionUtils.isEmpty(processList) || processList.get(0) == null){
            return ReturnBean.error("流程不存在！");
        }
        Map<String, Object> map = new HashMap(8);
        map.put("url", swTasks.get(0).getAttachment());
        map.put("taskId", swTasks.get(0).getId());
        map.put("content", processList.get(0).getContent());
        map.put("swid", id);
        map.put("propose", swTasks.get(0).getPropose());
        ReturnBean returnBean = instructionsBySwid(id);
        map.put("instructions", returnBean.getData());
        map.put("title", receivingRecords.getTitle());
        map.put("receivedOrg", receivingRecords.getReceivedOrg());
        return ReturnBean.ok(map);
    }

    /**
     * 根据收文ID获取最后一次任务的所有相关批示
     * */
    @Override
    public ReturnBean instructionsBySwid(Integer id) {
        StringBuffer sb = new StringBuffer();
        List<SwTask> tasks = swTaskService.selectList(new QueryWrapper<SwTask>().eq("swid", id));
        if (!CollectionUtils.isEmpty(tasks) && tasks.get(0)!=null){
            Collections.sort(tasks);
            SwTask task = tasks.get(0);

            List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", task.getId()));
            if (!CollectionUtils.isEmpty(processList)){
                Collections.sort(processList);
                for (SwProcess p : processList){
                    if (StringUtils.isNotBlank(p.getContent())){
                        sb.append(p.getUsername()+" : ");
                        sb.append(p.getContent()==null? " ": p.getContent());
                        sb.append("<br>");
                    }
                }
            }
        }
        return ReturnBean.ok(sb.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean complete(Integer id) {
        ReceivingRecords receivingRecords = selectById(id);
        if (ObjectUtils.nullSafeEquals(receivingRecords.getComplete(),1)){
            return ReturnBean.error("收文已归档，不能再次归档");
        }
        List<SwTask> taskList = swTaskService.selectList(new QueryWrapper<SwTask>().eq("swid", id)
        .eq("status", SwConst.TaskStatus.getValue("进行中")));
        if (CollectionUtils.isEmpty(taskList) || taskList.get(0)==null || taskList.get(0).getId()==null){
            return ReturnBean.error("收文：" + id + " 没有正在进行中任务，归档失败");
        }
        if (taskList.size() > 1){
            return ReturnBean.error("收文：" + id + " 正在进行中任务超过1个，归档失败");
        }

        int passCount = swProcessService.selectCount(new QueryWrapper<SwProcess>()
                .eq("status", SwConst.ApprovalStatus.getValue("同意"))
                .eq("task_id", taskList.get(0).getId()));
        int count = swProcessService.selectCount(new QueryWrapper<SwProcess>().eq("task_id", taskList.get(0).getId()));
        if (count == passCount){
            ReceivingRecords rr = new ReceivingRecords();
            rr.setComplete(SwConst.IsOrNot.getValue("是"));
            rr.setId(id);
            receivingRecordsMapper.updateById(rr);

            SwTask swTask = taskList.get(0);
            swTask.setStatus(SwConst.TaskStatus.getValue("完成"));
            swTaskMapper.updateById(swTask);
        }else {
            return ReturnBean.error("文件未完成审批流程，不能归档");
        }
        return ReturnBean.ok();
    }

    @Override
    @Transactional
    public ReturnBean saveText(ReceivingRecords rr) {
        int i = swUsableService.selectCount(new QueryWrapper<SwUsable>().eq("swid", rr.getId())
                .eq("uid", ShiroUtils.getSessionUser().getId())
                .eq("usable", SwConst.UsableScope.getValue("读写")));
        if (i==0){
            return ReturnBean.error("没有审批权限，保存失败");
        }
        List<SwTask> swTasks = swTaskService.selectList(new QueryWrapper<SwTask>()
                .eq("swid", rr.getId()).eq("status", SwConst.TaskStatus.getValue("进行中")));
        if (CollectionUtils.isEmpty(swTasks) || swTasks.get(0) == null
                || swTasks.get(0).getId() == null){
            return ReturnBean.error("任务不存在！");
        }
        if (swTasks.size() > 1){
            return ReturnBean.error("任务异常，本收文含一个以上正在进行的任务");
        }
        SwTask swTask = swTasks.get(0);
        swTask.setText(rr.getText());
        swTaskService.updateById(swTask);

        updateById(rr);
        return ReturnBean.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean readSet(Integer id, Integer[] organIds) {
        swUsableService.delete(new QueryWrapper<SwUsable>().eq("swid", id)
                .eq("read_set", SwConst.UsableType.getValue("传阅")));

        List<Integer> uidList = new ArrayList<>();
        List<SwUsable> list = new ArrayList(organIds.length);
        for (Integer organId : organIds){
            List<User> users1 = leaveRecordMapper.getUserByRoleAndOrgan(RoleEnums.DEPT_LEADER.getStatus(), organId);
            List<User> users2 = leaveRecordMapper.getUserByRoleAndOrgan(RoleEnums.DEPT_AGENT.getStatus(), organId);

            List<User> userList = new ArrayList<>();

            if (!CollectionUtils.isEmpty(users1)){
                userList.addAll(users1);
            }
            if (!CollectionUtils.isEmpty(users2)){
                userList.addAll(users2);
            }

            if (!CollectionUtils.isEmpty(userList)){
                for (User user : userList){
                    SwUsable swUsable = new SwUsable();
                    swUsable.setReadSet(SwConst.IsOrNot.getValue("是"));
                    swUsable.setOrganId(organId);
                    swUsable.setSwid(id);
                    swUsable.setUsable(SwConst.UsableScope.getValue("只读"));
                    swUsable.setUid(user.getId());
                    list.add(swUsable);
                    uidList.add(user.getId());
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            swUsableService.insertBatch(list, 1000);

            ReceivingRecords rr = receivingRecordsService.selectById(id);

            Integer [] tempUids = new Integer[uidList.size()];
            Integer[] uids = uidList.toArray(tempUids);
            List<String> openIds = swCommonMapper.selectGZHOpenIDSBySysUserIds(uids);

            for (String openId : openIds){
                SendGZHMsgDTO dto = new SendGZHMsgDTO();
                dto.setOpenId(openId);
                dto.setState("待查看");
                dto.setName("收文查看");
                dto.setRemarkText("点击查看详情");
                dto.setRegUser("pages/sw/seeList/info/info?id=" + id);//跳转页面
                dto.setHead("您有一个收文待查看。");

                dto.setMessageText(rr.getTitle());

                sendMsgService.sendToGZH(dto);
            }
        }
        return ReturnBean.ok();
    }

    @Override
    public ReturnBean allotUser(Integer id) {
        List<User> list = swCommonMapper.selectUserOrganByUid(ShiroUtils.getSessionUser().getId());

        Set<User> set = new HashSet();
        if (!CollectionUtils.isEmpty(list)){
            set.addAll(list);
        }
        User user = userService.selectById(ShiroUtils.getSessionUser().getId());
        List<User> users = swCommonMapper.selectUserByOrganId(user.getOrganId());
        set.addAll(users);
        return ReturnBean.ok(set);
    }

    @Override
    public ReturnBean allot(Integer id, Integer [] uids) {
        swUsableService.delete(new QueryWrapper<SwUsable>().eq("swid", id)
                .eq("read_set", SwConst.UsableType.getValue("分发")));
        List<SwUsable> list = new ArrayList(uids.length);
        if (uids.length > 0){
            Collection<User> users = userService.selectByIds(Arrays.asList(uids));
            for (User user : users){
                SwUsable swUsable = new SwUsable();
                swUsable.setReadSet(SwConst.UsableType.getValue("分发"));
                swUsable.setOrganId(user.getOrganId());
                swUsable.setSwid(id);
                swUsable.setUsable(SwConst.UsableScope.getValue("只读"));
                swUsable.setUid(user.getId());
                list.add(swUsable);
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            swUsableService.insertBatch(list, 1000);

            ReceivingRecords rr = receivingRecordsService.selectById(id);
            List<String> openIds = swCommonMapper.selectGZHOpenIDSBySysUserIds(uids);
            for (String openId : openIds){
                SendGZHMsgDTO dto = new SendGZHMsgDTO();
                dto.setOpenId(openId);
                dto.setState("待查看");
                dto.setName("收文查看");
                dto.setRemarkText("点击查看详情");
                dto.setRegUser("pages/sw/seeList/info/info?id=" + id);//跳转页面
                dto.setHead("您有一个收文待查看。");

                dto.setMessageText(rr.getTitle());

                sendMsgService.sendToGZH(dto);
            }
        }
        return ReturnBean.ok();
    }
}
