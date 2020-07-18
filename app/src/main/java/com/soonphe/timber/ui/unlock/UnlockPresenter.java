package com.soonphe.timber.ui.unlock;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * @Author anna
 * @Date 2017-12-06 13:54
 * @Description
 */
@PerActivity
public class UnlockPresenter extends BasePresenter<UnlockContract.UnlockView> implements UnlockContract.UnlockPresenter {

    private AppApi api;

    @Inject
    public UnlockPresenter(AppApi accountApi) {
        this.api = accountApi;
    }

}
