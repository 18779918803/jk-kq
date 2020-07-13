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
import com.jk.sw.constant.SwConst;
import com.jk.sw.entity.ReceivingRecords;
import com.jk.sw.entity.SwTask;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.entity.dto.InstructionDTO;
import com.jk.sw.entity.dto.SendGZHMsgDTO;
import com.jk.sw.mapper.SwCommonMapper;
import com.jk.sw.mapper.SwUsableMapper;
import com.jk.sw.service.*;
import com.jk.sys.entity.User;
import com.jk.sys.entity.UserRole;
import com.jk.sys.mapper.RoleMapper;
import com.jk.sys.service.IUserRoleService;
import com.jk.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.sw.entity.SwProcess;
import com.jk.sw.mapper.SwProcessMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;


/**
 * 流程服务接口实现层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月11日
 */
@Service
@Slf4j
public class SwProcessServiceImpl extends BaseServiceImpl<SwProcessMapper, SwProcess> implements ISwProcessService {

    @Autowired
    private ISwUsableService swUsableService;
    @Autowired
    private ISwTaskService swTaskService;
    @Autowired
    private IUserService userService;
    @Autowired
    private SwCommonMapper swCommonMapper;
    @Autowired
    private SwProcessMapper swProcessMapper;
    @Autowired
    private SwUsableMapper swUsableMapper;
    @Autowired
    private IReceivingRecordsService receivingRecordsService;
    @Autowired
    private SendMsgService sendMsgService;

