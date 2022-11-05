package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.base.BaseEntity;
import com.fosss.model.system.SysRole;
import com.fosss.model.system.SysUser;
import com.fosss.model.system.SysUserRole;
import com.fosss.model.vo.SysUserQueryVo;
import com.fosss.system.mapper.SysRoleMapper;
import com.fosss.system.mapper.SysUserMapper;
import com.fosss.system.mapper.SysUserRoleMapper;
import com.fosss.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

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

    /**
     * 更改用户状态
     */
    @Override
    public void updateStatus(String id, int status) {
        //查询用户信息
        SysUser sysUser = baseMapper.selectById(id);
        //设置用户状态
        sysUser.setStatus(status);
        //进行修改
        baseMapper.updateById(sysUser);
    }

    /**
     * 给用户分配角色
     * 1.查询所有角色和当前用户已经分配的角色
     */
    @Override
    public Map<String, Object> getUserRoles(String userId) {
        Map<String,Object> map=new HashMap<>();
        //查询所有角色
        List<SysRole> roleList = sysRoleMapper.selectList(null);
        map.put("roleList",roleList);

        //查询当前用户所拥有的角色
        LambdaQueryWrapper<SysUserRole> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,userId).orderByDesc(BaseEntity::getUpdateTime);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(wrapper);
        map.put("userRoles",userRoles);

        return map;
    }
    /**
     * 给用户分配角色
     * 2.删除当前用户的角色，添加新的角色来实现更改用户的角色
     */
    @Override
    public void doAssignRole(String userId, List<String> userRoleList) {
        //先删除当前用户的角色
        LambdaQueryWrapper<SysUserRole> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,userId);
        sysUserRoleMapper.delete(wrapper);

        //在添加新的用户角色
        for (String userRole : userRoleList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(userRole);
            sysUserRoleMapper.insert(sysUserRole);
        }
    }

}
















