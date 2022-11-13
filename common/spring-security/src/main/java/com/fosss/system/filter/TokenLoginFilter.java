package com.fosss.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fosss.model.vo.LoginVo;
import com.fosss.system.custom.CustomUser;
import com.fosss.system.result.R;
import com.fosss.system.result.ResponseUtil;
import com.fosss.system.service.SysLoginLogService;
import com.fosss.system.utils.IpUtil;
import com.fosss.system.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录过滤器
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;
    private SysLoginLogService sysLoginLogService;

    //指定登录接口及请求方式
    public TokenLoginFilter(AuthenticationManager authenticationManager,
                            RedisTemplate redisTemplate,
                            SysLoginLogService sysLoginLogService
    ) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login", "POST"));
        this.redisTemplate = redisTemplate;
        this.sysLoginLogService=sysLoginLogService;
    }

    //登录校验

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //登录成功调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        String token = JwtUtils.getJwtToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        //保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        //记录日志
        sysLoginLogService.recordLoginLog(customUser.getUsername(),1, IpUtil.getIpAddress(request),"登录成功");

        ResponseUtil.out(response, R.ok().data("token", token));
    }

    //登录失败调用
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        //记录日志
        sysLoginLogService.recordLoginLog("未知用户",0,IpUtil.getIpAddress(request),"登录失败");
        if (e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, R.error().message(e.getMessage()));
        } else {
            ResponseUtil.out(response, R.error().message("用户名或密码错误"));
        }
    }

}

























