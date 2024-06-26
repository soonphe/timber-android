package com.soonphe.timber.ui.city.citydetail;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.CityArticleAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.entity.TCity;
import com.soonphe.timber.entity.TCityArticle;
import com.soonphe.timber.entity.TabEntity;
import com.soonphe.timber.ui.city.cityarticle.CityArticleActivity;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * @Author soonphe
 * @Date 2018-08-28 16:09
 * @Description 城市详情
 */
public class CitydetailActivity extends BaseActivity implements CitydetailContract.View {

    @Inject
    CitydetailPresenter presenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.tl_2)
    CommonTabLayout tl2;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    //city的ID
    int id;
    TCity tCity;

    CityArticleAdapter cityArticleAdapter;
    private int typeid = 0; //城市文章类型ID
    private String[] mTitles = {"文化", "美食", "生活", "旅游"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

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
        return R.layout.activity_city_detail;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {

        BarUtils.setStatusBarColor(this, 0);
        presenter.attachView(this);

        //取传过来的分类ID
        if (Objects.nonNull(mOperation.getParameter("city"))) {
            id = (int) mOperation.getParameter("city");
        }
        LogUtils.e("接受传过来的来城市ID:" + id);
        tCity = LitePal.where("tid = ?", id + "").findFirst(TCity.class);
        appTitle.setTitle(tCity.getName());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tl2.setTabData(mTabEntities);

        tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                typeid = position;
                presenter.getCityList(id + "", typeid + "");
            }

            @Override
            public void onTabReselect(int position) {

            }

        });

        cityArticleAdapter = new CityArticleAdapter(R.layout.item_city_article);
        rvList.setLayoutManager(new GridLayoutManager(this, 2));
        rvList.addItemDecoration(new SpaceDecoration(10));
        rvList.setAdapter(cityArticleAdapter);
        rvList.setNestedScrollingEnabled(false);

        cityArticleAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mOperation.addParameter("cityarticle", ((TCityArticle) adapter.getItem(position)).getTid());
            mOperation.forward(CityArticleActivity.class);
        });
    }

    @Override
    public void doBusiness(Context mContext) {

        presenter.getCityList(id + "", typeid + "");
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    public void getCityListSuccess(List<TCityArticle> list) {
        if (list.size() > 0) {
            cityArticleAdapter.setNewData(list);
        }
    }

}
