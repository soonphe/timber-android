package com.soonphe.timber.constants;

import android.os.Environment;

import com.blankj.utilcode.util.SDCardUtils;
import com.soonphe.timber.base.IBaseConstant;

/**
 * 常量池
 *
 * @author soonphe
 * @since 1.0
 */
public class Constants implements IBaseConstant {

    public static final String LOCAL_SERVER_IP = "10.0.2.2:80";
    public static final String SERVER_IP = "10.0.2.2";
    public static final String SERVER_PORT = "8081";
    public static final String HOST = "http://" + SERVER_IP + ":" + SERVER_PORT;//服务器地址
    public static final String BASE_URL = HOST + "/";  // 项目名
    public static final String BASE_API_URL = BASE_URL + "api/";    //API请求地址
    public static final String BASE_IMAGE_URL = "http://" + LOCAL_SERVER_IP + "/upload";    //文件下载地址

    //基础请求分页
    public static int PAGE_SIZE = 20;
    //验证码等待时间 1分钟
    public static int CAPTCHA_WAIT_TIME = 60;
    //黑屏注册页面失效时间倒计时，5分钟
    public static final int REGISTER_SCREEN_LOCK = 5 * 60 * 1000;

    //网络状态
    public static final String NETWORK_AVAILABLE = "NETWORK_AVAILABLE"; //网络是否可用
    public static final String IS_MOBILE = "IS_MOBILE"; //是否为4G设备
    public static final String WIFI_STATE_CONNECT = "已连接";
    public static final String WIFI_STATE_ON_CONNECTING = "正在连接";
    public static final String WIFI_STATE_UNCONNECT = "未连接";

    //用户信息
    public static final String USER_INFO = "USER_INFO";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_TELEPHONE = "USER_TELEPHONE";
    public static final String USER_BADGENUMBER = "USER_BADGENUMBER"; //消息模块 badge数量
    public static final String LAST_WEATHER = "LAST_WEATHER"; //天气
    public static final String REGIST_CLICK = "REGIST_CLICK";   //注册用户点击二维码次数
    public static final String REGIST_PHONE = "REGIST_PHONE";   //注册用户手机号

    //版本
    public static final String APK_VERSION = "APK_VERSION";   //APK版本
    public static final String ADVERT_VERSION = "ADVERT_VERSION";    //广告版本
    public static final String DATA_VERSION = "DATA_VERSION";   //数据版本
    public static final String DOWNLOAD_COUNT = "DOWNLOAD_COUNT";   //电影下载部数
    public static final String CURRENT_DOWNLOAD_COUNT = "CURRENT_DOWNLOAD_COUNT";   //当前下载次数
    public static final String GAME_UNZIP = "GAME_UNZIP";   //游戏解压路径

    //管理员密码
    public static final String ADMIN_PASSWORD = "218069";
    //按钮切换
    public static final String SWITCH_BTN_STATE = "switch_btn_state";
    //黑屏页面倒计时时间间隔，1分钟（接受回调）
    public static final int REGIST_SCREEN_COUNT = 60 * 1000;
    //锁屏时间key
    public static final String LOCK_SCREEN_TIME = "LOCK_SCREEN_TIME";
    //高德定位
    public static final String AMAP_LOCATION = "AMAP_LOCATION";
    //bugly
    public static String BUGLY_APPID = "4449ffc54b";
    //主题
    public static final String NIGHT_THEME = "THEME";

    /**
     * 项目根SD卡目录
     **/
    public static final String PROJECT_ROOT = "timber";
//    public static final String SDPATHLIST = SDCardUtils.getMountedSDCardPath().toString();
//    public static final String SDPATH = SDPATHLIST.substring(1, SDPATHLIST.length() - 1);
    public static final String DOWNLOAD_PATH = "/storage/sdcard0/Download";
    public static final String DOWNLOAD_ExternalStorage_PATH = Environment.getExternalStorageDirectory().getPath() + "/Download" + "/"+ PROJECT_ROOT;
    public static final String DOWNLOAD_IMAGE_COMPRESS_PATH = DOWNLOAD_ExternalStorage_PATH + "/compressImage/";  //图片压缩地址


}
