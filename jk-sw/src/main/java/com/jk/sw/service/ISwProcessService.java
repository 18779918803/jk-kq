package com.jk.sw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.base.IBaseService;
import com.jk.common.bean.ReturnBean;
import com.jk.sw.entity.SwProcess;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.entity.dto.InstructionDTO;
import com.jk.sw.entity.dto.RevocationDTO;

import java.util.Collection;
import java.util.Map;


/**
 * 流程服务接口层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月11日
 */
public interface ISwProcessService extends IBaseService<SwProcess> {

    IPage processApproverList(Map para);

    /**审批*/
    ReturnBean instruction(InstructionDTO dto);

    /**撤销审批*/
    boolean revoke(Integer taskId);

    boolean insertBatch(Collection<SwProcess> entityList, int batchSize);
}
