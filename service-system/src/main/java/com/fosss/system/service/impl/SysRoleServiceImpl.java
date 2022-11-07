package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.base.BaseEntity;
import com.fosss.model.system.SysMenu;
import com.fosss.model.system.SysRole;
import com.fosss.model.system.SysRoleMenu;
import com.fosss.model.vo.AssignMenuVo;
import com.fosss.model.vo.SysRoleQueryVo;
import com.fosss.system.mapper.SysMenuMapper;
import com.fosss.system.mapper.SysRoleMapper;
import com.fosss.system.mapper.SysRoleMenuMapper;
import com.fosss.system.service.SysRoleService;
import com.fosss.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

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

    /**
     * 根据角色id显示所有菜单和已经分配过的菜单
     */
    @Override
    public List<SysMenu> getRoleMenus(String roleId) {
        //先查询所有的菜单（状态是可操作的菜单）
        LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.eq(SysMenu::getStatus, 1);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(menuWrapper);

        //再查询当前角色已经分配的菜单
        LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
        roleMenuWrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(roleMenuWrapper);
        //封装所有的菜单id
        List<String> roleIdList = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            roleIdList.add(sysRoleMenu.getMenuId());
        }

        //将已经分配过的菜单的Select属性设为true
        for (SysMenu sysMenu : sysMenus) {
            if(roleIdList.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }
        }

        //构造树形结构
        List<SysMenu> list = MenuHelper.buildTree(sysMenus);
        return list;
    }
    /**
     * 根据角色id给角色分配菜单，先删除原有分配，再添加现有分配
     */
    @Override
    public void doAssignMenu(AssignMenuVo assignMenuVo) {
        //先删除掉当前角色已经分配的菜单
        LambdaQueryWrapper<SysRoleMenu> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);

        //再添加菜单
        for(String menuId:assignMenuVo.getMenuIdList()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }
}



















