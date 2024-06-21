package com.soonphe.timber.services.hotspot;

import android.content.Context;
import android.content.Intent;

import com.soonphe.timber.services.FloatBallService;


/**
 * 悬浮窗service启动，销毁工具类
 *
 * @author soonphe
 * @since 1.0
 */
public class ServiceUtil {

    private static Intent intent;
    private static Context mContext;

    /**
     * 启动service
     * @param context
     */
    public static void startService(Context context){
        mContext=context;
        intent = new Intent(context, FloatBallService.class);
        context.startService(intent);
    }

    /**
     * 停止service
     */
    public static  void stopService(){
        mContext.stopService(intent);
    }
}