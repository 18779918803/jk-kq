package com.jk.kq.entity;

import java.util.Date;

/**
 * 命令表实体类
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年04月17日
 */
public class DeviceCmd {

    private Integer id;

    /**
     * 设备号sn
     */
    private String sn;

    /**
     * 命令执行状态
     */
    private Integer status;

    /**
     * 命令编号
     */
    private String msgId;

    /**
     * 命令内容
     */
    private String command;

    /**
     * 执行结果
     */
    private Integer result;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}