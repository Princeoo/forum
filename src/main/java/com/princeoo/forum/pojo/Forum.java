package com.princeoo.forum.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.princeoo.forum.message.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tk_forum")
@ApiModel(value="Forum对象", description="")
public class Forum extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "帖子id")
    private String bid;

    @ApiModelProperty(value = "帖子标题")
    private String title;

    @ApiModelProperty(value = "帖子内容")
    private String content;

    @ApiModelProperty(value = "帖子图片")
    private String picture;

    @ApiModelProperty(value = "排序 0 普通  1 置顶")
    private Integer sort;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @ApiModelProperty(value = "作者id")
    private String authorId;

    @ApiModelProperty(value = "论坛主题id")
    private Integer categoryId;

    @ApiModelProperty(value = "帖子描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    private Date gmtUpdate;


}
