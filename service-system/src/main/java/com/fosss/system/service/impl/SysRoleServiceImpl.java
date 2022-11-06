package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.base.BaseEntity;
import com.fosss.model.system.SysRole;
import com.fosss.model.vo.SysRoleQueryVo;
import com.fosss.system.mapper.SysRoleMapper;
import com.fosss.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public IPage<SysRole> getPageCondition(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        //封装查询条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(!ObjectUtils.isEmpty(sysRoleQueryVo.getRoleName()), SysRole::getRoleName, sysRoleQueryVo.getRoleName())
                .orderByDesc(BaseEntity::getUpdateTime);
        Page<SysRole> sysRolePage = baseMapper.selectPage(pageParam, wrapper);
        return sysRolePage;
    }
}
