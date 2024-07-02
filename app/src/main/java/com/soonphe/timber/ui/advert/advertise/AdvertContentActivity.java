package com.soonphe.timber.ui.advert.advertise;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.BarUtils;
import com.just.library.AgentWeb;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import org.litepal.LitePal;

import java.util.Objects;

import butterknife.BindView;


/**
 * 广告详情页
 *
 * @author soonphe
 * @since 1.0
 */
public class AdvertContentActivity extends BaseActivity {

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.fl_web)
    FrameLayout flWeb;

    @Override
    public int bindLayout() {
        return R.layout.activity_advert;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        BarUtils.setStatusBarColor(this, 0);
        String downloadContent = "www.baidu.com";
        //取传过来的分类ID
        Object advert = mOperation.getParameter("advert");
        if (Objects.nonNull(advert)) {
            int id = (int) advert;
            if (id>0){
                TAdvert tAdvert = LitePal.find(TAdvert.class,id);
                downloadContent = tAdvert.getDownloadContent();
            }
        }
        //初始化webview
        AgentWeb web = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(flWeb, new FrameLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback((view1, title) -> appTitle.setTitle("广告")) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go("http://"+downloadContent);
//                .go("file://"+downloadContent);
        web.getAgentWebSettings().getWebSettings().setSupportZoom(true);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void initInjector() {
//        getComponent().inject(this);
    }


}
