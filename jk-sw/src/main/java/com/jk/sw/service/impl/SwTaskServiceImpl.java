package com.jk.sw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.ReturnBean;
import com.jk.common.bean.ReturnCode;
import com.jk.common.exception.KrtException;
import com.jk.common.util.StringUtils;
import com.jk.sw.constant.SwConst;
import com.jk.sw.entity.ReceivingRecords;
import com.jk.sw.entity.SwProcess;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.entity.dto.SendGZHMsgDTO;
import com.jk.sw.mapper.SwCommonMapper;
import com.jk.sw.mapper.SwProcessMapper;
import com.jk.sw.mapper.SwUsableMapper;
import com.jk.sw.service.*;
import com.jk.sw.util.SwUtils;
import org.apache.regexp.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import com.jk.sw.entity.SwTask;
import com.jk.sw.mapper.SwTaskMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;


/**
 * 任务服务接口实现层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月07日
 */
@Service
public class SwTaskServiceImpl extends BaseServiceImpl<SwTaskMapper, SwTask> implements ISwTaskService {

    @Autowired
    private IReceivingRecordsService receivingRecordsService;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private ISwUsableService swUsableService;
    @Autowired
    private ISwProcessService swProcessService;
    @Autowired
    private SwCommonMapper swCommonMapper;
    @Autowired
    private SwUsableMapper swUsableMapper;
    @Autowired
    private SwTaskMapper swTaskMapper;
    @Value("${sw.send-interval}")
    private String sendInterval;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean insert(Serializable swid) {
        //SwTask swTask = new SwTask();
        /**查询驳回的任务*/
        List<SwTask> swTasks = super.selectList(new QueryWrapper<SwTask>().eq("swid", swid));
        if (!CollectionUtils.isEmpty(swTasks) && swTasks.get(0) != null){
            Collections.sort(swTasks);
            SwTask task = swTasks.get(0);
            if(task.getStatus() == SwConst.TaskStatus.getValue("驳回结束")
                || task.getStatus() == SwConst.TaskStatus.getValue("停止")){
                ReturnBean returnBean = create(true, (int) swid);
                if(returnBean.getCode() == ReturnCode.ERROR.getCode()){
                    return returnBean;
                }
                SwTask newTask = (SwTask) returnBean.getData();
                List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", task.getId()));
                for (SwProcess sp : processList){
                    sp.setTaskId(newTask.getId());
                    if (sp.getStatus() == SwConst.ApprovalStatus.getValue("驳回")){
                        sp.setStatus(SwConst.ApprovalStatus.getValue("未批示"));
                        sp.setContent(" ");
                    }
                }
                swProcessService.insertBatch(processList, 1000);
            }else {
                return ReturnBean.error("该收文已有任务完成或有任务进行中");
            }
        }else {
            return create(true, (int) swid);
        }
        return ReturnBean.ok();
    }

