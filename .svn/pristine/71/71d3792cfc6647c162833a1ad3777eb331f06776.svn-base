package com.jk.sw.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date 2020/6/9
 * @Version 1.0
 */
@Data
public class SendGZHMsgDTO implements Serializable {

    /**公众号OpenID*/
    private String openId;

    /**消息头部提示文本*/
    private String head;

    /**审批名称（例如：加班申请）*/
    private String name;

    /**审批状态（待审批）*/
    private String state;

    /**详细内容（一般填入申请事由或原因，例如：overtimeRecord.getReason()）*/
    private String messageText;

    /**消息备注（点击查看详情）*/
    private String remarkText;

    /**点击消息后跳转的页面*/
    private String regUser;
}
