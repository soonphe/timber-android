package com.soonphe.timber.ui.web;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;

import butterknife.BindView;


/**
 * webview页面加载
 *
 * @author soonphe
 * @since 1.0
 */
public class WebviewActivity extends BaseActivity {
    @BindView(R.id.fl_web)
    FrameLayout flWeb;

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void initInjector() {

    }


}
