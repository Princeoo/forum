package com.princeoo.forum.vo;

import com.princeoo.forum.message.Entity;
import com.princeoo.forum.pojo.Forum;
import com.princeoo.forum.pojo.ForumCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ForumCategoryVo extends Entity implements Serializable {

    private Integer categoryId;

    private String categoryName;

    private List<ForumCategory> categoryList;
}
