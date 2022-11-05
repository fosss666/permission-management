package com.fosss.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fosss.model.system.SysUser;
import com.fosss.model.vo.SysUserQueryVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fosss
 * @since 2022-11-04
 */
public interface SysUserService extends IService<SysUser> {

    Page<SysUser> getPageCondition(Long page, Long limit, SysUserQueryVo sysUserQueryVo);
}
