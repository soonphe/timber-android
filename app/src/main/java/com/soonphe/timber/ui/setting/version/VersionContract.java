package com.soonphe.timber.ui.setting.version;

import com.soonphe.timber.entity.TVersion;
import com.soonphe.timber.base.mvp.BasePView;


public class VersionContract {
    interface View extends BasePView {
        void getVersionSuccess(TVersion tVersion);

        void downloadApkSuccess(String filepath);
    }

    interface Presenter {
        //获取当前版本信息
        void getVersion();

        //下载APK
        void downloadApk(TVersion tVersion);
    }
}
