package com.fosss.system.controller;

import com.fosss.model.vo.LoginVo;
import com.fosss.system.result.R;
import com.fosss.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/system/index")
@CrossOrigin//解决跨域

public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录接口
     * {"code":20000,"data":{"token":"admin-token"}}
     */
//    @PostMapping("/login")
//    public R login(){
//        return R.ok().data("token","admin-token");
//    }
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = sysUserService.loginByUsername(loginVo);
        return R.ok().data("token", token);
    }

    /**
     * 用户详情
     * {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
     */
    @GetMapping("info")
    public R loginInfo() {
        return R.ok()
                .data("roles", "admin")
                .data("introduction", "I am a super administrator")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name", "Super Admin FOSSS");
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }
}





























