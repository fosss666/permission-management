package com.fosss.system.controller;

import com.fosss.model.system.SysRole;
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
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色
     */
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public R findAll() {
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
}


















