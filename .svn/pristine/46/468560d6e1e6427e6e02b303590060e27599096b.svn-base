package com.jk.kq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 值班记录实体类
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月26日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_onduty_record")
public class OndutyRecord extends BaseEntity {

    /**
     * 姓名
     */
    private Integer openUserId;

    /**
     * 公司ID/部门ID
     */
    private Integer organId;

    /**
     * 公司/部门
     */
    private String organ;

    /**
     * 值班日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 状态
     */
    private Integer state;

}