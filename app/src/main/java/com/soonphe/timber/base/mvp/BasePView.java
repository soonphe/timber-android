package com.soonphe.timber.base.mvp;

/**
 * BasePView 基础页面加载
 *
 * @author soonphe
 * @since 1.0
 */
public interface BasePView {

    /**
     * 开始加载
     */
    void startLoading();

    /**
     * 结束加载
     */
    void endLoading();

    /**
     * 错误异常
     * @param error
     */
    void onError(String error);
}
