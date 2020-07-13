package com.jk.xcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.ReturnBean;
import com.jk.common.constant.GlobalConstant;
import com.jk.common.util.StringUtils;
import com.jk.xcx.mapper.XcxPageResMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.xcx.entity.XcxRoleRes;
import com.jk.xcx.mapper.XcxRoleResMapper;
import com.jk.xcx.service.IXcxRoleResService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 小程序页面角色关系表服务接口实现层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
@Service
public class XcxRoleResServiceImpl extends BaseServiceImpl<XcxRoleResMapper, XcxRoleRes> implements IXcxRoleResService {

    @Autowired
    private XcxRoleResMapper xcxRoleResMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnBean insertBatch(String resIds, Integer roleId) {
        if (ObjectUtils.isEmpty(roleId)){
            return ReturnBean.error("角色ID不能为空");
        }
        List<XcxRoleRes> list = null;
        if (StringUtils.isNotBlank(resIds)){
            String[] split = resIds.split(GlobalConstant.COMMA);
            list = new ArrayList<>(split.length);
            for (String s : split){
                XcxRoleRes xcxRoleRes = new XcxRoleRes();
                if (StringUtils.isNotBlank(s)){
                    xcxRoleRes.setResId(Integer.parseInt(s));
                }
                xcxRoleRes.setRoleId(roleId);
                list.add(xcxRoleRes);
            }
        }
        xcxRoleResMapper.delete(new QueryWrapper<XcxRoleRes>().eq("role_id", roleId));
        if (!CollectionUtils.isEmpty(list)){
            this.insertBatch(list, 1000);
        }
        return ReturnBean.ok();
    }

    @Override
    public ReturnBean getResByRoleId(Integer roleId) {
        List<XcxRoleRes> list = xcxRoleResMapper.selectList(new QueryWrapper<XcxRoleRes>().eq("role_id", roleId));
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)){
            for (XcxRoleRes res : list){
                sb.append(res.getResId());
                sb.append(GlobalConstant.COMMA);
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return ReturnBean.ok(sb.toString());
    }

    public boolean insertBatch(Collection<XcxRoleRes> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (XcxRoleRes anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }
}
