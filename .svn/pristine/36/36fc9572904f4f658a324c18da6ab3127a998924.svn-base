package com.jk.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.api.dto.sw.ReceivingRecordsDTO;
import com.jk.api.service.ApiService;
import com.jk.api.service.SwService;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.common.bean.ReturnBean;
import com.jk.common.util.ServletUtils;
import com.jk.common.util.ShiroUtils;
import com.jk.common.util.StringUtils;
import com.jk.kq.mapper.LeaveRecordMapper;
import com.jk.sw.constant.SwConst;
import com.jk.sw.entity.ReceivingRecords;
import com.jk.sw.entity.SwProcess;
import com.jk.sw.entity.SwTask;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.entity.dto.InstructionDTO;
import com.jk.sw.entity.dto.SendGZHMsgDTO;
import com.jk.sw.mapper.ReceivingRecordsMapper;
import com.jk.sw.mapper.SwCommonMapper;
import com.jk.sw.mapper.SwProcessMapper;
import com.jk.sw.mapper.SwUsableMapper;
import com.jk.sw.service.ISwProcessService;
import com.jk.sw.service.ISwTaskService;
import com.jk.sw.service.ISwUsableService;
import com.jk.sw.service.SendMsgService;
import com.jk.sys.entity.Dic;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.User;
import com.jk.sys.mapper.DicMapper;
import com.jk.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date 2020/07/01
 * @Version 1.0
 */
@Slf4j
@Service
public class SwServiceImpl implements SwService {

    @Autowired
    private DicMapper dicMapper;
    @Autowired
    private ISwTaskService swTaskService;
    @Autowired
    private ISwProcessService swProcessService;
    @Autowired
    private ReceivingRecordsMapper receivingRecordsMapper;
    @Autowired
    private ISwUsableService swUsableService;
    @Autowired
    private LeaveRecordMapper leaveRecordMapper;
    @Autowired
    private SwCommonMapper swCommonMapper;
    @Autowired
    private SwProcessMapper swProcessMapper;
    @Autowired
    private SwUsableMapper swUsableMapper;
    @Autowired
    private ApiService apiService;
    @Autowired
    private SendMsgService sendMsgService;

    @Override
    public ReturnBean instructionPage(Integer uid, Integer id) {
        List<SwTask> swTasks = swTaskService.selectList(new QueryWrapper<SwTask>()
                .eq("swid", id).eq("status", SwConst.TaskStatus.getValue("进行中")));
        if (CollectionUtils.isEmpty(swTasks) || swTasks.get(0) == null
                || swTasks.get(0).getId() == null){
            return ReturnBean.error("任务不存在！");
        }
        if (swTasks.size() > 1){
            return ReturnBean.error("任务异常，本收文含一个以上正在进行的任务");
        }
        SwTask task = swTasks.get(0);
        ReceivingRecords receivingRecords = receivingRecordsMapper.selectById(id);
        List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", task.getId())
                .eq("approver", uid));
        if (CollectionUtils.isEmpty(processList) || processList.get(0) == null){
            return ReturnBean.error("流程不存在！");
        }
        Map<String, Object> map = new HashMap(16);
        map.put("url", task.getAttachment());
        map.put("taskId", task.getId());
        map.put("content", processList.get(0).getContent());
        map.put("swid", id);
        map.put("propose", task.getPropose());
        map.put("instructions", instructionListBySwid(id));
        map.put("title", receivingRecords.getTitle());

        Map dtoMap = new HashMap<>();
        dtoMap.put("ReceivingRecords", receivingRecords);
        dtoMap.put("sw_received_org", this.getDicList("sw_received_org"));
        dtoMap.put("sw_security_classification",  this.getDicList("sw_security_classification"));
        dtoMap.put("sw_importance",  this.getDicList("sw_importance"));

        ReceivingRecordsDTO dto = this.getReceivingRecordsDTO(dtoMap);
        map.put("receivedOrgName", dto.getReceivedOrgName());
        map.put("importanceName", dto.getImportanceName());
        map.put("securityClassificationName", dto.getSecurityClassificationName());
        map.put("receivedDate", receivingRecords.getReceivedDate());

