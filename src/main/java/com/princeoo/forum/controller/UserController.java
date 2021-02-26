package com.princeoo.forum.controller;


import com.princeoo.forum.pojo.User;
import com.princeoo.forum.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpSession session;

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){

        return authentication;
    }

}

