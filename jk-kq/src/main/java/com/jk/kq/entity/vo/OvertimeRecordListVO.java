package com.jk.kq.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OvertimeRecordListVO {


    /**
     * 按类型统计汇总
     */
    private List<SumVo> sum;


    /**
     * 全部列表
     */
    private List<OvertimeRecordVo> list;


}
