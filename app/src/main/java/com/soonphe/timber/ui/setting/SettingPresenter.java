package com.soonphe.timber.ui.setting;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.base.mvp.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter{



    private AppApi api;

    @Inject
    public SettingPresenter(AppApi api) {
        this.api = api;
    }

}
