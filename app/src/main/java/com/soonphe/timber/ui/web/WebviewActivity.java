package com.soonphe.timber.ui.web;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;

import butterknife.BindView;


/**
 * @Author soonphe
 * @Date 2018-08-28 17:12
 * @Description webview页面加载
 */
public class WebviewActivity extends BaseActivity {
    @BindView(R.id.fl_web)
    FrameLayout flWeb;

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initParms(Bundle parms) {

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
