package com.jk.sw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.base.IBaseService;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.sw.entity.ReceivingRecords;

import java.io.Serializable;
import java.util.Map;


/**
 * 收文记录服务接口层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月06日
 */
public interface IReceivingRecordsService extends IBaseService<ReceivingRecords> {
    /**
     *根据可执行状态查询收文
     *
     */
    IPage selectUsableList(Serializable usable, Serializable uid, Map para);

    ReturnBean instructionPage(Integer id);

    ReturnBean instructionsBySwid(Integer id);

    ReturnBean complete(Integer id);

    ReturnBean saveText(ReceivingRecords rr);

    ReturnBean readSet(Integer id, Integer [] organIds);

    ReturnBean allotUser(Integer id);

    ReturnBean allot(Integer id, Integer [] uids);
}
