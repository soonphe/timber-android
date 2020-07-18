package com.soonphe.timber.ui.video;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.base.mvp.BasePresenter;

import javax.inject.Inject;


@PerActivity
public class CinemaPresenter extends BasePresenter<CinemaContract.View> implements CinemaContract.Presenter {

    private AppApi api;

    @Inject
    public CinemaPresenter(AppApi api) {
        this.api = api;
    }


}
