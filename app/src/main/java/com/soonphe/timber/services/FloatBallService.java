package com.soonphe.timber.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.soonphe.timber.services.hotspot.ViewManager;


/**
 * 悬浮窗按钮service
 */

public class FloatBallService extends Service {

    public FloatBallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        ViewManager manager = ViewManager.getInstance(this);
        manager.showFloatBall();
        Log.i("悬浮窗Service启动了","悬浮窗Service启动了");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("悬浮窗Service销毁了","悬浮窗Service销毁了");
    }
}