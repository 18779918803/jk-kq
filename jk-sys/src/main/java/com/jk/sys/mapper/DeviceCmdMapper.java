package com.jk.sys.mapper;

import com.jk.sys.entity.DeviceCmd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 命令表映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年04月17日
 */
@Mapper
@Repository
public interface DeviceCmdMapper {

    /**
     * 通过设备号查找队列命令
     *
     * @param sn
     * @return
     */
    List<DeviceCmd> selectQueueCmd(String sn);

    /**
     * 新增一个命令实体类
     *
     * @param deviceCmd
     * @return
     */
    Integer insert(DeviceCmd deviceCmd);

    /**
     * 将命令状态修改为已被消费
     *
     * @param msgId
     * @return
     */
    void updateStatusOk(@Param("msgId") String msgId);


}
