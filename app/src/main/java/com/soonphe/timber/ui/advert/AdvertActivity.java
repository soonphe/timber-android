package com.soonphe.timber.ui.advert;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.base.BaseActivity;

import java.util.List;


/**
 * @Author soonphe
 * @Date 2018-08-30 10:41
 * @Description 广告activity——这里没有使用
 */
public class AdvertActivity extends BaseActivity implements AdvertContract.View {

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void getAdvertListSuccess(List<TAdvert> list) {

    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void initInjector() {

    }
}
