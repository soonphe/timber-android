package com.soonphe.timber.ui.splash;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.soonphe.timber.utils.MyLog;
import com.tbruyelle.rxpermissions2.RxPermissions;

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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * flash闪屏页
 *
 * @author soonphe
 * @since 1.0
 */
public class SplashActivity extends BaseActivity implements AdvertContract.View, DataContract.View {

    /**
     * 权限列表：定位，存储，网络，设备信息，相机
     */
    protected static final String[] NEEDS_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA};
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;

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
        MyLog.d("测试Kotlin打印日志key","测试打印日志Value");
        MyLog.defLogFunction("测试Kotlin自定义system打印日志key","测试自定义system打印日志Value");
        //权限检查
        checkPermissions();
        //获取flash广告
        advertPresenter.getAdvertListByType(12);
    }

    /**
     * 权限检查+授权
     */
    private void checkPermissions() {
        if (!checkPermissions(NEEDS_PERMISSIONS)) {
            //申请权限
            ActivityCompat.requestPermissions(this, NEEDS_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        Log.d(TAG,"===权限检查+授权已完成===");
    }

    /**
     * 权限检查
     *
     * @param neededPermissions 需要的权限
     * @return 是否全部被允许
     */
    protected boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            Log.d(TAG, "===权限检查是否全部被允许==="+true);
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        Log.d(TAG,"===权限检查是否全部被允许==="+allGranted+"");
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAllGranted = true;
        for (int grantResult : grantResults) {
            isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
        }
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            if (isAllGranted) {
                Log.d(TAG, "权限检查全部被允许");
            } else {
                //手动去打开app权限提示
                Log.d(TAG, "权限检查存在未授予权限，需要去跳转设置");
                openAppDetails();
            }
        }
    }


    /**
     * 打开 APP 的详情设置
     */
    protected void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("拍照“设备信息” 和 “外部存储器权限”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettingActivity();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    /**
     * 打开设置页面
     */
    protected void openSettingActivity() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 100);
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
