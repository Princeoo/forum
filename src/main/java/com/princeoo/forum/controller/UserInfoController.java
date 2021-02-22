package com.princeoo.forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.princeoo.forum.message.BaseResMessage;
import com.princeoo.forum.pojo.Forum;
import com.princeoo.forum.pojo.ForumCategory;
import com.princeoo.forum.pojo.UserInfo;
import com.princeoo.forum.service.UserInfoService;
import com.princeoo.forum.utils.RedisUtil;
import com.princeoo.forum.vo.ForumCategoryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private RedisUtil redisUtil;

    /**
     * 查询个人信息
     * @return
     */
    @RequestMapping(value = "/{uid}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询个人信息", httpMethod = "GET", notes = "查询个人信息",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object queryAllUserinfo(@PathVariable String uid) {
        UserInfo userInfo;
        if (!redisUtil.hasKey("uid")) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", uid);
            userInfo = userInfoService.getOne(queryWrapper);
            redisUtil.set("uid",userInfo);
        }else {
            userInfo = (UserInfo) redisUtil.get("uid");
        }
        return null != userInfo ?
                new BaseResMessage<UserInfo>().addContent(userInfo).success("查询个人信息成功!") :
                new BaseResMessage<UserInfo>().error(404, "查询个人信息失败!");

    }

    /**
     * 修改个人信息
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改个人信息", httpMethod = "POST", notes = "修改个人信息",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object updataUserinfo(@RequestBody UserInfo userInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",userInfo.getUid());
        boolean update = userInfoService.update(userInfo, queryWrapper);
        redisUtil.set("uid",userInfo);
        return true == update ?
                new BaseResMessage<UserInfo>().addContent(userInfo).success("修改个人信息成功!") :
                new BaseResMessage<UserInfo>().error(404, "修改个人信息失败!");

    }

}

