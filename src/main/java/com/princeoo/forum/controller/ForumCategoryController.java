package com.princeoo.forum.controller;


import com.princeoo.forum.message.BaseResMessage;
import com.princeoo.forum.pojo.ForumCategory;
import com.princeoo.forum.service.ForumCategoryService;
import com.princeoo.forum.utils.RedisUtil;
import com.princeoo.forum.vo.ForumCategoryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
@RestController
@RequestMapping("/forum-category")
public class ForumCategoryController {


    @Autowired
    private ForumCategoryService forumCategoryService;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 查询论坛全部主题
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询论坛全部主题", httpMethod = "GET", notes = "查询论坛全部主题",response = BaseResMessage.class,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object calendar() {
        ForumCategoryVo forumCategoryVo = new ForumCategoryVo();
        List<ForumCategory>  categoryList = new ArrayList<>();
        if (!redisUtil.hasKey("category")){
        categoryList = forumCategoryService.list(null);
        redisUtil.set("category",categoryList);
        }else {
            categoryList = (List<ForumCategory>)redisUtil.get("category");
        }
        String categoryId = (String) session.getAttribute("categoryId");
        if (categoryId != null && !categoryId.equals("null")) {
            ForumCategory lists = forumCategoryService.getById(categoryId);
            forumCategoryVo.setCategoryId(Integer.valueOf(categoryId));
            forumCategoryVo.setCategoryName(lists.getCategory());
        }
        forumCategoryVo.setCategoryList(categoryList);
        return null != forumCategoryVo ?
                    new BaseResMessage<ForumCategoryVo>().addContent(forumCategoryVo).success("查询论坛全部主题成功!") :
                    new BaseResMessage<ForumCategoryVo>().error(404, "查询论坛全部主题失败!");

    }

}

