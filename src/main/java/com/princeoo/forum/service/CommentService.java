package com.princeoo.forum.service;

import com.princeoo.forum.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.princeoo.forum.vo.CommentAndUserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author princeoo
 * @since 2021-02-08
 */
public interface CommentService extends IService<Comment> {

    List<CommentAndUserVo> findCommentsByBlogId(String bId);


}
