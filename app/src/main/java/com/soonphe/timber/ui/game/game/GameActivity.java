package com.soonphe.timber.ui.game.game;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.GameAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.entity.TGame;
import com.soonphe.timber.entity.TGameType;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.entity.TabEntity;
import com.soonphe.timber.ui.advert.advertise.AdvertContentActivity;
import com.soonphe.timber.ui.data.DataContract;
import com.soonphe.timber.ui.data.DataPresenter;
import com.soonphe.timber.ui.game.gameplay.GamePlayActivity;
import com.soonphe.timber.view.widget.titlebar.AppTitle;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;


/**
 * 游戏Activity
 *
 * @author soonphe
 * @since 1.0
 */
public class GameActivity extends BaseActivity implements GameContract.View, DataContract.View {

    @Inject
    GamePresenter presenter;
    @Inject
    DataPresenter dataPresenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.banner_Game)
    Banner banner;
    //    @BindView(R.id.pagerTab)
//    TabLayout pagerTab;
    @BindView(R.id.tl_2)
    CommonTabLayout tl2;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private GameAdapter gameAdapter;
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
        return R.layout.activity_game;
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
        //banner图
        banner.setImageLoader(new GlideImageLoader());

        gameAdapter = new GameAdapter(R.layout.item_game);
        rvList.setLayoutManager(new GridLayoutManager(this, 3));
        rvList.addItemDecoration(new SpaceDecoration(10));
        rvList.setAdapter(gameAdapter);
        rvList.setNestedScrollingEnabled(false);

        gameAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mOperation.addParameter("game", ((TGame) adapter.getItem(position)).getTid());
            mOperation.forward(GamePlayActivity.class);
        });
    }

    /**
     * 轮播图加载图片
     */
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//            String url = Constants.BASE_IMAGE_URL + ((PCarousel) path).getPicUrl();
            Glide.with(context).load(((TAdvert) path).getDownloadPic()).into(imageView);
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getAdvertList();
        presenter.getTypeList();
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    public void getAdvertListSuccess(List<TAdvert> list) {
        banner.update(list);
        banner.setImages(list);
        banner.setDelayTime(4000);
        banner.start();

        banner.setOnBannerListener(position ->
        {
            mOperation.addParameter("advert", list.get(position).getId());
            mOperation.forward(AdvertContentActivity.class);
        });
    }

    @Override
    public void getTypeListSuccess(List<TGameType> list) {
        for (int i = 0; i < list.size(); i++) {
            mTabEntities.add(new TabEntity(list.get(i).getName()));
        }
        mTabEntities.add(new TabEntity("游戏类型1"));
        mTabEntities.add(new TabEntity("游戏类型2"));
        mTabEntities.add(new TabEntity("游戏类型3"));
        mTabEntities.add(new TabEntity("游戏类型4"));
        tl2.setTabData(mTabEntities);

        tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                presenter.getGameList(list.get(position).getTid());
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
//        presenter.getGameList(  list.get(0).getTid());
    }

    @Override
    public void getGameListSuccess(List<TGame> list) {
        if (list.size() > 0) {
            gameAdapter.setNewData(list);
        }
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
                tOpen.setGametime((int) (tOpen.getGametime() + stayDataTime));
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
        LogUtils.e("电影停留上传成功");
    }
}
