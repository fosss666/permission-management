package com.fosss.system.service.impl;

import com.fosss.model.system.SysUser;
import com.fosss.system.mapper.UserMapper;
import com.fosss.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

}
