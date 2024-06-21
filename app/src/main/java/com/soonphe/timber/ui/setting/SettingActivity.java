package com.soonphe.timber.ui.setting;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.BarUtils;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.MenuAdapter;
import com.soonphe.timber.adapter.ViewPagerAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.entity.PMenu;
import com.soonphe.timber.ui.setting.aboutus.AboutUsFragment;
import com.soonphe.timber.ui.setting.gaindata.GainDataFragment;
import com.soonphe.timber.ui.setting.hotspot.HotspotFragment;
import com.soonphe.timber.ui.setting.network.NetworkFragment;
import com.soonphe.timber.ui.setting.version.VersionFragment;
import com.soonphe.timber.widget.MyViewPager;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * 设置Activity
 *
 * @author soonphe
 * @since 1.0
 */
public class SettingActivity extends BaseActivity implements SettingContract.View {

    @Inject
    SettingPresenter presenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.recycler_menu)
    RecyclerView recyclerMenu;
    @BindView(R.id.mypager)
    MyViewPager mypager;

    private MenuAdapter menuAdapter;

    private int menuChecked = 0;
    private List<Fragment> mFragments = new ArrayList<>();  //viewpager fragment集合
    List<PMenu> pMenus = new ArrayList<>(); //左部菜单集合

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
        return R.layout.activity_settings;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {

        BarUtils.setStatusBarColor(this, 0);
        setTouchDissIm(true);
        //设置不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        presenter.attachView(this);
        mFragments.add(new GainDataFragment());
        mFragments.add(new VersionFragment());
        mFragments.add(new NetworkFragment());
        mFragments.add(new HotspotFragment());
        mFragments.add(new AboutUsFragment());
//        mFragments.add(new DownloadAllFragment());
        mypager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mypager.setOffscreenPageLimit(5); //设置ViewPager的缓存界面数，每一侧的界面数(默认是缓存相邻的)
        mypager.setCurrentItem(0);

        //创建左部菜单
        pMenus.add(new PMenu(0, "数据同步"));
        pMenus.add(new PMenu(1, "系统版本"));
        pMenus.add(new PMenu(2, "网络设置"));
        pMenus.add(new PMenu(4, "热点设置"));
        pMenus.add(new PMenu(5, "关于我们"));
//        pMenus.add(new PMenu(6, "下载管理"));
        menuAdapter = new MenuAdapter(R.layout.item_menu, pMenus);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenu.addItemDecoration(new SpaceDecoration(1));
        recyclerMenu.setAdapter(menuAdapter);
        recyclerMenu.setNestedScrollingEnabled(false);

        menuAdapter.setOnItemClickListener((adapter, view1, position) -> {
            //点击变色
            if (position != menuChecked) {
                view1.setBackgroundColor(getResources().getColor(R.color.text_red));
                recyclerMenu.getChildAt(menuChecked).setBackgroundColor(getResources().getColor(R.color.transparent));
                menuChecked = position;
                mypager.setCurrentItem(position);
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


}
