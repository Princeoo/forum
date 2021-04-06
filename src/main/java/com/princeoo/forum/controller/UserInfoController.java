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
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    //获取系统文件分隔符
    private static String seperator = System.getProperty("file.separator");
    //乱码问题加了toURI,获取resource路径
    private static String basePath;
    static {
        try {
            basePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();//字节码文件地址/Library/项目/forum/target/classes/static
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改个人头像
     * @return
     */
    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改个人头像", httpMethod = "POST", notes = "修改个人头像",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        //获取操作系统名称
        String os = System.getProperty("os.name");
        String filePath;//文件路径
        //判断操作系统路径
        if (os.toLowerCase().startsWith("win")){
            filePath = "D:/uploadBaseDir/productPic/";
        }else {
            filePath = "/Users/princegg/Desktop/";//没权限只能放桌面
        }
        //由于win的分隔符不一样，所以需要替换
        filePath = filePath.replace("/",seperator);


        fileName = UUID.randomUUID() + suffixName; //随机uuid+后缀（新文件名）
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            Thumbnails.of(file.getInputStream()).size(300,300)//传入的路径
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/static/head.jpg")),0.1f)//添加水印
                    .outputQuality(1f).toFile(dest);//传入本机新路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo:更新数据库即可
        String filename = "/productPic/" + fileName;
        model.addAttribute("filename", filename);
        return "file";
    }


}

