package com.jk.sys.service.impl;


import com.jk.sys.entity.DeviceCmd;
import com.jk.sys.mapper.DeviceCmdMapper;
import com.jk.sys.service.DeviceCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年05月20日
 */
@Service
public class DeviceCmdServiceImpl implements DeviceCmdService {

    @Autowired
    private DeviceCmdMapper deviceCmdMapper;

    @Override
    public List<DeviceCmd> selectQueueCmd(String sn) {
        return deviceCmdMapper.selectQueueCmd(sn);
    }

    @Override
    public Integer insert(DeviceCmd deviceCmd) {
        return deviceCmdMapper.insert(deviceCmd);
    }

    /**
     * 将命令状态修改为已被消费
     *
     * @param msgId
     * @return
     */
    @Override
    public void updateStatusOk(String msgId) {
        deviceCmdMapper.updateStatusOk(msgId);
    }
}
