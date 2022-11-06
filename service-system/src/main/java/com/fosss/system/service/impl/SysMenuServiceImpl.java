package com.fosss.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fosss.model.system.SysMenu;
import com.fosss.system.mapper.SysMenuMapper;
import com.fosss.system.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public SysMenu getMenusTree() {
        return null;
    }
}















