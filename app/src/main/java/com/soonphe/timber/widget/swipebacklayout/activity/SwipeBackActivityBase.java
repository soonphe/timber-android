package com.soonphe.timber.widget.swipebacklayout.activity;


import com.soonphe.timber.widget.swipebacklayout.SwipeBackLayout;

public interface SwipeBackActivityBase {
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);
    public abstract void scrollToFinishActivity();

}
