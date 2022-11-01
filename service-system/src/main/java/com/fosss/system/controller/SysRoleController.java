package com.fosss.system.controller;

import com.fosss.model.system.SysRole;
import com.fosss.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/findAll")
    public List<SysRole> findAll(){
        return sysRoleService.list();
    }
}


















