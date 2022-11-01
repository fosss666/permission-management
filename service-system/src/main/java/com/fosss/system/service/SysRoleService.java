package com.fosss.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fosss.model.system.SysRole;
import com.fosss.model.vo.SysRoleQueryVo;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> getPageCondition(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);
}
