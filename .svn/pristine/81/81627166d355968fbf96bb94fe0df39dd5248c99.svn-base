package com.jk.sw.service.impl;

import com.jk.common.bean.ReturnBean;
import com.jk.sw.entity.dto.SendGZHMsgDTO;
import com.jk.sw.service.SendMsgService;
import com.jk.sw.util.SendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件描述：发送消息
 *
 * @Author 温龙飞
 * @Date $ $
 * @Version 1.0
 */
@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Autowired
    private SendUtil sendUtil;

    @Override
    public ReturnBean sendToGZH(SendGZHMsgDTO dto) {
        sendUtil.sendMessage(dto.getOpenId(), dto.getHead(), dto.getName(),
                dto.getState(), dto.getMessageText(), dto.getRemarkText(), dto.getRegUser());
        return ReturnBean.ok();
    }

}
