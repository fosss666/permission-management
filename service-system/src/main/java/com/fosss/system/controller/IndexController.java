package com.fosss.system.controller;

import com.fosss.system.util.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    /**
     * 登录接口
     * {"code":20000,"data":{"token":"admin-token"}}
     */
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin-token");
    }

    /**
     * 用户详情
     * {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
     */
    @GetMapping("info")
    public R loginInfo(){
        return R.ok()
                .data("roles","admin")
                .data("introduction","I am a super administrator")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name","Super Admin FOSSS");
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }
}





























