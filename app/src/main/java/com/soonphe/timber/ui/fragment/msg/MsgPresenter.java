package com.soonphe.timber.ui.fragment.msg;

import android.view.View;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.pojo.vo.AdvertVo;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * 消息
 *
 * @author soonphe
 * @since 1.0
 */
@PerActivity
public class MsgPresenter extends BasePresenter<MsgContract.MsgView> implements MsgContract.MsgPresenter {

    private AppApi api;

    @Inject
    public MsgPresenter(AppApi api) {
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
