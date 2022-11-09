package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.base.BaseEntity;
import com.fosss.model.system.SysRole;
import com.fosss.model.system.SysUser;
import com.fosss.model.system.SysUserRole;
import com.fosss.model.vo.LoginVo;
import com.fosss.model.vo.RouterVo;
import com.fosss.model.vo.SysUserQueryVo;
import com.fosss.system.exception.MyException;
import com.fosss.system.mapper.SysRoleMapper;
import com.fosss.system.mapper.SysUserMapper;
import com.fosss.system.mapper.SysUserRoleMapper;
import com.fosss.system.service.SysMenuService;
import com.fosss.system.service.SysUserService;
import com.fosss.system.utils.JwtUtils;
import com.fosss.system.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;

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
        Map<String, Object> map = new HashMap<>();
        //查询所有角色
        List<SysRole> roleList = sysRoleMapper.selectList(null);
        map.put("roleList", roleList);

        //查询当前用户所拥有的角色的id
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId).orderByDesc(BaseEntity::getUpdateTime);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(wrapper);
        List<String> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            userRoleIds.add(userRole.getRoleId());
        }

        map.put("userRoleIds", userRoleIds);

        return map;
    }

    /**
     * 给用户分配角色
     * 2.删除当前用户的角色，添加新的角色来实现更改用户的角色
     */
    @Override
    public void doAssignRole(String userId, List<String> userRoleList) {
        //先删除当前用户的角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(wrapper);

        //在添加新的用户角色
        for (String userRole : userRoleList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(userRole);
            sysUserRoleMapper.insert(sysUserRole);
        }
    }

    /**
     * 登录接口
     */
    @Override
    public String loginByUsername(LoginVo loginVo) {
        //先判断是否存在当前用户及密码是否正确
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,loginVo.getUsername())
                .eq(SysUser::getPassword, MD5Util.convertMD5(loginVo.getPassword()));
        SysUser sysUser = baseMapper.selectOne(wrapper);
        if(sysUser==null){
            throw new MyException(20001,"用户名或密码错误");
        }
        //用户存在且密码正确时，判断用户状态
        if(sysUser.getStatus()==0){
            throw new MyException(20001,"该用户已被禁用，请联系管理员");
        }

        //都没有问题后，获取token并返回
        String token = JwtUtils.getJwtToken(sysUser.getId(), sysUser.getUsername());
        return token;
    }
    //根据用户id查询用户基本信息、菜单权限和按钮权限
    @Override
    public Map<String, Object> getUserInfo(String username) {
        //用map集合封装结果信息
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        resultMap.put("username",username);

        //查询用户基本信息
        LambdaQueryWrapper<SysUser> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser user = baseMapper.selectOne(wrapper);

        //查询该用户的菜单权限
        List<RouterVo> routerVoList=sysMenuService.getMenuByUsername(user.getId());
        resultMap.put("routers",routerVoList);

        //查询该用户的按钮权限
        List<String> permsList=sysMenuService.getButtonByUsername(user.getId());
        resultMap.put("buttons",permsList);
        return resultMap;
    }

}
















