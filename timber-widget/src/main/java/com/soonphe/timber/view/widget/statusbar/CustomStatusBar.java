package com.soonphe.timber.view.widget.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonphe.timber.view.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

/**
 * 自定义状态栏——时间，wifi，电量百分比，电池图标，电池状态
 *
 * @author soonphe
 * @since 1.0
 */
public class CustomStatusBar extends LinearLayout {

    private static final String TAG = "MyCustomStatusBar";
    private final String NETWORK_STATE_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    Context c;
    View statusBarView;
    TextView battery, battery_state, currentTime;
    ImageView batteryIcon;
    ImageView wifi_image;
    ImageView ivNet;
    private WifiInfo wifiInfo;
    private TimeChangeReceiver timeChangeReceiver;

    int[] wifiStateImgs = new int[]{
            R.mipmap.wifi0,
            R.mipmap.wifi1,
            R.mipmap.wifi2,
            R.mipmap.wifi3};

    public CustomStatusBar(Context context) {
        this(context, null);
    }

    public CustomStatusBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomStatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = context;
        initView();
        initWifiService();
        initBatteryService();
        initTimeService();
    }

    /**
     * 页面初始化
     */
    private void initView() {
        statusBarView = LayoutInflater.from(c.getApplicationContext()).inflate(R.layout.view_status_bar, this);
        battery = statusBarView.findViewById(R.id.battery);
        battery_state = statusBarView.findViewById(R.id.battery_state);
        currentTime = statusBarView.findViewById(R.id.currentTime);
        batteryIcon = statusBarView.findViewById(R.id.battery_icon);
        wifi_image = statusBarView.findViewById(R.id.wifi);
        //设置当前时间
        String currentTimeStr = new SimpleDateFormat("HH:mm").format(new Date());
        currentTime.setText(currentTimeStr);
    }

    /**
     * 初始化电池view，注册电池广播
     */
    private void initBatteryService() {
        // 创建意图对象
        IntentFilter iFilter = new IntentFilter();
        // 添加电池改变的活动
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        // 注册广播
        c.registerReceiver(batteryIntentReceiver, iFilter);
    }

    /**
     * 初始化wifi view，注册wifi广播
     */
    private void initWifiService() {
//        WifiManager wifi_service = (WifiManager) c.getSystemService(WIFI_SERVICE);
//        wifiInfo = wifi_service.getConnectionInfo();
        IntentFilter wifiIntentFilter = new IntentFilter();
        //wifi状态变化
        wifiIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        //判断网络状态是否改变：NETWORK_STATE_CHANGE
        wifiIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //wifi信号强度，0到-100的区间值，是一个int型数据，其中0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线。
        wifiIntentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        // 注册广播
        c.registerReceiver(wifiIntentReceiver, wifiIntentFilter);
    }

    /**
     * 初始化timer广播，监听时钟每分钟变化
     */
    private void initTimeService() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        timeChangeReceiver = new TimeChangeReceiver();
        c.registerReceiver(timeChangeReceiver, intentFilter);
    }

    /**
     * 电池广播
     * 用于接收电池变化信息，改变电池电量和健康显示view
     *
     * 也可以使用继承+new对象方式创建：
     * private class BatteryBroadcast extends BroadcastReceiver {
     *         broadcast = new BatteryBroadcast();
     */
    private BroadcastReceiver batteryIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();//获取意图中所有的附加信息
            //获取当前电量，总电量
            int level = extras.getInt(BatteryManager.EXTRA_LEVEL/*当前电量*/, 0);
            int total = extras.getInt(BatteryManager.EXTRA_SCALE/*总电量*/, 100);
            int levelPercent = (int) (((float) level / total) * 100);
            //电池温度
            int temperature = extras.getInt(BatteryManager.EXTRA_TEMPERATURE/*电池温度*/);
            Log.d(TAG, "温度: " + temperature + "");
            Log.d(TAG, "当前电量: " + levelPercent + "%");
            int resId = 0;
            if (levelPercent > 90) {
                resId = R.mipmap.baterry5;
            } else if (levelPercent > 70) {
                resId = R.mipmap.baterry4;
            } else if (levelPercent > 50) {
                resId = R.mipmap.baterry3;
            } else if (levelPercent > 30) {
                resId = R.mipmap.baterry2;
            } else if (levelPercent > 10) {
                resId = R.mipmap.baterry1;
            } else if (levelPercent <= 10) {
                resId = R.mipmap.baterry0;
            }
            batteryIcon.setImageResource(resId);
            battery.setText(levelPercent + "%");
            //电池状态
            int status = extras.getInt(BatteryManager.EXTRA_STATUS);
            battery_state.setVisibility(GONE);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING://充电
                    Log.d(TAG, "充电中..");
                    battery_state.setVisibility(VISIBLE);
                    battery_state.setText("充电中");
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING://放电
                    Log.d(TAG, "放电..");
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING://未充电
                    Log.d(TAG, "未充电");
                    break;
                case BatteryManager.BATTERY_STATUS_FULL://充满
                    break;
                //BatteryManager.BATTERY_STATUS_NOT_CHARGING，未充电，包括放电和充满
                //BATTERY_STATUS_UNKNOWN：未知状态
                default:
                    break;
            }
            //电池健康程度
            int health = extras.getInt(BatteryManager.EXTRA_HEALTH);
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_GOOD://健康状态
                    Log.d(TAG, "健康状态好");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT://过热
                    Log.d(TAG, "过热");
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD://过冷
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE://电压过高
                    break;
                default://其他三个放在default中。dead、unknown、unspecial failure
                    break;
            }
        }
    };


    /**
     * wifi广播
     * 用于接收wifi变化信息
     */
    private BroadcastReceiver wifiIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                //判断wifi是否连接
                if (isWifiConnected()) {
                    initWifiState();
                } else {
                    wifi_image.setBackgroundResource(R.mipmap.wifi0);
                }
            } else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int wifistate = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_DISABLED);

                if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                    wifi_image.setBackgroundResource(R.mipmap.wifi0);
                } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                    updateWifiStrength();
                }
            } else if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
                initWifiState();
            }
        }
    };

    /**
     * 判断wifi是否连接
     *
     * @return
     */
    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public boolean isWifiConnected() {
        ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        } else {
            NetworkInfo ni = manager.getActiveNetworkInfo();
//            NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return ni != null && ni.getType() == 1;
        }
    }

    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String currentTimeStr = new SimpleDateFormat("HH:mm").format(new Date());
            currentTime.setText(currentTimeStr);
        }
    }

    /**
     * 初始化wifi
     *
     * blankj判断网络：
     * ConnectivityManager cm = (ConnectivityManager)Utils.getApp().getSystemService("connectivity");
     * return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getType() == 1;
     */
    public void initWifiState() {
        ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d(TAG, "info.isConnected(): " + info.isConnected());
        if (info.isConnected()) {
            updateWifiStrength();
        } else {
            wifi_image.setBackgroundResource(R.mipmap.wifi0);
        }
    }

    /**
     * 更新wifi图标
     */
    public void updateWifiStrength() {
        int strength = getStrength(c);
        if (strength >= 0 && strength <= 3)
            wifi_image.setBackgroundResource(wifiStateImgs[strength]);
        Log.d(TAG, "wifi strength: " + strength);
    }


    /**
     * 获取wifi强度
     * @param context
     * @return
     */
    public int getStrength(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        if (info.getBSSID() != null) {
            int strength = WifiManager.calculateSignalLevel(info.getRssi(), 4);
            // 链接速度
            // int speed = info.getLinkSpeed();
            // // 链接速度单位
            // String units = WifiInfo.LINK_SPEED_UNITS;
            // // Wifi源名称
            // String ssid = info.getSSID();
            return strength;

        }
        return 0;
    }

    /**
     * 释放广播
     */
    public void unregisterBroadcast() {
        c.unregisterReceiver(batteryIntentReceiver);
        c.unregisterReceiver(wifiIntentReceiver);
        c.unregisterReceiver(timeChangeReceiver);
    }
}
