package com.fosss.system.service.impl;

import com.fosss.model.system.SysUser;
import com.fosss.system.custom.CustomUser;
import com.fosss.system.service.SysMenuService;
import com.fosss.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义获取用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserInfoByUsername(username);
        //增强健壮性
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (sysUser.getStatus() == 0) {
            throw new RuntimeException("该用户已被禁用");
        }
        //根据用户id查询权限数据
        List<String> sysPermissionList = sysMenuService.getButtonByUsername(sysUser.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : sysPermissionList) {
            new SimpleGrantedAuthority(perm.trim());
        }

        return new CustomUser(sysUser, authorities);
    }
}
