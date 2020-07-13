package com.jk.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.constant.GlobalConstant;
import com.jk.common.validator.Assert;
import com.jk.sys.entity.Organ;
import com.jk.sys.mapper.OrganMapper;
import com.jk.sys.service.IOrganService;
import com.jk.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 组织机构服务实现层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Service
public class OrganServiceImpl extends BaseServiceImpl<OrganMapper, Organ> implements IOrganService {

    @Autowired
    private IRoleService roleService;

    /**
     * 新增组织机构
     *
     * @param organ 组织机构
     * @return {@link boolean}
     */
    @Override
    @CacheEvict(value = "organCache", allEntries = true)
    public boolean insert(Organ organ) {
        if (Assert.isNull(organ.getPid())) {
            organ.setPid(GlobalConstant.DEFAULT_PID);
        }
        return super.insert(organ);
    }

    /**
     * 修改组织机构
     *
     * @param organ 组织机构
     * @return {@link boolean}
     */
    @Override
    @CacheEvict(value = "organCache", allEntries = true)
    public boolean updateById(Organ organ) {
        if (Assert.isNull(organ.getPid())) {
            organ.setPid(GlobalConstant.DEFAULT_PID);
        }
        return super.updateById(organ);
    }

    /**
     * 查询全部
     *
     * @return List<T>
     */
    @Override
    @Cacheable(value = "organCache", unless = "#result == null")
    public List<Organ> selectList() {
        return super.selectList();
    }

    /**
     * 根据pid查询组织机构
     *
     * @param pid 机构pid
     * @return {@link List<Organ>}
     */
    @Override
    @Cacheable(value = "organCache", unless = "#result == null")
    public List<Organ> selectByPid(Integer pid) {
        return baseMapper.selectByPid(pid);
    }

    /**
     * 根据机构code查询
     *
     * @return {@link Organ}
     */
    @Override
    @Cacheable(value = "organCache", unless = "#result == null")
    public Organ selectByCode(String organCode) {
        return selectOne(new LambdaQueryWrapper<Organ>().eq(Organ::getCode, organCode));
    }

    /**
     * 查询角色的部门资源
     *
     * @return List<Organ>
     */
    @Override
    @Cacheable(value = "organCache", key = "#roleId",unless = "#result == null")
    public List<Organ> selectRoleOrgan(Integer roleId) {
        return baseMapper.selectRoleOrgan(roleId);
    }

    /**
     * 删除组织结构
     *
     * @param id 组织机构id
     * @return {@link boolean}
     */
    @Override
    @CacheEvict(value = "organCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        boolean result = super.deleteById(id);
        List<Organ> regionList = selectList(new LambdaQueryWrapper<Organ>().eq(Organ::getPid, id));
        for (Organ organ : regionList) {
            deleteById(organ.getId());
        }
        return result;
    }
}
