package com.princeoo.forum.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()//所有人都可以访问
                .antMatchers("").hasRole("VIP1")//角色权限
                .antMatchers("").hasRole("VIP2");//角色权限
        //开启自动配置的登陆功能
        http.formLogin();//1./login请求来到登陆 2.重定向到/login?error表示登陆失败

    }
}
