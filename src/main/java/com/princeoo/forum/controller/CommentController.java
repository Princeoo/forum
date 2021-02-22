package com.princeoo.forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.princeoo.forum.message.BaseResMessage;
import com.princeoo.forum.pojo.Comment;
import com.princeoo.forum.pojo.Forum;
import com.princeoo.forum.pojo.ForumCategory;
import com.princeoo.forum.pojo.User;
import com.princeoo.forum.service.CommentService;
import com.princeoo.forum.service.ForumService;
import com.princeoo.forum.service.UserService;
import com.princeoo.forum.service.impl.CommentServiceImpl;
import com.princeoo.forum.utils.PrinceooUtils;
import com.princeoo.forum.vo.CommentAndUserVo;
import com.princeoo.forum.vo.CommentVo;
import com.princeoo.forum.vo.ForumCategoryPageVo;
import com.princeoo.forum.vo.ForumVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author princeoo
 * @since 2021-02-08
 */
@RestController
@RequestMapping("/forum-comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ForumService forumService;


    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    /**
     * 查询评论详情
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询评论详情", httpMethod = "GET", notes = "查询评论详情",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object queryComment() {
        String bid = (String) session.getAttribute("bid");
        List<CommentAndUserVo> commentsByBlogId = commentService.findCommentsByBlogId(bid);
        return commentsByBlogId;
    }

    /**
     * 查询当前用户所有评论
     * @return
     */
    @RequestMapping(value = "/{uid}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询当前用户所有评论", httpMethod = "GET", notes = "查询所有评论",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object queryAllComment(@PathVariable String uid) {
        Page<Comment> pageParam = new Page<>(1, 10);
        commentService.page(pageParam, new QueryWrapper<Comment>().eq("uid", uid));
        CommentVo commentVo = new CommentVo();
        commentVo.setCurrent(Math.toIntExact(pageParam.getCurrent()));
        commentVo.setSize(Math.toIntExact(pageParam.getSize()));
        commentVo.setTotal(pageParam.getTotal());
        commentVo.setCommentList(pageParam.getRecords());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        User user = userService.getOne(queryWrapper);
        commentVo.setAvatar(user.getAvatar());
        return commentVo;
    }

    /**
     * 新增评论
     * @return
     */
    @RequestMapping(value = "/insertComment",method = RequestMethod.POST)
    @ApiOperation(value = "新增评论", httpMethod = "POST", notes = "新增评论",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object insertForum(@RequestBody CommentAndUserVo commentVo) {
        String bid = (String) session.getAttribute("bid");
        User user = (User)session.getAttribute("loginUser");
        Comment comment = new Comment();
        comment.setUid(user.getUid());
        comment.setBid(bid);
        comment.setCreateTime(new Date());
        comment.setContent(commentVo.getContent());
        if (commentVo.getParentComment() != null && commentVo.getParentComment().getId() != null) {
            comment.setParentCommentId(commentVo.getParentComment().getId());
        }
        boolean b = commentService.save(comment);


        return false != b ?
                new BaseResMessage<Forum>().success("新增评论成功!") :
                new BaseResMessage<Forum>().error(404, "新增评论失败!");
    }

    /**
     * 删除单个评论
     * @return
     */
    @RequestMapping(value = "/deleteCommentByBid/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除单个评论", httpMethod = "GET", notes = "删除单个评论",response = BaseResMessage.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteCommentByBid(@PathVariable String id) {

        boolean b = commentService.removeById(Long.parseLong(id));
        return false != b ?
                new BaseResMessage<Forum>().success("删除评论成功!") :
                new BaseResMessage<Forum>().error(404, "删除评论失败!");
    }

}

