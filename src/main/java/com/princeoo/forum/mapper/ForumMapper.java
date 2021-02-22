package com.princeoo.forum.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.princeoo.forum.pojo.Forum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Locale;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
public interface ForumMapper extends BaseMapper<Forum> {

//    List<ForumAndCategoryVo> getForumAndCategoryPageList(Page page, @Param("id") Integer id);
}
