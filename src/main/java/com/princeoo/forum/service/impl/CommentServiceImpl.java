package com.princeoo.forum.service.impl;

import com.princeoo.forum.pojo.Comment;
import com.princeoo.forum.mapper.CommentMapper;
import com.princeoo.forum.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.princeoo.forum.vo.CommentAndUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author princeoo
 * @since 2021-02-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();



    @Override
    public List<CommentAndUserVo> findCommentsByBlogId(String blogId) {
        //查询出父节点
        List<CommentAndUserVo> comments = commentMapper.findByBlogIdParentIdNull(blogId);
        for(CommentAndUserVo comment : comments){
            Long id = comment.getId();//这个评论的id
            String parentNickname1 = comment.getNickname();

            //            查询出子评论
            List<CommentAndUserVo> childComments = commentMapper.findByBlogIdParentIdNotNull(blogId,id);

            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyComments(tempReplys);
            //new一个新的用于下一个
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(String blogId, List<CommentAndUserVo> childComments, String parentNickname1) {
//        判断是否有一级子评论
        if(childComments.size() > 0){
//                循环找出子评论的id
            for(CommentAndUserVo childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
//                    查询出子二级评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(String blogId, Long childId, String parentNickname1) {
//        根据子一级评论的id找到子二级评论
        List<CommentAndUserVo> replayComments = commentMapper.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(CommentAndUserVo replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

}
