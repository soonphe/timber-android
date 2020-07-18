package com.soonphe.timber.view.entity;

import java.io.Serializable;

/**
 * @Author anna
 * @Date 2017-12-01 17:01
 * @Descprition banner轮播图
 */
public class PCarousel implements Serializable {

    /**
     * id : 1
     * carouselCode : 1
     * picUrl : /banner/banner1.png
     * intervalTime : 2
     * state : 0
     * startTime : 2017-03-08 11:09:19
     * endTime : 2017-03-09 11:09:22
     * location : null
     * createTime : 2017-12-01 16:21:25
     * updateTime : 2017-12-01 16:21:25
     * delFlag : 0
     */

    private int id;
    private String carouselCode;
    private String picUrl;
    private String intervalTime;
    private int state;
    private String startTime;
    private String endTime;
    private String location;
    private String createTime;
    private String updateTime;
    private int delFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarouselCode() {
        return carouselCode;
    }

    public void setCarouselCode(String carouselCode) {
        this.carouselCode = carouselCode;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
