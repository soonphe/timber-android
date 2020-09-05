package com.soonphe.timber.ui.city.citydetail;

import com.soonphe.timber.entity.TCityArticle;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class CitydetailContract {
    interface View extends BasePView {
        void getCityListSuccess(List<TCityArticle> list);
    }

    interface Presenter {
        void getCityList(String cityid, String typeid);
    }
}
