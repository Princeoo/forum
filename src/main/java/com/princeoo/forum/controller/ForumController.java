package com.princeoo.forum.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.princeoo.forum.message.BaseResMessage;
import com.princeoo.forum.pojo.Forum;
import com.princeoo.forum.pojo.ForumCategory;
import com.princeoo.forum.pojo.User;
import com.princeoo.forum.pojo.UserRole;
import com.princeoo.forum.service.ForumCategoryService;
import com.princeoo.forum.service.ForumService;
import com.princeoo.forum.service.UserRoleService;
import com.princeoo.forum.service.UserService;
import com.princeoo.forum.utils.PrinceooUtils;
import com.princeoo.forum.utils.RedisUtil;
import com.princeoo.forum.vo.ForumCategoryPageVo;
import com.princeoo.forum.vo.ForumCategoryVo;
import com.princeoo.forum.vo.ForumUserRoleVo;
import com.princeoo.forum.vo.ForumVo;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
@Controller
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumCategoryService categoryService;

    @Autowired
    private UserRoleService userRoleService;


    @Autowired
    HttpSession session;

    @Autowired
    private RedisUtil redisUtil;

//    /**
//     * 查询论坛单个帖子全部内容
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8", consumes = "application/json")
//    @ApiOperation(value = "查询论坛单个帖子全部内容", httpMethod = "GET", notes = "查询论坛单个帖子全部内容",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Object calendar(@Param("bid") int bid) {
//        return forumService.queryForumByBid(bid);
//    }  /**
       /**
     * 查看单个帖子详情
     * @return
     */
    @RequestMapping(value = "/queryForumByBid",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查看单个帖子详情", httpMethod = "GET", notes = "查看单个帖子详情",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object queryForumByBid() {
        String bid = (String) session.getAttribute("bid");
        User user = (User) session.getAttribute("loginUser");
        ForumUserRoleVo forumUserRoleVo = new ForumUserRoleVo();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bid",bid);
        Forum forum = forumService.getOne(queryWrapper);
        if (forum.getViews() != null) {
            forum.setViews(forum.getViews() + 1);
        }else {
            forum.setViews(1);
        }
        forumService.update(forum,queryWrapper);
        forum = forumService.getOne(queryWrapper);
        BeanUtils.copyProperties(forum,forumUserRoleVo);
        forumUserRoleVo.setRoleId(user.getRoleId());
        return forumUserRoleVo;
    }

    /**
     * 删除单个帖子详情
     * @return
     */
    @RequestMapping(value = "/deleteForumByBid",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除单个帖子详情", httpMethod = "GET", notes = "删除单个帖子详情",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteForumByBid() {
        String bid = (String) session.getAttribute("bid");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bid",bid);
        boolean b = forumService.remove(queryWrapper);
        return false != b ?
                new BaseResMessage<Forum>().success("删除帖子成功!") :
                new BaseResMessage<Forum>().error(404, "删除帖子失败!");
    }




    /**
     * 查询所有论坛
     * @return
     */
    @RequestMapping(value = "/page/{current}/{size}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询所有论坛", httpMethod = "GET", notes = "查询所有论坛",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object queryAllForum(@PathVariable String current,@PathVariable String size) {
        Page<Forum> pageParam = new Page<>(Integer.parseInt(current), Integer.parseInt(size));
        ForumCategoryPageVo forumVo = new ForumCategoryPageVo();
        QueryWrapper queryWrapper = new QueryWrapper();
        List<ForumCategory> categoryList;
        if (!redisUtil.hasKey("category")){
            categoryList = categoryService.list(null);
            redisUtil.set("category",categoryList);
        }else {
            categoryList = (ArrayList<ForumCategory>)redisUtil.get("category");
        }
        queryWrapper.orderByAsc("gmt_update");
        forumService.page(pageParam,queryWrapper);
        forumVo.setTotal(pageParam.getTotal());
        forumVo.setCurrent(Math.toIntExact(pageParam.getCurrent()));
        forumVo.setSize(Math.toIntExact(pageParam.getSize()));
        forumVo.setForumList(pageParam.getRecords());
        forumVo.setCategoryList(categoryList);
        return forumVo;
    }

    /**
     * 根据分类id查询论坛
     * @return
     */
    @RequestMapping(value = "/findCategoryById/{categoryId}/{categoryName}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据分类id查询论坛", httpMethod = "GET", notes = "根据分类查询论坛",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object queryAllForumByCatery(@PathVariable String categoryId,@PathVariable String categoryName) {
        Page<Forum> pageParam = new Page<>(1, 10);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_id",categoryId);
        forumService.page(pageParam,queryWrapper);

        List<ForumCategory> categoryList = categoryService.list(null);

        ForumCategoryPageVo forumVo = new ForumCategoryPageVo();
        forumVo.setTotal(pageParam.getTotal());
        forumVo.setCurrent(Math.toIntExact(pageParam.getCurrent()));
        forumVo.setSize(Math.toIntExact(pageParam.getSize()));
        forumVo.setForumList(pageParam.getRecords());
        forumVo.setCategoryList(categoryList);
        forumVo.setCategoryId(Integer.valueOf(categoryId));//获取当前的分类名
        forumVo.setCategoryName(categoryName);//获取当前的分类名
        return forumVo;
    }


    /**
     * 新增帖子
     * @return
     */
    @RequestMapping(value = "/insertForum",method = RequestMethod.POST)
    @ApiOperation(value = "新增帖子", httpMethod = "POST", notes = "新增帖子",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object insertForum(@RequestBody Forum forum) {
        forum.setBid(PrinceooUtils.getUuid());
        forum.setGmtCreate(PrinceooUtils.getTime());
        forum.setGmtUpdate(PrinceooUtils.getTime());

        User user = (User) session.getAttribute("loginUser");
        forum.setPicture(user.getAvatar());
        boolean b = forumService.save(forum);
        return false != b ?
                new BaseResMessage<Forum>().success("新增帖子成功!") :
                new BaseResMessage<Forum>().error(404, "新增帖子失败!");
    }

    /**
     * 编辑帖子
     * @return
     */
    @RequestMapping(value = "/editForum",method = RequestMethod.POST)
    @ApiOperation(value = "编辑帖子", httpMethod = "POST", notes = "编辑帖子",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editForum(@RequestBody Forum forum) {
        QueryWrapper<Forum> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", forum.getId());
        forum.setGmtUpdate(PrinceooUtils.getTime());
        boolean b = forumService.update(forum,queryWrapper);
        return false != b ?
                new BaseResMessage<Forum>().success("帖子修改成功!") :
                new BaseResMessage<Forum>().error(404, "帖子修改成失败!");
    }

    /**
     * 根据authorId查询用户的帖子
     * @return
     */
    @RequestMapping(value = "/queryForumByUid/{authorId}/{current}/{size}",method = RequestMethod.GET)
    @ApiOperation(value = "根据authorId查询用户的帖子", httpMethod = "GET", notes = "根据authorId查询用户的帖子",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object insertForum(@PathVariable String authorId,@PathVariable String current,@PathVariable String size) {
        Page<Forum> pageParam = new Page<>(Integer.parseInt(current), Integer.parseInt(size));
        QueryWrapper<Forum> queryWrapper = new QueryWrapper<>();

        User user = (User) session.getAttribute("loginUser");
        UserRole userRole = userRoleService.getById(user.getRoleId());


        if (userRole.getName().equals("admin")) {
            queryWrapper.eq("author_id", authorId);
            forumService.page(pageParam, queryWrapper);
        }else{
            forumService.page(pageParam, null);
        }
        ForumVo forumVo = new ForumVo();
        forumVo.setTotal(pageParam.getTotal());
        forumVo.setCurrent(Math.toIntExact(pageParam.getCurrent()));
        forumVo.setSize(Math.toIntExact(pageParam.getSize()));
        forumVo.setForumList(pageParam.getRecords());
        return forumVo;
    }
}

