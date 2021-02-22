package com.princeoo.forum.vo;

import com.princeoo.forum.pojo.Forum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ForumUserRoleVo extends Forum implements Serializable {

    private Integer roleId;


}
