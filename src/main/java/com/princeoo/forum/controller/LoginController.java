package com.princeoo.forum.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.princeoo.forum.pojo.User;
import com.princeoo.forum.service.UserInfoService;
import com.princeoo.forum.service.UserService;
import com.princeoo.forum.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

//    @Autowired
//    InviteService inviteService;
    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpSession session;

    @GetMapping({"/","/index"})
    public String index(Authentication authentication){
        Map<Object, Object> map = redisUtil.hmget("user");
        JSONObject jsonObject = JSONObject.parseObject((String)map.get(authentication.getPrincipal()));
        User user = JSON.toJavaObject(jsonObject,User.class);
        session.setAttribute("loginUser",user);
        return "index";
    }

//    @GetMapping("/toLogin")
//    public String toLogin(){
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String toRegister(){
//        return "register";
//    }

//    // 注册业务
//    @PostMapping("/register")
//    public String register(RegisterForm registerForm, Model model){
//        KuangUtils.print("注册表单信息："+registerForm.toString());
//        // 表单密码重复判断
//        if (!registerForm.getPassword().equals(registerForm.getRepassword())){
//            model.addAttribute("registerMsg","密码输入有误");
//            return "register";
//        }
//        // 用户名已存在
//        User hasUser = userService.getOne(new QueryWrapper<User>().eq("username", registerForm.getUsername()));
//        if (hasUser!=null){
//            model.addAttribute("registerMsg","用户名已存在");
//            return "register";
//        }
//
//        // 验证邀请码
//        Invite invite = inviteService.getOne(new QueryWrapper<Invite>().eq("code", registerForm.getCode()));
//        if (invite==null){
//            model.addAttribute("registerMsg","邀请码不存在");
//            return "register";
//        }else {
//            // 邀请码存在，判断邀请码是否有效
//            if (invite.getStatus()==1){
//                model.addAttribute("registerMsg","邀请码已被使用");
//                return "register";
//            }else {
//                // 构建用户对象
//                User user = new User();
//                user.setUid(KuangUtils.getUuid()); // 用户唯一id
//                user.setRoleId(2);
//                user.setUsername(registerForm.getUsername());
//                // 密码加密
//                String bCryptPassword = new BCryptPasswordEncoder().encode(registerForm.getPassword());
//                user.setPassword(bCryptPassword);
//                user.setGmtCreate(KuangUtils.getTime());
//                user.setLoginDate(KuangUtils.getTime());
//                // 保存对象！
//                userService.save(user);
//                KuangUtils.print("新用户注册成功："+user);
//
//                // 激活邀请码
//                invite.setActiveTime(KuangUtils.getTime());
//                invite.setStatus(1);
//                invite.setUid(user.getUid());
//                inviteService.updateById(invite);
//
//                // todo: 用户信息
//                userInfoService.save(new UserInfo().setUid(user.getUid()));
//
//                // 注册成功，重定向到登录页面
//                return "redirect:/toLogin";
//            }
//        }
//    }

}
