package com.jk.kq.utils;

public class MessageIdUtil {

    /**
     * 获取messageId
     * @return id
     */
    public static String messageId(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取ccid
     * @return ccid
     */
    public static String ccid(){
        String ccid = String.valueOf(System.currentTimeMillis());
        return ccid.substring(0, 2) + ccid.substring(ccid.length() - 6, ccid.length());
    }

//    public static void main(String[] args){
//        System.out.println(ccid());
//    }
}