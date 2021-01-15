package com.princeoo.forum.controller;


import com.princeoo.forum.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/user-info")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;



}

