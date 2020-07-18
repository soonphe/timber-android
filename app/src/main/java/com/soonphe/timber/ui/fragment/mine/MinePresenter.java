package com.soonphe.timber.ui.fragment.Mine;

import android.content.Context;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.ui.fragment.mine.MineContract;

import javax.inject.Inject;

/**
 * @Author anna
 * @Date 2017-12-06 13:54
 * @Description
 */
@PerActivity
public class MinePresenter extends BasePresenter<MineContract.MineView> implements MineContract.MinePresenter {

    Context context;
    private AppApi api;

    @Inject
    public MinePresenter(Context context, AppApi accountApi) {
        this.context = context;
        this.api = accountApi;
    }


}
