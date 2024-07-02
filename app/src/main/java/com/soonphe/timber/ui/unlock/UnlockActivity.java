package com.soonphe.timber.ui.unlock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.ui.advert.AdvertContract;
import com.soonphe.timber.ui.advert.AdvertPresenter;
import com.soonphe.timber.ui.splash.SplashActivity;
import com.soonphe.timber.utils.GlideUtils;
import com.soonphe.timber.view.widget.unlock.CustomerUnlockView;

import org.litepal.LitePal;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class UnlockActivity extends BaseActivity implements AdvertContract.View, UnlockContract.UnlockView {

    @Inject
    AdvertPresenter advertPresenter;

    @Inject
    UnlockPresenter presenter;

    @BindView(R.id.unlock_bg_advert)
    ImageView unlockBgAdvert;
    @BindView(R.id.unlock_view)
    CustomerUnlockView unlockView;


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
    public int bindLayout() {
        return R.layout.activity_unlock;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        BarUtils.setStatusBarColor(this, 0);
        advertPresenter.attachView(this);
        presenter.attachView(this);
        Glide.with(this).load(R.mipmap.bg).into(unlockBgAdvert);

        //这里调用初始化litepal
//        SQLiteDatabase db = LitePal.getDatabase();
        //自定义view回调监听
        unlockView.setOnLockListener(isLocked -> {
            if (isLocked) {
                mOperation.forwardAndFinish(SplashActivity.class);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
//        advertPresenter.getAdvertListByType(1);
    }

    @Override
    public void getAdvertListSuccess(List<TAdvert> list) {
        if (list.size() > 0) {
            //这里只选取最新的1张
            GlideUtils.loadImageViewLoding(this,
                    list.get(0).getDownloadPic(), unlockBgAdvert, R.mipmap.bg, R.mipmap.bg);
        }
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }



}
