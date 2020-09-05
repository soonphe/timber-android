package com.soonphe.timber.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author anna
 * @Date 2017-12-07 16:57
 * @Descprition 商品
 */
public class PGoods implements Serializable {

    private int id;
    private String goodsCode;
    private Integer storeId;
    private Integer categoryId;
    private String name;
    private String discount;
    private String picUrl;
    private BigDecimal price;
    private Integer recommendValue;
    private Integer state;
    private String createTime;
    private String updateTime;
    private Integer delFlag;

    private String storeName;
    private String categoryName;

    public PGoods(Integer storeId, Integer categoryId, String name, String discount, String picUrl, BigDecimal price) {
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.name = name;
        this.discount = discount;
        this.picUrl = picUrl;
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getRecommendValue() {
        return recommendValue;
    }

    public void setRecommendValue(Integer recommendValue) {
        this.recommendValue = recommendValue;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
