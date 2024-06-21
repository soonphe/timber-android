package com.soonphe.timber.ui.splash;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.ui.advert.AdvertContract;
import com.soonphe.timber.ui.advert.AdvertPresenter;
import com.soonphe.timber.ui.data.DataContract;
import com.soonphe.timber.ui.data.DataPresenter;
import com.soonphe.timber.ui.main.MainActivity;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.entity.TStats;

import org.litepal.LitePal;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.soonphe.timber.constants.Constants.BASE_IMAGE_URL;
import static com.soonphe.timber.constants.Constants.IS_MOBILE;
import static com.soonphe.timber.constants.Constants.NETWORK_AVAILABLE;

/**
 * flash闪屏页
 *
 * @author soonphe
 * @since 1.0
 */
public class SplashActivity extends BaseActivity implements AdvertContract.View, DataContract.View {

    @Inject
    AdvertPresenter advertPresenter;
    @Inject
    DataPresenter dataPresenter;

    @BindView(R.id.iv_flash)
    ImageView ivFlash;
    @BindView(R.id.splash_countdown)
    TextView splashCountdown;

    Timer timer = new Timer();
    /**
     * flash最大停留时间
     */
    int currentSecond = 4;


    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initParams(Bundle parms) {
    }

    @Override
    public void initView(View view) {

        BarUtils.setStatusBarColor(this, 0);
        advertPresenter.attachView(this);
        dataPresenter.attachView(this);
        //背景栏默认图片
        Glide.with(this).load(R.mipmap.flash_bg).into(ivFlash);
        //判断网络是否可用
        Log.d("SplashActivity","网络是否可用："+NetworkUtils.isAvailableByPing());
        if (NetworkUtils.isAvailableByPing()) {
            SPUtils.getInstance().put(NETWORK_AVAILABLE, true);
            //判断是否为wifi网络
            Log.d("SplashActivity","网络运营商名称："+NetworkUtils.getNetworkOperatorName());
            if (NetworkUtils.getNetworkOperatorName() != null) {
                SPUtils.getInstance().put(IS_MOBILE, true);
            }
        } else {
            SPUtils.getInstance().put(NETWORK_AVAILABLE, true);
            SPUtils.getInstance().put(IS_MOBILE, false);
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        //获取flash广告
        advertPresenter.getAdvertListByType(12);
    }

    @Override
    public void getAdvertListSuccess(List<TAdvert> list) {
        if (list.size() > 0) {
            //这里只选取最新的1张
            Glide.with(this)
                    .load(BASE_IMAGE_URL + list.get(0).getPicUrl())
                    .error(R.mipmap.flash_bg)
                    .into(ivFlash);
        }

        //判断这里是否存在用户，如果存在则要记录本次解锁数据
        String phone = SPUtils.getInstance().getString(Constants.REGIST_PHONE, "");
        if (!StringUtils.isEmpty(phone)) {
            //判断该手机号是否创建过统计数据——有数据则解锁+1
            TStats tOpen = LitePal.where("phone = ?", phone + "").order("id desc").findFirst(TStats.class);
            if (tOpen != null) {
                tOpen.setOpenlock(tOpen.getOpenlock() + 1);
                boolean result = tOpen.save();
                if (result) {
                    //上传所有数据
                    dataPresenter.uploadData(tOpen);
                }
            }
        }

        //处理倒计时逻辑，倒计时4s之后自动跳到主界面
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentSecond--;
                runOnUiThread(() -> splashCountdown.setText(currentSecond + "秒跳过"));
                if (currentSecond == 0) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    mOperation.forwardAndFinish(MainActivity.class);
                }
            }
        }, 1, 1000);

    }

    @OnClick({R.id.splash_countdown})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.splash_countdown:
                if (timer != null) {
                    timer.cancel();
                }
                mOperation.forwardAndFinish(MainActivity.class);
                break;
        }
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public void onError(String error) {
        LogUtils.e(error);
    }

    @Override
    public void uploadDataSuccess() {
        LogUtils.e("数据上传成功");
    }


}
