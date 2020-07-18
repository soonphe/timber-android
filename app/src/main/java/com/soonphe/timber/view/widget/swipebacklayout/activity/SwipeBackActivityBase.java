package com.soonphe.timber.view.widget.swipebacklayout.activity;


import com.soonphe.timber.view.widget.swipebacklayout.SwipeBackLayout;

public interface SwipeBackActivityBase {
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);
    public abstract void scrollToFinishActivity();

}
