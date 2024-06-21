package com.soonphe.timber.di.component;

import android.content.Context;
import android.view.LayoutInflater;

import com.soonphe.timber.MyApplication;
import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.components.okhttp.OkHttpHelper;
import com.soonphe.timber.di.module.ApiModule;
import com.soonphe.timber.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @Author soonphe
 * @Date 2017-11-21 10:53
 * @Description ApplicationComponent
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    Context getContext();

    LayoutInflater getLayoutInflater();

    AppApi getAccountApi();

    /**
     * okhttp注入对象
     * @return
     */
    OkHttpHelper getOkHttpHelper();

    void inject(MyApplication mApplication);

    void inject(BaseActivity mBaseActivity);
}
