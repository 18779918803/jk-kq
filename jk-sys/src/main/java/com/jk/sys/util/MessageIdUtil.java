package com.jk.sys.util;

public class MessageIdUtil {

    /**
     * 获取messageId
     * @return id
     */
    public static String messageId(){
        return String.valueOf(System.currentTimeMillis());
    }
}