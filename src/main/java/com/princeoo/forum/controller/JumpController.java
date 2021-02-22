package com.princeoo.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("jump")
public class JumpController {

    @Autowired
    HttpSession session;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping(value = "/login")
    public String jumpLogin() {
        return "login";

    }

    /**
     * 跳转注册页面
     * @return
     */
    @RequestMapping(value = "/register")
    public String jumpRegister() {
        return "register";

    }

    /**
     * 跳转查看所有论坛
     * @return
     */
    @GetMapping(value = "/forumList")
    public String jumpAllForum() {
        return "blog/list";

    }

    /**
     * 跳转新增帖子
     * @return
     */
    @GetMapping(value = "/forumWrite/{categoryId}")
    public String jumpForumWrite(@PathVariable String categoryId) {
        session.setAttribute("categoryId",categoryId);//获取分类的id传给前端默认选中分
        return "blog/write";
    }

    /**
     * 跳转新增分类
     * @return
     */
    @GetMapping(value = "/categoryWrite")
    public String jumpCategoryWrite() {
        return "blog/write-category";
    }

    /**
     * 跳转帖子编辑
     * @return
     */
    @GetMapping(value = "/editForumList/{bid}")
    public String jumpForumEditor(@PathVariable String bid) {
        session.setAttribute("bid",bid);
        return "blog/editor";
    }

    /**
     * 跳转帖子详情
     * @return
     */
    @GetMapping(value = "/readForumList/{bid}")
    public String jumpForumRead(@PathVariable String bid) {
        session.setAttribute("bid",bid);
        return "blog/read";
    }

    /**
     * 跳转个人主页
     * @return
     */
    @GetMapping(value = "/user")
    public String jumpUserinfo() {
        return "user/index";
    }

    /**
     * 跳转个人信息
     * @return
     */
    @GetMapping(value = "/user/settings")
    public String jumpUserSetting() {
        return "/user/settings";
    }

    /**
     * 跳转查看个人评论信息
     * @return
     */
    @GetMapping(value = "/user/comment")
    public String jumpUserComment() {
        return "/user/user-comment";
    }
}
