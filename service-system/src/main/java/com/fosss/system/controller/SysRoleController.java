package com.fosss.system.controller;

import com.fosss.model.system.SysRole;
import com.fosss.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色
     */
    @GetMapping("/findAll")
    public List<SysRole> findAll(){
        return sysRoleService.list();
    }

    /**
     * 逻辑删除
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id){
        return sysRoleService.removeById(id);
    }
}


















