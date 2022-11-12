package com.fosss.system.service.impl;

import com.fosss.model.system.SysUser;
import com.fosss.system.custom.CustomUser;
import com.fosss.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 自定义获取用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

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
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