    @Override
    public IPage processApproverList(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List<User> users = swCommonMapper.selectUsersByRoleName("集团领导班子成员");
        page.setRecords(users);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean instruction(InstructionDTO dto) {
        int i = swUsableService.selectCount(new QueryWrapper<SwUsable>().eq("swid", dto.getSwid())
                .eq("uid", ShiroUtils.getSessionUser().getId())
                .eq("usable", SwConst.UsableScope.getValue("读写"))
                .eq("read_set", SwConst.UsableType.getValue("流转")));
        if (i==0){
            return ReturnBean.error("没有审批权限");
        }
        List<SwTask> swTasks = swTaskService.selectList(new QueryWrapper<SwTask>().eq("swid", dto.getSwid())
                .eq("status", SwConst.TaskStatus.getValue("进行中")));
        if (CollectionUtils.isEmpty(swTasks) || swTasks.get(0) == null){
            return ReturnBean.error("没有正在进行中的任务，不能审批");
        }
        if (swTasks.size() > 1){
            return ReturnBean.error("进行中的任务超过1个以上，请联系管理员");
        }
        SwTask task = swTasks.get(0);

        SwProcess swProcess = new SwProcess();
        swProcess.setStatus(dto.getStatus());
        swProcess.setContent(dto.getContent());
        swProcessMapper.update(swProcess, new UpdateWrapper<SwProcess>().eq("approver", ShiroUtils.getSessionUser().getId())
        .eq("task_id", task.getId()));

        SwUsable swUsable = new SwUsable();
        swUsable.setUsable(SwConst.UsableScope.getValue("只读"));
        swUsableMapper.update(swUsable, new UpdateWrapper<SwUsable>().eq("swid", task.getSwid())
                .eq("uid", ShiroUtils.getSessionUser().getId()).eq("read_set", SwConst.UsableType.getValue("流转")));
        if(dto.getStatus() == SwConst.ApprovalStatus.getValue("驳回").intValue()){
            SwTask taskTemp = new SwTask();
            taskTemp.setId(task.getId());
            taskTemp.setStatus(SwConst.TaskStatus.getValue("驳回结束"));
            swTaskService.updateById(taskTemp);
            return ReturnBean.ok();
        }

        /**获取此次审批的排序号*/
        List<SwProcess> processList = selectList(new UpdateWrapper<SwProcess>().eq("approver", ShiroUtils.getSessionUser().getId())
                .eq("task_id", task.getId()));
        if (CollectionUtils.isEmpty(processList) || processList.get(0) == null
                || processList.get(0).getSort() == null || processList.size() > 1 ){
            return ReturnBean.error("未能审批成功，请联系管理员");
        }

        Integer sort = processList.get(0).getSort();

        /**获取此次审批的之后的审批
         * 目前SwProcess为升序,适用于至上而下的审批。至下而上的审批需要重写排序方法，采用Comparator。
         * */
        List<SwProcess> sortList = selectList(new UpdateWrapper<SwProcess>().eq("task_id", task.getId()).gt("sort", sort));
        if (CollectionUtils.isEmpty(sortList) || sortList.get(0) == null){
            log.debug("任务ID："+task.getId()+"，此次审批为最终环节，此次任务已经结束");
            return ReturnBean.ok("任务ID："+task.getId()+"，此次审批未最终环节，此次任务已经结束");
        }

        /**同批次审批内是否还有人未审批*/
        int sameSortList = selectCount(new UpdateWrapper<SwProcess>().eq("task_id", task.getId())
                .eq("sort", sort).eq("status", SwConst.ApprovalStatus.getValue("未批示")));
        if (sameSortList == 0){
            List<Integer> approverList = new ArrayList();//下一批审批人ID
            Collections.sort(sortList);
            Integer sort1 = sortList.get(0).getSort();
            /**获取到此次审批之后一批次的审批人员更新为可审批状态（读写）*/
            for(SwProcess swp : sortList){
                if (swp.getSort().intValue() == sort1){
                    SwUsable swu = new SwUsable();
                    swu.setUsable(SwConst.UsableScope.getValue("读写"));
                    swUsableMapper.update(swu, new UpdateWrapper<SwUsable>().eq("swid", swp.getSwid())
                            .eq("uid", swp.getApprover()).eq("read_set", SwConst.UsableType.getValue("流转")));
                    approverList.add(swp.getApprover());
                }
            }

            ReceivingRecords rr = receivingRecordsService.selectById(dto.getSwid());
            /**给流程中下一批审批人发送消息*/
            if(!CollectionUtils.isEmpty(approverList)){
                Integer [] tempUids = new Integer[approverList.size()];
                Integer[] uids = approverList.toArray(tempUids);
                List<String> openIds = swCommonMapper.selectGZHOpenIDSBySysUserIds(uids);
                for (String openId : openIds){
                    if (StringUtils.isNotBlank(openId)){
                        SendGZHMsgDTO msgDTO = new SendGZHMsgDTO();
                        msgDTO.setOpenId(openId);
                        msgDTO.setState("待审批");
                        msgDTO.setName("收文审批");
                        msgDTO.setRemarkText(ShiroUtils.getSessionUser().getName() + " : " + dto.getContent());
                        msgDTO.setRegUser("pages/sw/instructionList/info/info?id=" + dto.getSwid());//跳转页面
                        msgDTO.setMessageText(rr.getTitle());
                        msgDTO.setHead("您有一个收文待审批。");
                        sendMsgService.sendToGZH(msgDTO);
                    }
                }
            }

            /**审批人审批后给收文管理员推送消息*/
            List<Integer> uidList = swCommonMapper.selectUserIdsByRoleName("收文管理员");
            if(!CollectionUtils.isEmpty(uidList)){
                Integer [] tempUids = new Integer[uidList.size()];
                Integer[] uids = uidList.toArray(tempUids);
                List<String> openIds = swCommonMapper.selectGZHOpenIDSBySysUserIds(uids);
                for (String openId : openIds){
                    if (StringUtils.isNotBlank(openId)){
                        SendGZHMsgDTO msgDTO = new SendGZHMsgDTO();
                        msgDTO.setOpenId(openId);
                        msgDTO.setState("已审批");
                        msgDTO.setName(rr.getTitle());
                        msgDTO.setRemarkText("点击查看详情");
                        msgDTO.setRegUser("pages/sw/seeList/info/info?id=" + dto.getSwid());//跳转页面
                        msgDTO.setMessageText(ShiroUtils.getSessionUser().getName() + ":" + dto.getContent());
                        msgDTO.setHead(ShiroUtils.getSessionUser().getName() + "已审批，请查看");
                        sendMsgService.sendToGZH(msgDTO);
                    }
                }
            }
        }
        return ReturnBean.ok();
    }

    @Override
    public boolean insertBatch(Collection<SwProcess> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (SwProcess anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean revoke(Integer taskId) {
        /**如果本次撤销前已有下一环节审批了就不能撤销*/
        SwTask task = swTaskService.selectById(taskId);
        int i = swUsableService.selectCount(new QueryWrapper<SwUsable>().eq("swid", task.getSwid())
                .eq("uid", ShiroUtils.getSessionUser().getId())
                .eq("status", SwConst.UsableScope.getValue("读写")));
        if (i==0){
            throw new KrtException(ReturnBean.error("请勿重复审批"));
        }

        SwProcess swProcess = new SwProcess();
        swProcess.setStatus(SwConst.ApprovalStatus.getValue("未批示"));
        swProcess.setContent(" ");
        update(swProcess, new UpdateWrapper<SwProcess>().eq("approver", ShiroUtils.getSessionUser().getId())
                .eq("task_id", taskId));
        return true;
    }
}
