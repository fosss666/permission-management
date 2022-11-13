package com.fosss.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fosss.model.system.SysLoginLog;
import com.fosss.model.vo.SysLoginLogQueryVo;
import com.fosss.system.result.R;
import com.fosss.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SysLoginLog管理", tags = "SysLoginLog管理")
@RestController
@RequestMapping(value="/admin/system/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService loginLogService;

    //条件分页查询登录日志
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public R index(@PathVariable long page,
                   @PathVariable long limit,
                   SysLoginLogQueryVo sysLoginLogQueryVo) {
        IPage<SysLoginLog> pageModel =  loginLogService.selectPage(page,limit,sysLoginLogQueryVo);
        return R.ok().data("pageInfo",pageModel);
    }
}
