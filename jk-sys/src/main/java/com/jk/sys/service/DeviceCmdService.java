package com.jk.sys.service;

import com.jk.sys.entity.DeviceCmd;

import java.util.List;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年05月20日
 */
public interface DeviceCmdService {

    List<DeviceCmd> selectQueueCmd(String sn);

    Integer insert(DeviceCmd deviceCmd);

    /**
     * 将命令状态修改为已被消费
     *
     * @param msgId
     * @return
     */
    void updateStatusOk(String msgId);
}
