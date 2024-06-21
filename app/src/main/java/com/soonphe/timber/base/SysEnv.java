package com.soonphe.timber.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


/**
 * 系统运行环境信息
 *
 * @author soonphe
 * @since 1.0
 */
public final class SysEnv {

	/***Log输出标识**/
	private static final String TAG = SysEnv.class.getSimpleName();
	
	/***屏幕显示材质**/
	private static final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
	
	/**上下文**/
	private static final Context context = BaseApplication.gainContext();
	
	/**操作系统名称(GT-I9100G)***/
	public static final String MODEL_NUMBER = Build.MODEL;
	
	/**操作系统名称(I9100G)***/
	public static final String DISPLAY_NAME = Build.DISPLAY;
	
	/**操作系统版本(4.4)***/
	public static final String OS_VERSION = Build.VERSION.RELEASE;;
	
	/**应用程序版本***/
	public static final String APP_VERSION = getVersionName();
	
	/***屏幕宽度**/
	public static final int SCREEN_WIDTH = getDisplayMetrics().widthPixels;
	
	/***屏幕高度**/
	public static final int SCREEN_HEIGHT = getDisplayMetrics().heightPixels;
	
	/***本机手机号码**/
//	public static final String PHONE_NUMBER = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
	
	/***设备ID**/
//	public static final String DEVICE_ID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	
	/***设备IMEI号码**/
//	public static final String IMEI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
	
	/***设备IMSI号码**/
//	public static final String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	
	/**获取系统显示材质***/
	public static DisplayMetrics getDisplayMetrics(){
		  WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		  windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
		  return mDisplayMetrics;
	}
	
	/**获取摄像头支持的分辨率***/
	public static List<Camera.Size> getSupportedPreviewSizes(Camera camera){
        Camera.Parameters parameters = camera.getParameters(); 
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        return sizeList;
	}
	
	/**
	 * 获取应用程序版本（versionName）
	 * @return 当前应用的版本号
	 */
	public static String getVersionName() {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "获取应用程序版本失败，原因："+e.getMessage());
			return "";
		}
		return info.versionName;
	}
	
	/**
	 * 获取应用程序版本（versionName）
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode() {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "获取应用程序版本失败，原因："+e.getMessage());
			return -1;
		}
		return info.versionCode;
	}
	
	/**
	 * 获取系统内核版本
	 * @return
	 */
	public static String getKernelVersion(){
		String strVersion= "";
		FileReader mFileReader = null;
		BufferedReader mBufferedReader = null;
		try {
			mFileReader = new FileReader("/proc/version");
			mBufferedReader = new BufferedReader(mFileReader, 8192);
			String str2 = mBufferedReader.readLine();
			strVersion = str2.split("\\s+")[2];//KernelVersion
			
		} catch (Exception e) {
			Log.e(TAG, "获取系统内核版本失败，原因："+e.getMessage());
		}finally{
			try {
				mBufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strVersion;
	}
	
	/***
	 * 获取MAC地址
	 * @return
	 */
	public static String getMacAddress(){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo.getMacAddress()!=null){
        	return wifiInfo.getMacAddress();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取运行时间
	 * @return 运行时间(单位/s)
	 */
	public static long getRunTimes() {
		long ut = SystemClock.elapsedRealtime() / 1000;
		if (ut == 0) {
			ut = 1;
		}
		return ut;
	}

	/**
	 * 判断是否为模拟器环境（需要权限）
	 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * 目前已不支持使用telephonyManager.getDeviceId()，替代方案telephonyManager.getImei()
	 * @param mContext 上下文
	 * @return
	 */
	@Deprecated
	public static boolean isEmulator(Context mContext) {
		TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//		String deviceID = telephonyManager.getDeviceId();
		// 如果 运行的 是一个 模拟器
//		if (deviceID == null || deviceID.trim().length() == 0
//				|| deviceID.matches("0+")) {
//			return true;
//		}
		return false;
	}
	
	/**
	 * 获取可用内存
	 * @param mContext 上下文
	 * @return
	 */
    public static long gainUnusedMemory(Context mContext) {
        long MEM_UNUSED = 0L;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }

    /**
     * 获取总内存
     * @return
     */
    public static long gainTotalMemory() {
        long mTotal = 0;
        // /proc/meminfo读出的内核信息进行解析
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');
            // 截取字符串信息

            content = content.substring(begin + 1, end).trim();
            mTotal = Integer.parseInt(content);
        } catch (Exception e) {
        	Log.e(TAG, "获取总内存失败，原因："+e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return mTotal;
    }

	/***
	 * 查看网络可用状态
	 * @return
	 */
	public static boolean getNetworkIsAvailable(){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		}else {
			return false;
		}
	}

	public static String getWifiMacAddress() {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if(wifiInfo!=null && wifiInfo.getMacAddress()!=null){
			return wifiInfo.getMacAddress();
		} else {
			return "";
		}
	}

	/**
	 * 传入网络类型判断是否可用
	 * ConnectivityManager.TYPE_MOBILE  	0
	 * ConnectivityManager.TYPE_WIFI		1
	 * ConnectivityManager.TYPE_ETHERNET	9
	 *
	 * @param networkType
	 * @return
	 */
	public static boolean getNetworkType(int networkType) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getNetworkInfo(networkType);
		if (info == null) {
			return false;
		} else {
			NetworkInfo.State state = info.getState();
			if (null == state) {
				return false;
			} else {
				//如果需要判断2G/3G/4G/5G
				switch (info.getSubtype()) {
					case 16:
						System.out.println("NetworkUtils.NetworkType.NETWORK_2G");
					case 17:
						System.out.println("NetworkUtils.NetworkType.NETWORK_3G");
					case 18:
						System.out.println("NetworkUtils.NetworkType.NETWORK_4G");
					case 20:
						System.out.println("NetworkUtils.NetworkType.NETWORK_5G");
					default:
						String subtypeName = info.getSubtypeName();
						if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
							System.out.println("NetworkUtils.NetworkType.NETWORK_UNKNOWN");
						}
						System.out.println("NetworkUtils.NetworkType.NETWORK_3G");
				}
				return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
			}
		}
	}

	/**
	 * 查看网络运营商名称
	 * @return
	 */
	public static String getNetworkOperatorName() {
		if (getNetworkIsAvailable()){
			TelephonyManager tm = (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
			return tm == null ? "" : tm.getNetworkOperatorName();
		}
		return "";
	}
}
