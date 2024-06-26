package com.soonphe.timber.ui.video;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.MenuAdapter;
import com.soonphe.timber.adapter.ViewPagerAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.PMenu;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.ui.data.DataContract;
import com.soonphe.timber.ui.data.DataPresenter;
import com.soonphe.timber.ui.video.movie.MovieFragment;
import com.soonphe.timber.ui.video.video.VideoFragment;
import com.soonphe.timber.widget.MyViewPager;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * 影视
 *
 * @author soonphe
 * @since 1.0
 */
public class CinemaActivity extends BaseActivity implements CinemaContract.View, DataContract.View {

    @Inject
    CinemaPresenter presenter;
    @Inject
    DataPresenter dataPresenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.movie_recycler_menu)
    RecyclerView movieRecyclerMenu;
    @BindView(R.id.movie_pager)
    MyViewPager moviePager;

    private MenuAdapter menuAdapter;

    private int menuChecked = 0;
    private List<Fragment> mCinemaFragments = new ArrayList<>();    //viewpager fragment集合
    List<PMenu> pMenus = new ArrayList<>(); //左部菜单集合
    private long mMovieTime;//模块停留时长

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
        return R.layout.activity_cinema;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        BarUtils.setStatusBarColor(this, 0);
        presenter.attachView(this);
        dataPresenter.attachView(this);
        mMovieTime = System.currentTimeMillis();
        mCinemaFragments.add(new MovieFragment());
        mCinemaFragments.add(new VideoFragment());
        moviePager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mCinemaFragments));
        moviePager.setOffscreenPageLimit(2); //设置ViewPager的缓存界面数，每一侧的界面数(默认是缓存相邻的)
        moviePager.setCurrentItem(0);


        pMenus.add(new PMenu(0, "电影"));
        pMenus.add(new PMenu(1, "小视频"));
        menuAdapter = new MenuAdapter(R.layout.item_menu, pMenus);
        movieRecyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        movieRecyclerMenu.addItemDecoration(new SpaceDecoration(1));
        movieRecyclerMenu.setAdapter(menuAdapter);
        movieRecyclerMenu.setNestedScrollingEnabled(false);

        menuAdapter.setOnItemClickListener((adapter, view1, position) -> {
            //点击变色
            if (position != menuChecked) {
                view1.setBackgroundColor(getResources().getColor(R.color.text_red));
                movieRecyclerMenu.getChildAt(menuChecked).setBackgroundColor(getResources().getColor(R.color.transparent));
                menuChecked = position;
                moviePager.setCurrentItem(position);

            }

        });

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //停留时长统计:单位S
        long movieTime = TimeUtils.getTimeSpan(mMovieTime, System.currentTimeMillis(), TimeConstants.SEC);
        String phone = SPUtils.getInstance().getString(Constants.REGIST_PHONE, "");
        //判断是否为管理员
        if (!"111111".equals(phone)) {
            //获取统计信息
            TStats tStats = LitePal.where("phone = ?", phone + "").order("id desc").findFirst(TStats.class);
            if (tStats != null) {
                tStats.setMoviestime((int) (tStats.getMoviestime() + movieTime));
                boolean result = tStats.save();
                //判断当前网络可用且用户数据保存成功
                if (result) {
                    //上传所有数据
                    dataPresenter.uploadData(tStats);
                }
            }
        }
    }

    @Override
    public void uploadDataSuccess() {
        LogUtils.e("电影停留上传成功");
    }
}
