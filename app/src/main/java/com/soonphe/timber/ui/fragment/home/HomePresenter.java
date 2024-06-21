package com.soonphe.timber.ui.fragment.home;

import android.view.View;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.pojo.vo.AdvertVo;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * @Author soonphe
 * @Date 2018-03-30 14:41
 * @Description 
 */
@PerActivity
public class HomePresenter extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    private AppApi api;

    @Inject
    public HomePresenter(AppApi api) {
        this.api = api;
    }

    @Override
    public void getCategoryMenuList(int parentId, int type) {
        mView.showCategoryMenuList(new ArrayList<>());
//        mDisposable.add(api.getCategory( parentId,  type).subscribe(categoryMenus -> mView.showCategoryMenuList(categoryMenus),
//                throwable -> mView.onError(throwable.getMessage())));
    }

    @Override
    public void getBannerList(String location) {
        mView.showBannerList(new ArrayList<>());
//        mDisposable.add(api.getBannerList(location).subscribe(banners -> mView.showBannerList(banners),
//                throwable -> mView.onError(throwable.getMessage())));
    }

    @Override
    public void getHotGoods(int start) {
//        mDisposable.add(api.getHotGoods(new PGoodsSearchVO(start,"")).subscribe(cards -> mView.showHotGoodsList(cards),
//                throwable -> mView.onError(throwable.getMessage())));
        mDisposable.add(api.getAdvertList(new AdvertVo()).subscribe(cards -> mView.showHotGoodsList(cards),
                throwable -> mView.onError(throwable.getMessage())));
    }

    @Override
    public void getUserInfo(int userId) {
//        mDisposable.add(api.getUserInfo(userId).subscribe(pUser -> {
//                    mView.getUserInfoSuccess(v,pUser);
//                },
//                throwable -> {
//                    mView.onError(throwable.getMessage());
//                }
//        ));
    }


}
