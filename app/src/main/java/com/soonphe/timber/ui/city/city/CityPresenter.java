package com.soonphe.timber.ui.city.city;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.view.entity.TCity;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@PerActivity
public class CityPresenter extends BasePresenter<CityContract.View> implements CityContract.Presenter{


    private AppApi api;

    @Inject
    public CityPresenter(AppApi api) {
        this.api = api;
    }

    @Override
    public void getCityList() {
        //获取城市列表
        List<TCity> list = LitePal.findAll(TCity.class);
        if (list.size() > 0) {
            mView.getCityListSuccess(list);
        } else {
            mView.getCityListSuccess(new ArrayList<>());
        }
    }
}