    private ReturnBean create(boolean flag, int swid){
        SwTask swTask = new SwTask();
        if (flag){
            ReceivingRecords receivingRecords = receivingRecordsService.selectById(swid);
            swTask.setSwid((int)swid);
            swTask.setText(receivingRecords.getText());
            swTask.setTaskId(SwUtils.generateTaskId());
            swTask.setAttachment(receivingRecords.getAttachment());
            swTask.setPropose(receivingRecords.getPropose());
            swTask.setStatus(SwConst.TaskStatus.getValue("未开始"));
            swTaskMapper.insert(swTask);
        }else {
            return ReturnBean.error("该收文已有任务完成或有任务进行中");
        }
        return ReturnBean.ok(swTask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean start(Serializable id) {
        SwTask swTask = selectById(id);
        if (swTask.getStatus() == SwConst.TaskStatus.getValue("驳回结束") ||
            swTask.getStatus() == SwConst.TaskStatus.getValue("完成")){
            return ReturnBean.error("任务结束不能启动");
        }
        if (swTask.getStatus() == SwConst.TaskStatus.getValue("进行中") ){
            return ReturnBean.error("任务进行中不能启动");
        }
        List<SwTask> tasks = selectList(new QueryWrapper<SwTask>().eq("swid", swTask.getSwid()));
        Collections.sort(tasks);
        if (CollectionUtils.isEmpty(tasks) || tasks.get(0)==null){
            return ReturnBean.error("任务：" + id + " 任务不存在，启动失败");
        }
        if ((int)id != tasks.get(0).getId()){
            return ReturnBean.error("任务：" + id + " 不能启动，已存在新任务");
        }


        /**查询未批示的流程*/
        List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>()
                .eq("task_id", id).eq("status", SwConst.ApprovalStatus.getValue("未批示").intValue()));

        if (CollectionUtils.isEmpty(processList) || processList.get(0)==null){
            return ReturnBean.error("任务：" + id + " 任务未有审批人，启动失败");
        }
        Collections.sort(processList);
        List<SwUsable> usableList = new ArrayList<>(processList.size());
        /**获取最前的流程*/
        SwProcess swProcess = processList.get(0);

        /**获取该任务下所有流程情况*/
        List<SwProcess> pl = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", id));
        if (!CollectionUtils.isEmpty(pl)){

            List<Integer> approverList = new ArrayList();
            Iterator<SwProcess> it = pl.iterator();
            while (it.hasNext()){
                SwProcess next = it.next();
                SwUsable swUsable = new SwUsable();
                swUsable.setSend(SwConst.IsOrNot.getValue("否"));
                swUsable.setSwid(next.getSwid());
                swUsable.setUid(next.getApprover());
                swUsable.setReadSet(SwConst.IsOrNot.getValue("否"));
                if(next.getSort() == swProcess.getSort().intValue()){
                    swUsable.setUsable(SwConst.UsableScope.getValue("读写"));
                    approverList.add(next.getApprover());
                } else {
                    if (next.getStatus() == SwConst.ApprovalStatus.getValue("同意").intValue()){
                        swUsable.setUsable(SwConst.UsableScope.getValue("只读"));
                        swUsable.setSend(SwConst.IsOrNot.getValue("是"));
                    }else {
                        swUsable.setUsable(SwConst.UsableScope.getValue("不可读写"));
                    }
                }
                usableList.add(swUsable);
            }
            swUsableMapper.delete(new QueryWrapper<SwUsable>().eq("swid", swTask.getSwid()));
            swUsableService.insertBatch(usableList, 1000);

            swTask.setStatus(SwConst.TaskStatus.getValue("进行中"));
            swTaskMapper.updateById(swTask);

            /**给流程中第一批次的审批人发送消息*/
            Integer [] tempUids = new Integer[approverList.size()];
            Integer[] uids = approverList.toArray(tempUids);
            List<String> openIds = swCommonMapper.selectGZHOpenIDSBySysUserIds(uids);
            ReceivingRecords rr = receivingRecordsService.selectById(swProcess.getSwid());
            for (String openId : openIds){
                if (StringUtils.isNotBlank(openId)){
                    SendGZHMsgDTO dto = new SendGZHMsgDTO();
                    dto.setOpenId(openId);
                    dto.setState("待审批");
                    dto.setName("收文审批");
                    dto.setRemarkText("点击查看详情");
                    dto.setRegUser("pages/sw/instructionList/info/info?id=" + swTask.getSwid());//跳转页面
                    dto.setHead("您有一个收文待审批。");

                    dto.setMessageText(rr.getTitle());

                    sendMsgService.sendToGZH(dto);
                }
            }
        }
        return ReturnBean.ok();
    }

    /**
     * 停止任务，可再次启动恢复
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean stop(Serializable id) {
        SwTask task = selectById(id);
        if (task.getStatus() != SwConst.TaskStatus.getValue("进行中") ){
            return ReturnBean.error("任务未进行不能停止");
        }

        SwTask swTask = new SwTask();
        swTask.setStatus(SwConst.TaskStatus.getValue("停止"));
        swTask.setId((int)id);
        swTaskMapper.updateById(swTask);

        SwUsable swUsable = new SwUsable();
        swUsable.setUsable(SwConst.UsableScope.getValue("不可读写"));
        swUsableMapper.update(swUsable, new QueryWrapper<SwUsable>().eq("swid", task.getSwid())
                .eq("usable", SwConst.UsableScope.getValue("读写")));

        return ReturnBean.ok();
    }

    @Override
    public String getInstructionsByTaskId(Serializable id) {
        StringBuffer sb = new StringBuffer();
        List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", id));
        if (!CollectionUtils.isEmpty(processList)){
            for (SwProcess p : processList){
                if (StringUtils.isNotBlank(p.getContent())){
                    sb.append(p.getUsername()+" : ");
                    sb.append(p.getContent()==null? " ": p.getContent());
                    sb.append("<br>");
                }
            }
        }
        return sb.toString();
    }
}