        List<Dic> sw_Instruction_shortcut = this.getDicList("sw_Instruction_shortcut");
        map.put("shortcut", sw_Instruction_shortcut);
        return ReturnBean.ok(map);
    }

    @Override
    public ReturnBean seeInfoPage(Integer id) {
        ReceivingRecords receivingRecords = receivingRecordsMapper.selectById(id);
        Map<String, Object> map = new HashMap(16);
        map.put("url", receivingRecords.getAttachment());
        map.put("swid", id);
        map.put("propose", receivingRecords.getPropose());
        map.put("instructions", instructionListBySwid(id));
        map.put("title", receivingRecords.getTitle());
        Map dtoMap = new HashMap<>();
        dtoMap.put("sw_received_org", this.getDicList("sw_received_org"));
        dtoMap.put("ReceivingRecords", receivingRecords);
        dtoMap.put("sw_security_classification",  this.getDicList("sw_security_classification"));
        dtoMap.put("sw_importance",  this.getDicList("sw_importance"));
        ReceivingRecordsDTO dto = this.getReceivingRecordsDTO(dtoMap);
        map.put("receivedOrgName", dto.getReceivedOrgName());
        map.put("importanceName", dto.getImportanceName());
        map.put("securityClassificationName", dto.getSecurityClassificationName());
        map.put("receivedDate", receivingRecords.getReceivedDate());
        map.put("editable", receivingRecords.getEditable());
        return ReturnBean.ok(map);
    }

    @Override
    public ReturnBean swText(Integer id) {
        return ReturnBean.ok(receivingRecordsMapper.selectTextById(id));
    }

    @Override
    public ReturnBean saveSwText(Integer id, String text) {
        ReceivingRecords receivingRecords = new ReceivingRecords();
        receivingRecords.setText(text);
        receivingRecordsMapper.update(receivingRecords ,new UpdateWrapper<ReceivingRecords>().eq("id", id));
        return ReturnBean.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean instruction(InstructionDTO dto) {
        HttpServletRequest request = ServletUtils.getRequest();
        User user = apiService.getUser(request);

        int i = swUsableService.selectCount(new QueryWrapper<SwUsable>().eq("swid", dto.getSwid())
                .eq("uid", user.getId())
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
        swProcessMapper.update(swProcess, new UpdateWrapper<SwProcess>().eq("approver", user.getId())
                .eq("task_id", task.getId()));

        SwUsable swUsable = new SwUsable();
        swUsable.setUsable(SwConst.UsableScope.getValue("只读"));
        //可以先查出ID，判断是否为一条数据，如果是再更新。
        swUsableMapper.update(swUsable, new UpdateWrapper<SwUsable>().eq("swid", task.getSwid())
                .eq("uid", user.getId()).eq("read_set", SwConst.UsableType.getValue("流转")));
        if(dto.getStatus() == SwConst.ApprovalStatus.getValue("驳回").intValue()){
            SwTask taskTemp = new SwTask();
            taskTemp.setId(task.getId());
            taskTemp.setStatus(SwConst.TaskStatus.getValue("驳回结束"));
            swTaskService.updateById(taskTemp);
            return ReturnBean.ok();
        }

        /**获取此次审批的排序号*/
        List<SwProcess> processList = swProcessService.selectList(new UpdateWrapper<SwProcess>().eq("approver", user.getId())
                .eq("task_id", task.getId()));
        if (CollectionUtils.isEmpty(processList) || processList.get(0) == null
                || processList.get(0).getSort() == null || processList.size() > 1 ){
            return ReturnBean.error("未能审批成功，请联系管理员");
        }

        Integer sort = processList.get(0).getSort();

        /**获取此次审批的之后的审批
         * */
        List<SwProcess> sortList = swProcessService.selectList(new UpdateWrapper<SwProcess>().eq("task_id", task.getId()).gt("sort", sort));
        if (CollectionUtils.isEmpty(sortList) || sortList.get(0) == null){
            log.debug("任务ID："+task.getId()+"，此次审批为最终环节，此次任务已经结束");
            return ReturnBean.ok("任务ID："+task.getId()+"，此次审批未最终环节，此次任务已经结束");
        }

        /**同批次审批内是否还有人未审批*/
        int sameSortList = swProcessService.selectCount(new UpdateWrapper<SwProcess>().eq("task_id", task.getId())
                .eq("sort", sort).eq("status", SwConst.ApprovalStatus.getValue("未批示")));

        if (sameSortList == 0){
            List<Integer> approverList = new ArrayList();
            Collections.sort(sortList);
            Integer sort1 = sortList.get(0).getSort();
            ReceivingRecords rr = receivingRecordsMapper.selectById(dto.getSwid());
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
                        msgDTO.setRemarkText(user.getName() + " : " + dto.getContent());
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
                        msgDTO.setRemarkText(user.getName() + "已审批，请查看");
                        msgDTO.setRegUser("pages/sw/seeList/info/info?id=" + dto.getSwid());//跳转页面
                        msgDTO.setMessageText(user.getName() + ":" + dto.getContent());
                        msgDTO.setHead(user.getName() + "已审批，请查看");
                        sendMsgService.sendToGZH(msgDTO);
                    }
                }
            }
        }
        return ReturnBean.ok();
    }

    @Override
    public List<Dic> getDicList(String type) {
        return dicMapper.selectList(new QueryWrapper<Dic>().eq("type", type));
    }

    @Override
    public IPage selectUsableList(Serializable usable, Serializable uid, Map para) {
        if (!para.containsKey("length") || !para.containsKey("start")){
            log.warn("没有设置开始页或每页显示条数,系统设置为0,10");
            para.put("length", 10);
            para.put("start", 0);
        }
        HttpServletRequest request = ServletUtils.getRequest();
        List<Role> roles = apiService.getUser(request).getRoles();
        List<ReceivingRecords> list = null;
        boolean isSwAdmin = false;
        if (!CollectionUtils.isEmpty(roles)){
            for (Role r : roles){
                if(ObjectUtils.nullSafeEquals(r.getName(), "收文管理员")){
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
        List<Dic> sw_received_org = this.getDicList("sw_received_org");
        List<Dic> sw_importance = this.getDicList("sw_importance");
        List<Dic> sw_security_classification = this.getDicList("sw_security_classification");

        List<ReceivingRecordsDTO> dtoList = new ArrayList<>(Integer.parseInt(para.get("length").toString()));
        if (!CollectionUtils.isEmpty(list)){
            Iterator it = list.iterator();
            while (it.hasNext()){
                Object next = it.next();
                Map map = new HashMap(4);
                map.put("sw_security_classification", sw_security_classification);
                map.put("sw_importance", sw_importance);
                map.put("sw_received_org", sw_received_org);
                map.put("ReceivingRecords", next);
                dtoList.add(getReceivingRecordsDTO(map));
            }
        }
        page.setRecords(dtoList);
        return page;
    }

    private ReceivingRecordsDTO getReceivingRecordsDTO(Map map){
        ReceivingRecords next = (ReceivingRecords) map.get("ReceivingRecords");
        List<Dic> sw_security_classification = (List<Dic>) map.get("sw_security_classification");
        List<Dic> sw_importance = (List<Dic>) map.get("sw_importance");
        List<Dic> sw_received_org = (List<Dic>) map.get("sw_received_org");
        ReceivingRecordsDTO dto = new ReceivingRecordsDTO();
        BeanUtils.copyProperties(next, dto);
        if (ObjectUtils.nullSafeEquals(SwConst.IsOrNot.getValue("是"), next.getEditable())){
            dto.setEditableName("可编辑");
        }else {
            dto.setEditableName("不可编辑");
        }
        if (ObjectUtils.nullSafeEquals(SwConst.IsOrNot.getValue("否"), next.getComplete())){
            dto.setEditableName("未归档");
        }else {
            dto.setEditableName("已归档");
        }
        if (!CollectionUtils.isEmpty(sw_received_org)){
            for(Dic dic : sw_received_org){
                if (ObjectUtils.nullSafeEquals(Integer.parseInt(dic.getCode()), next.getReceivedOrg())){
                    dto.setReceivedOrgName(dic.getName());
                    break;
                }
            }
        }
        if (!CollectionUtils.isEmpty(sw_importance)){
            for(Dic dic : sw_importance){
                if (ObjectUtils.nullSafeEquals(Integer.parseInt(dic.getCode()), next.getImportance())){
                    dto.setImportanceName(dic.getName());
                    break;
                }
            }
        }
        if (!CollectionUtils.isEmpty(sw_security_classification)){
            for(Dic dic : sw_security_classification){
                if (ObjectUtils.nullSafeEquals(Integer.parseInt(dic.getCode()), next.getSecurityClassification())){
                    dto.setSecurityClassificationName(dic.getName());
                    break;
                }
            }
        }
        return dto;
    }

    /**
     * 根据收文ID获取最后一次任务的所有相关批示
     * */
    //@Override
    public ReturnBean instructionsBySwid(Integer id) {
        StringBuffer sb = new StringBuffer();
        List<SwTask> tasks = swTaskService.selectList(new QueryWrapper<SwTask>().eq("swid", id));
        if (!CollectionUtils.isEmpty(tasks) && tasks.get(0)!=null){
            Collections.sort(tasks);
            SwTask task = tasks.get(0);

            List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", task.getId()));
            if (!CollectionUtils.isEmpty(processList)){
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

    /**
     * 根据收文ID获取最后一次任务的所有相关批示
     * @return List
     * */
    //@Override
    public List instructionListBySwid(Integer id) {
        List list = new ArrayList();
        List<SwTask> tasks = swTaskService.selectList(new QueryWrapper<SwTask>().eq("swid", id));
        if (!CollectionUtils.isEmpty(tasks) && tasks.get(0)!=null){
            Collections.sort(tasks);
            SwTask task = tasks.get(0);

            List<SwProcess> processList = swProcessService.selectList(new QueryWrapper<SwProcess>().eq("task_id", task.getId()));
            if (!CollectionUtils.isEmpty(processList)){
                Collections.sort(processList);
                for (SwProcess p : processList){
                    if (StringUtils.isNotBlank(p.getContent())){
                        Map map = new HashMap();
                        map.put("name", p.getUsername());
                        map.put("status", p.getStatus());
                        map.put("content", p.getContent()==null? "": p.getContent());
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }
}
