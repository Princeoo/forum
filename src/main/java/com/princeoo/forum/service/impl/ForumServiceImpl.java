package com.princeoo.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.princeoo.forum.message.BaseResMessage;
import com.princeoo.forum.pojo.Forum;
import com.princeoo.forum.mapper.ForumMapper;
import com.princeoo.forum.pojo.ForumCategory;
import com.princeoo.forum.service.ForumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.princeoo.forum.vo.ForumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

//    public BaseResMessage<Forum> queryForumByBid(int bId){
//        QueryWrapper<Forum> forumWrapper = new QueryWrapper<>();
//        forumWrapper.eq("bid",bId);
//        List<Forum> list = forumMapper.selectList(forumWrapper);
//
//        return null != list ?
//                new BaseResMessage<Forum>().addContent(list).success("查询论坛单个帖子全部内容成功!") :
//                new BaseResMessage<Forum>().error(404, "查询论坛单个帖子全部内容失败!");
//    }



}
