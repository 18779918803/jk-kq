package com.jk.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.common.bean.ReturnBean;
import com.jk.sw.entity.dto.InstructionDTO;
import com.jk.sys.entity.Dic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SwService {
    /**查询某个收文审批页的详情*/
    ReturnBean instructionPage(Integer uid, Integer id);

    /**查询某个收文的详情*/
    ReturnBean seeInfoPage(Integer id);

    ReturnBean swText(Integer id);

    ReturnBean saveSwText(Integer id, String text);

    /**根据字典类型获取字典列表*/
    List<Dic> getDicList(String type);

    /**审批*/
    ReturnBean instruction(InstructionDTO dto);

    /**查询收文列表*/
    IPage selectUsableList(Serializable usable, Serializable uid, Map para);
}
