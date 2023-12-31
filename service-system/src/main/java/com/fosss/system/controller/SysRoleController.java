package com.fosss.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fosss.model.system.SysMenu;
import com.fosss.model.system.SysRole;
import com.fosss.model.vo.AssignMenuVo;
import com.fosss.model.vo.SysRoleQueryVo;
import com.fosss.system.annotation.Log;
import com.fosss.system.enums.BusinessType;
import com.fosss.system.enums.OperatorType;
import com.fosss.system.result.R;
import com.fosss.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色
     */
    @Log(title = "查询所有角色",businessType = BusinessType.OTHER,operatorType = OperatorType.OTHER)
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public R findAll() {
//        int i=1/0;
//        if (true) {
//            throw new MyException(20000, "自定义异常");
//        }
        List<SysRole> sysRoleList = sysRoleService.list();
        return R.ok().data("sysRoleList", sysRoleList);
    }

    /**
     * 逻辑删除
     */
    @Log(title = "角色管理",businessType = BusinessType.DELETE)
    @ApiOperation("逻辑删除")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return sysRoleService.removeById(id) ? R.ok() : R.error();
    }

    /**
     * 条件分页查询
     */
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public R getPageCondition(@PathVariable Long page, @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        IPage<SysRole> pageInfo = sysRoleService.getPageCondition(pageParam, sysRoleQueryVo);
        return R.ok().data("pageInfo", pageInfo);
    }

    /**
     * 添加角色
     */
    @Log(title = "角色管理",businessType = BusinessType.INSERT)
    @ApiOperation("添加角色")
    @PostMapping
    public R addRole(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.save(sysRole);
        return result ? R.ok() : R.error();
    }

    /**
     * 根据id查询角色
     */
    @ApiOperation("根据id查询")
    @GetMapping("{id}")
    public R getById(@PathVariable String id) {
        SysRole sysRole = sysRoleService.getById(id);
        return R.ok().data("sysRole", sysRole);
    }

    /**
     * 修改角色
     */
    @Log(title = "角色管理",businessType = BusinessType.UPDATE)
    @ApiOperation("修改角色")
    @PutMapping()
    public R updateRole(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateById(sysRole);
        return result ? R.ok() : R.error();
    }

    /**
     * 批量删除
     */
    @Log(title = "角色管理",businessType = BusinessType.DELETE)
    @ApiOperation("批量删除")
    @DeleteMapping
    public R deleteRoles(@RequestBody List<String> ids) {
        boolean result = sysRoleService.removeByIds(ids);
        return result ? R.ok() : R.error();
    }

    /**
     * 根据角色id显示所有菜单和已经分配过的菜单
     */
    @ApiOperation("显示所有菜单和已分配菜单")
    @GetMapping("/doAssign/{roleId}")
    public R getRoleMenus(@PathVariable String roleId) {
        List<SysMenu> sysMenus = sysRoleService.getRoleMenus(roleId);
        return R.ok().data("list", sysMenus);
    }

    /**
     * 根据角色id给角色分配菜单，先删除原有分配，再添加现有分配
     */
    @Log(title = "角色管理",businessType = BusinessType.ASSGIN)
    @ApiOperation("给角色分配菜单")
    @PostMapping("/doAssign")
    public R doAssignMenu(@RequestBody AssignMenuVo assignMenuVo){
        sysRoleService.doAssignMenu(assignMenuVo);
        return R.ok();
    }
}


















