package com.jk.kq.service.impl;

import com.jk.kq.entity.OpenUser;
import com.jk.kq.mapper.OpenUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.TravelRecord;
import com.jk.kq.mapper.TravelRecordMapper;
import com.jk.kq.service.ITravelRecordService;
import com.jk.common.base.BaseServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 出差申请服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Service
public class TravelRecordServiceImpl extends BaseServiceImpl<TravelRecordMapper, TravelRecord> implements ITravelRecordService {

    @Autowired
    private TravelRecordMapper travelRecordMapper;


    @Autowired
    private OpenUserMapper openUserMapper;

    /**
     * 通过openUserId查找当月的出差记录
     *
     * @param openUserId
     * @param date
     * @return
     */
    @Override
    public List<TravelRecord> selectByOpenUserIdAndDate(Long openUserId, Date date) {
        return travelRecordMapper.selectByOpenUserIdAndDate(openUserId, date);
    }

    /**
     * 新增用户出差申请
     *
     * @param openUser
     * @param travelRecord
     * @return 返回该申请需要推送的用户的集合
     */
    @Override
    public List<OpenUser> insert(OpenUser openUser, TravelRecord travelRecord) {
        travelRecord.setOpenUserId(openUser.getId());
        travelRecord.setName(openUser.getName());
        travelRecord.setOrganId(openUser.getOrganId());
        travelRecord.setOrgan(openUser.getOrgan());
        //查找用户的职位信息
//        Position position = positionService.selectById(openUser.getPositionId());
//        //用户的申请初始状态等于用户的职位审批等级
//        travelRecord.setState(position.getGrade());
//        List<OpenUser> checkList = null;
//        //一般员工
//        if (position.getGrade() == 0) {
//            //出差3天以下（含3天），呈部门负责人和分管领导审批；
//            if (travelRecord.getDay() <= 3) {
//                travelRecord.setTopState(2);
//            } else {
//                //出差3天以上（不含3天），呈部门负责人、分管领导和集团总经理审批。
//                travelRecord.setTopState(3);
//            }
//            //给同部门的上一级领导审批
//            checkList = openUserMapper.selectOpenUser(openUser.getOrganId(), position.getGrade() + 1);
//        } else if (position.getGrade() > 0) {
//            //集团部门正副职出差3天以下（含3天），呈分管领导和集团总经理审批；
//            if (travelRecord.getDay() <= 3) {
//                travelRecord.setTopState(3);
//            } else {
//                //出差3天以上（不含3天），呈分管领导、集团总经理和董事长审批。
//                travelRecord.setTopState(4);
//            }
//            //如果是公司部门负责人，那么查找分管领导进行推送
//            checkList = openUserMapper.selectLeaders(openUser.getOrganId(), position.getGrade() + 1);
//        }
//        //集团分管领导不走审批
//        travelRecordMapper.insert(travelRecord);
        return null;
    }

    /**
     * 找该用户等级可以审核的出差申请
     *
     * @param queryMap
     * @return
     */
    @Override
    public List selectToCheck(Map queryMap) {
        return travelRecordMapper.selectToCheck(queryMap);
    }
}
