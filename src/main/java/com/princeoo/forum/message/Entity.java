package com.princeoo.forum.message;

import com.google.gson.Gson;

/**
 * 创建者：Felix-刘胜
 * 创建时间：2016年 09月21日 17:14
 * 功能说明：基础实体类，所有的实体都应继承当前的基础实体类
 * 修改者：
 * 修改时间：
 * 修改说明：
 */
public class Entity<TEntity> {
    /**
     * 对象复制
     */
    public TEntity copy(Entity entity) {
        Gson gson = new Gson();
        TEntity copy = (TEntity) gson.fromJson(gson.toJson(entity), this.getClass());
        return copy;
    }

    /**
     * 转换为json字符串
     */
    public String toJson() {
        return new Gson().toJson(this);
    }

    public Entity() {
    }

    public TEntity toEntity(String json) {
        return (TEntity) new Gson().fromJson(json, this.getClass());
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
