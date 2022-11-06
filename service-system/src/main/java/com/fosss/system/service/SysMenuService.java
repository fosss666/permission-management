package com.fosss.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fosss.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author fosss
 * @since 2022-11-06
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 查询菜单列表（树形结构）
     */
    List<SysMenu> getMenusTree();
    /**
     * 删除菜单
     */
    boolean removeMenu(String id);
    /**
     * 更改菜单状态
     */
    void updateStatus(String id, int status);
}
