package com.princeoo.forum.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.princeoo.forum.pojo.Forum;
import com.princeoo.forum.pojo.ForumCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ForumCategoryPageVo implements Serializable {

    private Integer current;
    private Integer size;
    private Long total;
    private String categoryName;
    private Integer categoryId;
    private List<Forum> forumList;
    private List<ForumCategory> categoryList;
}
