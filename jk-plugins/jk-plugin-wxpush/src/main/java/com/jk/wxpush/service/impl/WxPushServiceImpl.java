package com.jk.wxpush.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.wxpush.service.WxPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.jk.common.util.HttpUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
@EnableScheduling
public class WxPushServiceImpl implements CommandLineRunner,WxPushService {

    /**
     * 模板消息ID（可以直接选择合适的模板ID）
     */
    private static String TEMPLATE_ID;

    /**
     * 微信公众号APP_ID（和微信小程序的APP_ID不是同一个）
     */
    public static String APP_ID;

    /**
     * 微信公众号APP_SECRET（和微信小程序的APP_SECRET不是同一个）
     */
    public static String APP_SECRET;

    /**
     * 微信小程序的APP_ID
     */
    public static String MINI_APP_ID;

    /**
     * 微信小程序的APP_SECRET
     */
    public static String MINI_APP_SECRET;


    @Value("${wx.app_secret}")
    public void setAppSecret(String appSecret) {
        APP_SECRET = appSecret;
    }

    @Value("${wx.app_id}")
    public void setAppId(String appId) {
        APP_ID = appId;
    }

    @Value("${wx.template_id}")
    public void setTemplateId(String templateId) {
        TEMPLATE_ID = templateId;
    }

    @Value("${wx.mini.app_id}")
    public void setMiniAppId(String miniAppId) {
        MINI_APP_ID = miniAppId;
    }

    @Value("${wx.mini.app_secret}")
    public void setMiniSecret(String miniAppSecret) {
        MINI_APP_SECRET = miniAppSecret;
    }

    /**
     * 访问凭证，有效期2个小时，因为每天只能获取200个，
     * 所以该类中有一个定时任务，每隔两个小时会刷新一次ACCESS_TOKEN
     */
    public static String ACCESS_TOKEN = "";

    /**
     * 推送消息模板方法
     *
     * @param openId      需要推送的微信公众号的用户的openId
     * @param head        消息头部提示文本（您好，你有一个员工申请待审批！）
     * @param name        审批名称（例如：加班申请）
     * @param state       审批状态（待审批）
     * @param messageText 详细内容（一般填入申请事由或原因，例如：overtimeRecord.getReason()）
     * @param remarkText  消息备注（点击查看详情）
     * @param pagePath     点击消息后跳转的页面
     */
    public void sendMessage(final String openId, final String head,
                            final String name, final String state, final String messageText,
                            final String remarkText, final String pagePath) {

        try {
            //用户点击推送消息之后跳转的页面
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + ACCESS_TOKEN;
            Map<String, Object> map = new HashMap<String, Object>();
            //推送用户openUserId
            map.put("touser", openId);
            //指定模版ID
            map.put("template_id", TEMPLATE_ID);
            //点击模版跳转地址
            map.put("url", "http://mp.weixin.qq.com");
            TreeMap<String, String> miniprograms = new TreeMap<String, String>();
            miniprograms.put("appid", MINI_APP_ID);
            miniprograms.put("pagepath", pagePath);// 注意，这里是支持传参的！！！
            map.put("miniprogram", miniprograms);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            Map<String, Object> first = new HashMap<String, Object>();
            first.put("value", head);
            first.put("color", "#000000");
            //审批名称
            Map<String, Object> keyword1 = new HashMap<String, Object>();
            keyword1.put("value", name);
            keyword1.put("color", "#000000");
            //审批状态
            Map<String, Object> keyword2 = new HashMap<String, Object>();
            keyword2.put("value", state);
            keyword2.put("color", "#000000");
            //审批内容
            Map<String, Object> keyword3 = new HashMap<String, Object>();
            keyword3.put("value", messageText);
            keyword3.put("color", "#000000");
            //点击查看详情
            Map<String, Object> remark = new HashMap<String, Object>();
            remark.put("value", remarkText);
            remark.put("color", "#000000");

            dataMap.put("first", first);
            dataMap.put("keyword1", keyword1);
            dataMap.put("keyword2", keyword2);
            dataMap.put("keyword3", keyword3);
            dataMap.put("remark", remark);

            map.put("data", dataMap);

            String jsonArgs = JSONObject.toJSONString(map);
            log.error("111:{}",jsonArgs);
            String result = HttpUtils.post(url, jsonArgs.toString());
            log.error("微信服务器:{}",result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String isOk = String.valueOf(jsonObject.get("errmsg"));
            if (isOk.equals("ok")) {
                log.info("申请审批消息推送成功：" + openId);
            } else {
                log.error("申请审批消息推送失败：" + openId);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }


    /**
     * 静态代码块，让项目在一启动的时候就获取一个微信公众号的access_token
     */
//    static {
//        ACCESS_TOKEN = getAccessToken();
//        log.info("ACCESS_TOKEN: " + ACCESS_TOKEN);
//    }

    /**
     * 每隔两个小时刷新一次微信公众号的access_token
     *
     * @throws UnsupportedEncodingException
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshAccessToken() {
        ACCESS_TOKEN = getAccessToken();
        log.info("ACCESS_TOKEN: " + ACCESS_TOKEN);
    }

    /**
     * 获取微信公众号的access_token
     *
     * @return
     */
    private static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + APP_ID + "&secret=" + APP_SECRET;
        String json = HttpUtils.get(url);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String accessToken = null;
        if (jsonObject.containsKey("access_token")) {
            accessToken = (String) jsonObject.get("access_token");
        }
        return accessToken;
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

    @Override
    public void run(String... args) throws Exception {
        ACCESS_TOKEN = getAccessToken();
        log.info("ACCESS_TOKEN: " + ACCESS_TOKEN);
    }
}
