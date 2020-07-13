package com.jk.common.session;

import com.jk.common.constant.GlobalConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * session用户
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Data
public class SessionUser implements Serializable{

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 手机号码
     */
    private String phone;

    /**
     * 机构编码
     */
    private String organCode;


    /**
     * app客户端类型
     */
    private String appType;

    /**
     * 推送id
     */
    private String registrationId;

    /**
     * 判断是否是超级管理员
     *
     * @return {@link boolean}
     */
    public boolean isAdmin(){
        return username.equalsIgnoreCase(GlobalConstant.ADMIN);
    }
}
