package com.fosss.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fosss.model.system.SysRole;
import com.fosss.model.vo.SysRoleQueryVo;
import com.fosss.system.exception.MyException;
import com.fosss.system.service.SysRoleService;
import com.fosss.system.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色
     */
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public R findAll() {
//        int i=1/0;
        if (true) {
            throw new MyException(20000, "自定义异常");
        }
        List<SysRole> sysRoleList = sysRoleService.list();
        return R.ok().data("sysRoleList", sysRoleList);
    }

    /**
     * 逻辑删除
     */
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
    @ApiOperation("修改角色")
    @PutMapping()
    public R updateRole(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateById(sysRole);
        return result ? R.ok() : R.error();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除")
    @DeleteMapping
    public R deleteRoles(@RequestBody List<Integer> ids) {
        boolean result = sysRoleService.removeByIds(ids);
        return result ? R.ok() : R.error();
    }
}


















