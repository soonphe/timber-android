package com.soonphe.timber.pojo.vo;


import com.soonphe.timber.constants.Constants;

/**
 * 基础分页VO
 *
 * @author soonphe
 * @since 1.0
 */
public class PageVo {

    private int pageNum = 1;
    private int pageSize = Constants.PAGE_SIZE;

    public PageVo() {
    }

    /**
     * 页码
     *
     * @param pageNum
     */
    public PageVo(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 页码和页长
     *
     * @param pageNum
     * @param pageSize
     */
    public PageVo(int pageNum, int pageSize) {

        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}