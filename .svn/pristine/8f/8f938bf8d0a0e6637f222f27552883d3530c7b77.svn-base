package com.jk.activiti.mapper;


import com.jk.activiti.domain.BizTodoItem;
import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 待办事项Mapper接口
 *
 * @author Xianlu Tech
 * @date 2019-11-08
 */
@Mapper
@Component
public interface BizTodoItemMapper extends BaseMapper<BizTodoItem> {
    /**
     * 查询待办事项
     *
     * @param id 待办事项ID
     * @return 待办事项
     */
    public BizTodoItem selectBizTodoItemById(Long id);

    /**
     * 查询待办事项列表
     *
     * @param param 待办事项
     * @return 待办事项集合
     */
    public List<BizTodoItem> selectBizTodoItemList(Map param);

    /**
     * 新增待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    public int insertBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 修改待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    public int updateBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 删除待办事项
     *
     * @param id 待办事项ID
     * @return 结果
     */
    public int deleteBizTodoItemById(Long id);

    /**
     * 批量删除待办事项
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizTodoItemByIds(String[] ids);


    BizTodoItem selectTodoItemByTaskId(@Param(value = "taskId") String taskId);


    List<String> selectTodoUserListByTaskId(@Param(value = "taskId") String taskId);


    BizTodoItem selectTodoItemByCondition(@Param(value = "taskId") String taskId, @Param(value = "todoUserId") String todoUserId);


    List<String> selectTodoUserByTaskId(@Param(value = "taskId") String taskId);
}
