package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.system.SysMenu;
import com.fosss.system.exception.MyException;
import com.fosss.system.mapper.SysMenuMapper;
import com.fosss.system.service.SysMenuService;
import com.fosss.system.utils.MenuHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author fosss
 * @since 2022-11-06
 */
@Transactional
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 查询菜单列表（树形结构）
     */
    @Override
    public List<SysMenu> getMenusTree() {

        //1.查询所有菜单
        List<SysMenu> menusList = baseMapper.selectList(null);

        //2.写一个工具类将菜单封装成树形结构
        List<SysMenu> menusTreeList = MenuHelper.buildTree(menusList);

        return menusTreeList;
    }

    /**
     * 删除菜单
     */
    @Override
    public boolean removeMenu(String id) {
        //需要判断要删除的菜单有没有子菜单，有的话则不能删除
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, id);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            //不能删除
            throw new MyException(20001, "请先删除子菜单");
        }
        int i = baseMapper.deleteById(id);
        return i > 0;
    }

    /**
     * 更改菜单状态
     */
    @Override
    public void updateStatus(String id, int status) {

        //进行修改，注意同时要将其子菜单的状态改掉
        //查询所有菜单
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        updateChildrenStatus(id, status, sysMenus);

    }

    /**
     * 递归更改菜单状态
     *
     * @param id     当前菜单id
     * @param status 要更改的状态
     */
    private void updateChildrenStatus(String id, int status, List<SysMenu> sysMenus) {
        //将当前菜单的状态先改掉
        SysMenu menu = baseMapper.selectById(id);
        menu.setStatus(status);
        baseMapper.updateById(menu);
        //递归寻找当前菜单的子菜单，改其状态
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId().equals(id)) {
                updateChildrenStatus(sysMenu.getId(), status, sysMenus);
            }
        }
    }
}















