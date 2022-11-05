package com.fosss.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fosss.model.system.SysUser;
import com.fosss.model.vo.SysUserQueryVo;
import com.fosss.system.service.SysUserService;
import com.fosss.system.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fosss
 * @since 2022-11-04
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 条件分页查询
     */
    @ApiOperation("条件分页查询")
    @GetMapping("/{page}/{limit}")
    public R getPageCondition(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageInfo = sysUserService.getPageCondition(page, limit, sysUserQueryVo);
        return R.ok().data("pageInfo", pageInfo);
    }

    /**
     * 添加用户
     */
    @ApiOperation("添加用户")
    @PostMapping
    public R addUser(@RequestBody SysUser sysUser) {
        //先判断数据库中是否有该用户（有可能已经添加过，只是逻辑删除了 is_deleted=1）
        boolean result = sysUserService.save(sysUser);
        return result ? R.ok() : R.error();
    }

    /**
     * 根据id查询
     */
    @ApiOperation("根据id查询用户")
    @GetMapping("{id}")
    public R getUserById(@PathVariable String id) {
        SysUser user = sysUserService.getById(id);
        return R.ok().data("user", user);
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @PutMapping
    public R updateUser(@RequestBody SysUser sysUser) {
        boolean result = sysUserService.updateById(sysUser);
        return result ? R.ok() : R.error();
    }

    /**
     * 根据id删除用户
     */
    @ApiOperation("根据id删除用户")
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        boolean result = sysUserService.removeById(id);
        return result ? R.ok() : R.error();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除")
    @DeleteMapping
    public R removeUsers(@RequestBody List<String> ids) {
        boolean result = sysUserService.removeByIds(ids);
        return result ? R.ok() : R.error();
    }

    /**
     * 更改用户状态
     */
    @ApiOperation("更改用户状态")
    @PutMapping("/{id}/{status}")
    public R updateStatus(@PathVariable String id,@PathVariable int status){
        sysUserService.updateStatus(id, status);
        return R.ok();
    }
}

















