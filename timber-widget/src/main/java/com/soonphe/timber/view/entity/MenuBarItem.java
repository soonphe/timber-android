package com.soonphe.timber.view.entity;

import java.io.Serializable;

/**
 * 菜单小图标
 *
 * @author soonphe
 * @since 1.0
 */
public class MenuBarItem implements Serializable {

    /**
     * 菜单ID，可用作跳转使用
     */
    private int id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String picUrl;
    //状态 图标是否可用
//    private Integer state;
    //类型 同一个类型的优先排列在一起
//    private Integer type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return  name;
    }
}
