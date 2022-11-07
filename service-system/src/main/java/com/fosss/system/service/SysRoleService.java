package com.fosss.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fosss.model.system.SysMenu;
import com.fosss.model.system.SysRole;
import com.fosss.model.vo.AssignMenuVo;
import com.fosss.model.vo.SysRoleQueryVo;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> getPageCondition(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);
    /**
     * 根据角色id显示所有菜单和已经分配过的菜单
     */
    List<SysMenu> getRoleMenus(String roleId);
    /**
     * 根据角色id给角色分配菜单，先删除原有分配，再添加现有分配
     */
    void doAssignMenu(AssignMenuVo assignMenuVo);
}
