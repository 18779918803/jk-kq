package com.jk.jpush.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;
import com.jk.jpush.dto.PushDTO;
import com.jk.jpush.enums.AppTypeEnum;
import com.jk.jpush.properties.JpushProperties;
import com.jk.jpush.service.IPushService;
import com.jk.jpush.util.PushUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机关推送接口实现层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年07月03日
 */
@Service
public class PushServiceImpl implements IPushService {

    @Autowired
    private JpushProperties jpushProperties;

    /**
     * 群发Alert消息
     *
     * @param pushDTO 推送dto
     */
    @Override
    public boolean pushAlertToAll(PushDTO pushDTO) {
        //1、构建推送客户端
        JPushClient jpushClient = new JPushClient(jpushProperties.getSecret(), jpushProperties.getAppkey(), null, ClientConfig.getInstance());
        //2、根据类型构建 PushPayload
        PushPayload payload = PushUtils.buildPushObjectAllAllAlert(pushDTO.getContent(), pushDTO.getExtras(), pushDTO.getApns());
        return PushUtils.push(jpushClient, payload);
    }

    /**
     * 群发Message消息
     *
     * @param pushDTO 推送dto
     */
    @Override
    public boolean pushMessageToAll(PushDTO pushDTO) {
        return false;
    }

    /**
     * 单发Alert消息
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#notification
     *
     * @param pushDTO 推送dto
     */
    @Override
    public boolean pushAlertToOne(PushDTO pushDTO) {
        //1、构建推送客户端
        JPushClient jpushClient = new JPushClient(jpushProperties.getSecret(), jpushProperties.getAppkey(), null, ClientConfig.getInstance());
        //2、根据类型构建 PushPayload
        PushPayload payload = null;
        String type = pushDTO.getType();
        if (AppTypeEnum.ANDROID.getValue().equals(type)) {
            payload = PushUtils.buildPushObjectAndroidOneAlert(pushDTO.getRegistrationId(), pushDTO.getContent(), pushDTO.getAlertType(), pushDTO.getExtras());
        }
        if (AppTypeEnum.IOS.getValue().equals(type)) {
            payload = PushUtils.buildPushObjectIosOneAlert(pushDTO.getRegistrationId(), pushDTO.getContent(), pushDTO.getExtras(), pushDTO.getApns());
        }
        return PushUtils.push(jpushClient, payload);
    }

    /**
     * 单发Message
     *
     * @param pushDTO 推送dto
     */
    @Override
    public boolean pushMessageToOne(PushDTO pushDTO) {
        //1、构建推送客户端
        JPushClient jpushClient = new JPushClient(jpushProperties.getSecret(), jpushProperties.getAppkey(), null, ClientConfig.getInstance());
        //2、根据类型构建 PushPayload
        PushPayload payload = null;
        String type = pushDTO.getType();
        if (AppTypeEnum.ANDROID.getValue().equals(type)) {
            payload = PushUtils.buildPushObjectAndroidOneMessage(pushDTO.getRegistrationId(), pushDTO.getContent());
        }
        if (AppTypeEnum.IOS.getValue().equals(type)) {
            payload = PushUtils.buildPushObjectIosOneMessage(pushDTO.getRegistrationId(), pushDTO.getContent(), pushDTO.getApns());
        }
        return PushUtils.push(jpushClient, payload);
    }
}
