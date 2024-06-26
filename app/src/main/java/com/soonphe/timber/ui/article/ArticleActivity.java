package com.soonphe.timber.ui.article;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.ArticleAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.TArticle;
import com.soonphe.timber.entity.TArticleType;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.entity.TabEntity;
import com.soonphe.timber.ui.article.article.ArticleContentActivity;
import com.soonphe.timber.ui.data.DataContract;
import com.soonphe.timber.ui.data.DataPresenter;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * @Author soonphe
 * @Date 2018-08-28 19:11
 * @Description 文章Activity
 */
public class ArticleActivity extends BaseActivity implements ArticleContract.View, DataContract.View {

    @Inject
    ArticlePresenter presenter;
    @Inject
    DataPresenter dataPresenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.tl_2)
    CommonTabLayout tl2;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private long stayTime;//模块停留时长

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        BarUtils.setStatusBarColor(this, 0);
        presenter.attachView(this);
        dataPresenter.attachView(this);
        stayTime = System.currentTimeMillis();

        articleAdapter = new ArticleAdapter(R.layout.item_article);
        rvList.setLayoutManager(new GridLayoutManager(this, 2));
        rvList.addItemDecoration(new SpaceDecoration(10));
        rvList.setAdapter(articleAdapter);
        rvList.setNestedScrollingEnabled(false);
        articleAdapter.setOnItemClickListener((adapter, view1, position) -> {
            TArticle tArticle = ((TArticle) adapter.getItem(position));
            //如果为视频直接全屏播放
            if (tArticle.getClassify() == 1) {
                JZVideoPlayerStandard.startFullscreen(this,
                        JZVideoPlayerStandard.class,
                        tArticle.getDownloadFile() + "",
                        tArticle.getTitle() + "");
            } else {
                mOperation.addParameter("article", ((TArticle) adapter.getItem(position)).getId());
                mOperation.forward(ArticleContentActivity.class);
            }
        });
    }

    /**
     * 避免切换为竖屏
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LogUtils.e(TAG, "竖屏");
        }
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            LogUtils.e(TAG, "横屏");
//        }
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getTypeList();
    }

    @Override
    public void getTypeListSuccess(List<TArticleType> list) {
        for (int i = 0; i < list.size(); i++) {
            mTabEntities.add(new TabEntity(list.get(i).getName()));
        }
        tl2.setTabData(mTabEntities);

        tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                LogUtils.e("当前typename:" + (mTabEntities.get(position)).getTabTitle());
                presenter.getArticleList(list.get(position).getTid());
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        presenter.getArticleList(list.get(0).getTid());
    }

    @Override
    public void getArticleListSuccess(List<TArticle> list) {

        if (list.size() > 0) {
            articleAdapter.setNewData(list);
        }
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //停留时长统计:单位S
        long stayDataTime = TimeUtils.getTimeSpan(stayTime, System.currentTimeMillis(), TimeConstants.SEC);
        String phone = SPUtils.getInstance().getString(Constants.REGIST_PHONE, "");
        //判断这里是否存在用户，如果存在则要记录数据
        if (!"111111".equals(phone)) {
            //判断今天是否创建过统计数据——有数据则更新数据+1
            TStats tOpen = LitePal.where("phone = ?", phone + "").order("id desc").findFirst(TStats.class);
            if (tOpen != null) {
                tOpen.setSubway(tOpen.getSubway() + 1);
                tOpen.setSubwaytime((int) (tOpen.getSubwaytime() + stayDataTime));
                boolean result = tOpen.save();
                //判断当前网络可用且用户数据保存成功
                if (result) {
                    //上传所有数据
                    dataPresenter.uploadData(tOpen);
                }
            }
        }
    }

    @Override
    public void uploadDataSuccess() {
        LogUtils.e("书吧停留上传成功");
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }



}
