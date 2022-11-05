package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.base.BaseEntity;
import com.fosss.model.system.SysUser;
import com.fosss.model.vo.SysUserQueryVo;
import com.fosss.system.mapper.SysUserMapper;
import com.fosss.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author fosss
 * @since 2022-11-04
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public Page<SysUser> getPageCondition(Long page, Long limit, SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(!StringUtils.isEmpty(sysUserQueryVo.getKeyword()), SysUser::getUsername, sysUserQueryVo.getKeyword())
                .or().like(!StringUtils.isEmpty(sysUserQueryVo.getKeyword()), SysUser::getName, sysUserQueryVo.getKeyword())
                .or().like(!StringUtils.isEmpty(sysUserQueryVo.getKeyword()), SysUser::getPhone, sysUserQueryVo.getKeyword())
                .between(!StringUtils.isEmpty(sysUserQueryVo.getCreateTimeBegin()) && !StringUtils.isEmpty(sysUserQueryVo.getCreateTimeEnd()), SysUser::getCreateTime, sysUserQueryVo.getCreateTimeBegin(), sysUserQueryVo.getCreateTimeEnd())
                .orderByDesc(BaseEntity::getUpdateTime);
        return baseMapper.selectPage(pageParam, wrapper);
    }
}
















