package com.jk.wxpush.service;

public interface WxPushService {

    void sendMessage(final String openId, final String head,
                final String name, final String state, final String messageText,
                final String remarkText, final String pagePath);
}
