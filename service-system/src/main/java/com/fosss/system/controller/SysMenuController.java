package com.fosss.system.controller;


import com.fosss.model.system.SysMenu;
import com.fosss.system.service.SysMenuService;
import com.fosss.system.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author fosss
 * @since 2022-11-06
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
@CrossOrigin
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询菜单列表（树形结构）
     */
    @ApiOperation("查询菜单列表")
    @GetMapping
    public R getMenusTree() {
        List<SysMenu> menusTreeList = sysMenuService.getMenusTree();
        return R.ok().data("menusTreeList", menusTreeList);
    }

    /**
     * 添加菜单
     */
    @ApiOperation("添加菜单")
    @PostMapping
    public R addMenu(@RequestBody SysMenu sysMenu) {
        boolean result = sysMenuService.save(sysMenu);
        return result ? R.ok() : R.error();
    }

    /**
     * 删除菜单
     */
    @ApiOperation("删除菜单")
    @DeleteMapping("{id}")
    public R removeMenu(@PathVariable String id) {
        boolean result = sysMenuService.removeMenu(id);
        return result ? R.ok() : R.error();
    }

    /**
     * 根据id查询菜单
     */
    @ApiOperation("根据id查询菜单")
    @GetMapping("{id}")
    public R getMenuById(@PathVariable String id) {
        SysMenu menu = sysMenuService.getById(id);
        return R.ok().data("menu", menu);
    }

    /**
     * 修改菜单
     */
    @ApiOperation("修改菜单")
    @PutMapping
    public R updateMenu(@RequestBody SysMenu sysMenu) {
        boolean result = sysMenuService.updateById(sysMenu);
        return result ? R.ok() : R.error();
    }

    /**
     * 更改菜单状态
     */
    @ApiOperation("更改菜单状态")
    @PutMapping("/{id}/{status}")
    public R updateStatus(@PathVariable String id, @PathVariable int status) {
        sysMenuService.updateStatus(id, status);
        return R.ok();
    }

}




























