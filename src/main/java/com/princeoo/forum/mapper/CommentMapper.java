package com.princeoo.forum.mapper;

import com.princeoo.forum.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.princeoo.forum.vo.CommentAndUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author princeoo
 * @since 2021-02-08
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

        //根据创建时间倒序来排
        List<CommentAndUserVo> findByBlogIdParentIdNull(@Param("bid") String bid);

        //查询一级回复
        List<CommentAndUserVo> findByBlogIdParentIdNotNull(@Param("bid") String bid, @Param("id") Long id);

        //查询二级回复
        List<CommentAndUserVo> findByBlogIdAndReplayId(@Param("bid") String bid,@Param("childId") Long childId);
}
