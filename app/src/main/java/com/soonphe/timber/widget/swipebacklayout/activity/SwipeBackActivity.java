package com.soonphe.timber.widget.swipebacklayout.activity;

import android.os.Bundle;
import android.view.View;

import com.soonphe.timber.widget.swipebacklayout.SwipeBackLayout;
import com.soonphe.timber.widget.swipebacklayout.Utils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 滑动侧边栏返回组件
 *
 * @author soonphe
 * @since 1.0
 */
public class SwipeBackActivity extends AppCompatActivity implements
        SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {

        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();

    }
}
