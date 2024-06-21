package com.soonphe.timber.ui.fragment.center;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.di.PerActivity;

import javax.inject.Inject;

/**
 * 商城
 *
 * @author soonphe
 * @since 1.0
 */
@PerActivity
public class CenterPresenter extends BasePresenter<CenterContract.CenterView> implements CenterContract.CenterPresenter {

    private AppApi api;

    @Inject
    public CenterPresenter(AppApi api) {
        this.api = api;
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
