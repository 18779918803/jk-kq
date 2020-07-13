package com.jk.kq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 公众号用户实体类
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月09日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_gzh_open_user")
public class GzhOpenUser extends BaseEntity {

    /**
     * 是否关注
     */
    private String subscribe;

    /**
     * openid
     */
    private String openid;

    /**
     * unionid
     */
    private String unionid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 头像
     */
    private String headimgurl;

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
     * 关注时间戳
     */
    private String subscribeTime;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 分组ID
     */
    private Integer groupid;

    /**
     * 分组ID
     */
    private String subscribeScene;

    /**
     * 下一个openUserId
     */
    private String nextOpenid;

}