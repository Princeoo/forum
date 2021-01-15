package com.princeoo.forum.message;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建者：Felix-刘胜
 * 创建时间：2016年 09月21日 18:33
 * 功能说明：每一个请求的消息响应实体
 * 修改者：
 * 修改时间：
 * 修改说明：
 */
public class BaseResMessage<TEntity extends Entity> extends Entity {
    /**
     * 请求的响应时间
     * @ApiModelProperty 表示对实体的说明
     */
    @ApiModelProperty(value = "请求的响应时间")
    private long time;
    /**
     * 请求响应状态码
     */
    @ApiModelProperty(value = "请求响应状态码，只有当请求为200时为正确的一次操作")
    private int status;
    /**
     * 请求响应体书名
     */
    @ApiModelProperty(value = "响应说明")
    private String msg;
    /**
     * 响应体具体内容
     */
    @ApiModelProperty(value = "本次请求的返回值具体内容，为数组容器，那怕只有一个数据也为数组容器")
    private List<TEntity> content;

    public BaseResMessage() {
        this.time = new Date().getTime();
        this.msg = "未触发任何操作";
        this.status = 503;
        this.content = new ArrayList<>();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TEntity> getContent() {
        return content;
    }

    public BaseResMessage<TEntity> setContent(List<TEntity> content) {
        this.content = content;
        return this;
    }

    public BaseResMessage<TEntity> addContent(TEntity entity) {
        if (this.content == null) {
            this.content = new ArrayList<TEntity>();
        }
        this.content.add(entity);
        return this;
    }

    public BaseResMessage<TEntity> addContent(List<TEntity> list) {
        if (this.content == null) {
            this.content = new ArrayList<TEntity>();
        }
        this.content.addAll(list);
        return this;
    }

    public void clearContent() {
        if (this.content != null) {
            this.content.clear();
        } else {
            this.content = new ArrayList<TEntity>();
        }
    }

    /**
     * 系统错误
     */
    public BaseResMessage<TEntity> error() {
        this.status = 500;
        this.msg = "系统错误";
        this.content = new ArrayList<>();
        return this;
    }

    /**
     * 系统错误
     */
    public BaseResMessage<TEntity> error(int status, String msg) {
        this.status = status;
        this.msg = msg;
        this.content = new ArrayList<>();
        return this;
    }

    /**
     * 请求成功
     */
    public BaseResMessage<TEntity> success() {
        this.status = 200;
        this.msg = "请求成功！";
        return this;
    }

    /**
     * 请求成功！
     */
    public BaseResMessage<TEntity> success(String msg) {
        this.msg = msg;
        this.status = 200;
        return this;
    }

    public BaseResMessage<TEntity> error404() {
        this.status = 404;
        this.msg = "当前资源不存在或者没有找到！";
        this.content = new ArrayList<TEntity>();
        return this;
    }

    public BaseResMessage<TEntity> error404(String msg) {
        this.status = 404;
        this.msg = msg;
        this.content = new ArrayList<TEntity>();
        return this;
    }
}
