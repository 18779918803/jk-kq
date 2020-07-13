package com.jk.api.dto.sw;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReceivingRecordsDTO implements Serializable {

    private Integer id;
    /**
     * 来文字号
     */
    private String receivedSn;

    /**
     * 收文日期
     */
    @DateTimeFormat(pattern = "yyyy年MM月dd日 HH时mm分")
    private Date receivedDate;

    /**
     * 来文单位
     */
    private Integer receivedOrg;

    /**
     * 标题
     */
    private String title;

    /**
     * 密级
     */
    private Integer securityClassification;

    /**
     * 重要程度
     */
    private Integer importance;

    /**
     * 拟办意见
     */
    private String propose;

    /**
     * 备注
     */
    private String remark;

    /**
     * 类别
     */
    private Integer category;

    /**
     * 归档
     */
    private Integer complete;

    /**
     * 附件
     */
    private String attachment;

    /**
     * 可编辑
     */
    private Integer editable;

    /**
     * 文件文本
     */
    private String text;

    private String editableName;
    private String completeName;
    private String importanceName;
    private String securityClassificationName;
    private String receivedOrgName;
}
