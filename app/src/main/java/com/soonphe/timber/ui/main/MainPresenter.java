package com.soonphe.timber.ui.main;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.di.PerActivity;

import javax.inject.Inject;

@PerActivity
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private AppApi api;

    @Inject
    public MainPresenter(AppApi accountApi) {
        this.api = accountApi;
    }

//    @Override
//    public void uploadUser(TUser tUser) {
//        TUserDto tUserDto =new TUserDto(tUser);
//        //有网则上传数据
//        if (SPUtils.getInstance().getBoolean(NETWORK_AVAILABLE)) {
//            mDisposable.add(api.uploadUser("973570", tUserDto)
//                    .subscribe(categoryMenus -> {
//                                //上传成功后更新数据状态码
//                                tUser.setDelflag(true);
//                                tUser.update(tUser.getId());
//                                mView.uploadUserSuccess();
//                            }, throwable -> mView.onError(throwable.getMessage())
//                    )
//            );
//        }
//    }


}
