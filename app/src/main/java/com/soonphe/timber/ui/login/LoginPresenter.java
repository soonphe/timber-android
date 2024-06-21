package com.soonphe.timber.ui.login;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.di.PerActivity;

import javax.inject.Inject;

/**
 * 登录页
 *
 * @author soonphe
 * @since 1.0
 */
@PerActivity
public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    private AppApi api;

    @Inject
    public LoginPresenter(AppApi accountApi) {
        this.api = accountApi;
    }

    @Override
    public void login(String userName, String password) {
        mDisposable.add(api.login(userName, password).subscribe(user -> mView.loginSuccess(user),
                throwable -> mView.onError(throwable.getMessage())));
    }
}
