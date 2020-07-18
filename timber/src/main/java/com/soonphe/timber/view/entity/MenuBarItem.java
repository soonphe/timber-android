package com.soonphe.timber.view.entity;

import java.io.Serializable;

/**
 * @Author soonphe
 * @Date 2017-11-22 13:54
 * @Descprition 菜单小图标
 */
public class MenuBarItem implements Serializable {

    private int id;
    private String name;
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
