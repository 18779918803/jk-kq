package com.jk.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.api.constant.WechatConst;
import com.jk.kq.entity.GzhOpenUser;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.service.IGzhOpenUserService;
import com.jk.kq.service.IOpenUserService;
import com.jk.sys.entity.User;
import com.jk.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 推送消息给微信公总号，封装模板方法
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月08日
 */
@Slf4j
@Component
//@EnableScheduling
public class SendWeChatUtil {

    @Autowired
    private IGzhOpenUserService gzhOpenUserService;

    @Autowired
    private IOpenUserService userService;



    /**
     * 微信公众号APP_ID（和微信小程序的APP_ID不是同一个）
     */
    public final static String APP_ID = "wx72cc2c4c6937ea1d";

    /**
     * 微信公众号APP_SECRET（和微信小程序的APP_SECRET不是同一个）
     */
    public final static String APP_SECRET = "0878f6485a07198a80a7f35317c61a33";






    /**
     * 微信官方参考文档：https://developers.weixin.qq.com/doc/offiaccount/User_Management/Getting_a_User_List.html
     * 用来拉取关注微信公众号的用户的openUserId
     * 由于一次拉取用户的最大数量为10000，所以这里直接一次性拉取，不使用递归拉取
     * <p>
     * access_token   访问凭证
     * next_openid    开始拉取的openUserId，超过10000个拉取数量需要填写，不超过直接传入空字符串，具体参考上方文档
     */
    @Transactional
    public void getGZHOpenUserId(String ACCESS_TOKEN) throws UnsupportedEncodingException {
        String url = "";
        QueryWrapper<GzhOpenUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("next_openid");
        queryWrapper.orderByDesc("id");
        queryWrapper.last("LIMIT 1");
        GzhOpenUser user = gzhOpenUserService.selectOne(queryWrapper);
        if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getNextOpenid())){
            url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + ACCESS_TOKEN+"&next_openid="+user.getNextOpenid();
        }else {
            url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + ACCESS_TOKEN;
        }
//        url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + ACCESS_TOKEN;

          //获取所有用户列表的接口
            String openList = HttpUtil.get(url);
            if (ObjectUtils.isEmpty(openList)){
                log.error("微信接口返回null");
                return;
            }
            Map map = JSON.parseObject(openList, Map.class);
            String errmsg =(String) map.get("errmsg");
            if (!ObjectUtils.isEmpty(errmsg)){
                log.error(errmsg);
                return;
            }
            String next_openid =(String) map.get("next_openid");
            if (ObjectUtils.isEmpty(next_openid)){
                log.error("微信接口返回null");
                return;
            }
            List<String> openIds = getOpenIdByOpenList(openList);
            for (String openid : openIds) {
                //获取单个用户信息的微信的接口
                String getOpenUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + ACCESS_TOKEN + "&openid=" + openid;
                String openUserInfo = HttpUtil.get(getOpenUserInfoUrl);
                GzhOpenUser gzhOpenUser = JSONObject.parseObject(openUserInfo, GzhOpenUser.class);
                gzhOpenUser.setNextOpenid(next_openid);
                gzhOpenUser.setNickname(URLEncoder.encode(gzhOpenUser.getNickname(), "utf-8"));
                GzhOpenUser gzh = gzhOpenUserService.selectByOpenId(openid);
                OpenUser dbUser = userService.selectOne(new QueryWrapper<OpenUser>().eq("open_id", openid));
                if (!ObjectUtils.isEmpty(dbUser) && !ObjectUtils.isEmpty(gzh)){
                    gzhOpenUser.setId(gzh.getId());
                    gzhOpenUserService.updateById(gzhOpenUser);
                    dbUser.setUnionId(gzhOpenUser.getUnionid());
                    userService.updateById(dbUser);
                }

                if (ObjectUtils.isEmpty(dbUser) && !ObjectUtils.isEmpty(gzh)){
                    gzhOpenUser.setId(gzh.getId());
                    gzhOpenUserService.updateById(gzhOpenUser);
                }

                if (!ObjectUtils.isEmpty(dbUser) && ObjectUtils.isEmpty(gzh)){
                    //否则我们就直接插入该用户的信息
                    gzhOpenUserService.insert(gzhOpenUser);
                    dbUser.setUnionId(gzhOpenUser.getUnionid());
                    userService.updateById(dbUser);
                }

                if (ObjectUtils.isEmpty(dbUser) && ObjectUtils.isEmpty(gzh)){
                    //否则我们就直接插入该用户的信息
                    gzhOpenUserService.insert(gzhOpenUser);
                }

            }

    }

    /**
     * 解析获取的OpenUser列表中的openid，将openid最终转换为一个String类型的字符串列表返回
     *
     * @param openList
     * @return
     */
    private List<String> getOpenIdByOpenList(String openList) {
        List<String> openids = new ArrayList<>();
        String substring = openList.substring(openList.indexOf("[") + 1, openList.indexOf("]"));
        String[] list = substring.split(",");
        for (String str : list) {
            openids.add(str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\"")));
        }
        return openids;
    }
}
