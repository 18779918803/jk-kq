package com.jk.kq.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.*;

/**
 * 小程序用户实体类
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年04月17日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_open_user")
public class OpenUser extends BaseEntity {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private String gender;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 语言
     */
    private String language;

    /**
     * 部门ID
     */
    private Integer organId;

    /**
     * 部门
     */
    private String organ;

    /**
     * D
     */
    private Integer roleId;

    /**
     * D
     */
    private String role;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户考勤机编号
     */
    private Integer ccid;

    /**
     * userid
     */
    private Integer userId;

    /**
     * openid
     */
    private String openId;

    /**
     * unionid
     */
    private String unionId;

    /**
     * session_key
     */
    private String sessionKey;

    /**
     * third_session
     */
    private String thirdSession;

    /**
     * auth
     */
    private Boolean auth;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 主指纹base64信息
     */
    private String fingerprint1;

    /**
     * 备用指纹base64信息
     */
    private String fingerprint2;
}