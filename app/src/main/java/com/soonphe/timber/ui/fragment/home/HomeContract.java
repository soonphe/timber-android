package com.soonphe.timber.ui.fragment.home;


import android.view.View;

import com.soonphe.timber.base.mvp.BasePView;
import com.soonphe.timber.entity.PCarousel;
import com.soonphe.timber.entity.PCategory;
import com.soonphe.timber.entity.PGoods;
import com.soonphe.timber.entity.PUser;

import java.util.List;


/**
 * @Author soonphe
 * @Date 2018-03-30 14:48
 * @Description 
 */
public interface HomeContract {
    interface HomeView extends BasePView {

        void showCategoryMenuList(List<PCategory> carouseMenus);
        void showBannerList(List<PCarousel> banners);
        void showHotGoodsList(List<PGoods> cards);
        void getUserInfoSuccess(View v, PUser certify);

    }

    interface HomePresenter {
        void getCategoryMenuList(int parentId, int type);
        void getBannerList(String location);
        void getHotGoods(int start);
        void getUserInfo(View v, int userId);

    }
}
