package com.princeoo.forum.vo;

import com.princeoo.forum.pojo.Forum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ForumVo implements Serializable {

    private Integer current;
    private Integer size;
    private Long total;
    private List<Forum> forumList;

}
