package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.kq.entity.LeaveRecord;
import com.jk.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 请假申请映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Mapper
public interface LeaveRecordMapper extends BaseMapper<LeaveRecord> {

    List selectHistory(@Param("userId") Integer userId, @Param("date") String date);

    /**
     * 查找用户可以审核的请假申请
     *
     * @param queryMap
     * @return
     */
    List selectToCheck(Map queryMap);

    List<User> getUserByRoleAndOrgan(@Param("name") String name, @Param("organId")  Integer organId);




    List<String> getUserOrganByOrgan(@Param("organId") Integer organId,@Param("roleName") String roleName);


}
