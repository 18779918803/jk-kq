package com.jk.api.util;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.kq.entity.GzhOpenUser;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.mapper.GzhOpenUserMapper;
import com.jk.kq.service.IOpenUserService;
import com.jk.sys.entity.User;
import com.jk.wxpush.service.WxPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Component
@Slf4j
public class PushMsg {

    @Autowired
    private IOpenUserService openUserService;

    @Autowired
    private GzhOpenUserMapper gzhMapper;

    @Autowired
    private WxPushService wxPushService;

    public void push(List<User> ids, String title, String reason) {
        if (ObjectUtils.isEmpty(ids)) return;
        for (User userId : ids) {
            try {
                QueryWrapper<OpenUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", userId.getId());
                OpenUser userDB = openUserService.selectOne(queryWrapper);
                if (ObjectUtils.isEmpty(userDB)) {
                    continue;
                }
                GzhOpenUser gzhOpenUser = gzhMapper.selectByOpenId(userDB.getUnionId());
                if (!ObjectUtils.isEmpty(gzhOpenUser)) {
                    wxPushService.sendMessage(gzhOpenUser.getOpenid(), "您有一个员工申请等待审批!",
                            title, "等待审批", reason == null ? "OK" : reason,
                            "点击查看详情", "pages/works/check/check");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                log.error("发送消息失败:{}", JSON.toJSONString(userId));
            }

        }

    }

    public void finish(List<User> ids, String title) {
        if (ObjectUtils.isEmpty(ids)) return;
        for (User userId : ids) {
            try {
                QueryWrapper<OpenUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", userId.getId());
                OpenUser userDB = openUserService.selectOne(queryWrapper);
                if (ObjectUtils.isEmpty(userDB)) {
                    continue;
                }
                GzhOpenUser gzhOpenUser = gzhMapper.selectByOpenId(userDB.getUnionId());
                if (!ObjectUtils.isEmpty(gzhOpenUser)) {
                    wxPushService.sendMessage(gzhOpenUser.getOpenid(), "您的申请已审批通过!",
                            title, "审批通过", "通过",
                            "点击查看详情", "pages/works/works");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                log.error("发送消息失败:{}", JSON.toJSONString(userId));
            }

        }

    }

    public void reject(List<User> ids, String title) {
        if (ObjectUtils.isEmpty(ids)) return;
        for (User userId : ids) {
            try {
                QueryWrapper<OpenUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", userId.getId());
                OpenUser userDB = openUserService.selectOne(queryWrapper);
                if (ObjectUtils.isEmpty(userDB)) {
                    continue;
                }
                GzhOpenUser gzhOpenUser = gzhMapper.selectByOpenId(userDB.getUnionId());
                if (!ObjectUtils.isEmpty(gzhOpenUser)) {
                    wxPushService.sendMessage(gzhOpenUser.getOpenid(), "您的申请已审批拒绝!",
                            title, "审批拒绝", "拒绝",
                            "点击查看详情", "pages/works/works");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                log.error("发送消息失败:{}", JSON.toJSONString(userId));
            }

        }

    }
}
