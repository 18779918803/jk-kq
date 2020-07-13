package com.jk.sw.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.jk.common.base.BaseServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import com.jk.sw.entity.SwUsable;
import com.jk.sw.mapper.SwUsableMapper;
import com.jk.sw.service.ISwUsableService;

import java.util.Collection;


/**
 * 文件可用范围服务接口实现层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月16日
 */
@Service
public class SwUsableServiceImpl extends BaseServiceImpl<SwUsableMapper, SwUsable> implements ISwUsableService {

    @Override
    public boolean insertBatch(Collection<SwUsable> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (SwUsable anEntityList : entityList) {
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
