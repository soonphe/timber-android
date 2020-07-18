package com.soonphe.timber.ui.city.citydetail;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.view.entity.TCityArticle;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class CitydetailPresenter extends BasePresenter<CitydetailContract.View> implements CitydetailContract.Presenter {

    private AppApi api;

    @Inject
    public CitydetailPresenter(AppApi accountApi) {
        this.api = accountApi;
    }

    @Override
    public void getCityList(String cityid, String typeid) {
        //获取城市文章列表
        List<TCityArticle> list = LitePal.where("cityid=? and typeid=?", cityid, typeid).find(TCityArticle.class);
        if (list.size() > 0) {
            mView.getCityListSuccess(list);
        } else {
            mView.getCityListSuccess(new ArrayList<>());
        }
    }
}
