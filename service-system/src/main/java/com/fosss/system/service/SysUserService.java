package com.fosss.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fosss.model.system.SysUser;
import com.fosss.model.vo.SysUserQueryVo;

import java.util.List;
import java.util.Map;

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

    void updateStatus(String id, int status);

    /**
     * 给用户分配角色
     * 1.查询所有角色和当前用户已经分配的角色
     */
    Map<String, Object> getUserRoles(String userId);
    /**
     * 给用户分配角色
     * 2.删除当前用户的角色，添加新的角色来实现更改用户的角色
     */
    void doAssignRole(String userId, List<String> userRoleList);
}
