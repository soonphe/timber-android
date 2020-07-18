//package com.soonphe.timber.ui.fragment.center.activity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//
//import com.blankj.utilcode.util.SPUtils;
//import com.blankj.utilcode.util.ToastUtils;
//import com.connxun.ltcx.R;
//import com.connxun.ltcx.constants.Constants;
//import com.connxun.ltcx.ui.mvp.BaseSwipeBackActivity;
//
//import java.util.concurrent.TimeUnit;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import io.reactivex.Observable;
//import skin.support.SkinCompatManager;
//
///**
// * @Author anna
// * @Date 2017-12-07 14:07
// * @Descprition 设置
// */
//
//public class SettingActivity extends BaseSwipeBackActivity {
//    @BindView(R.id.iv_right)
//    ImageView ivRight;
//    @BindView(R.id.rl_data)
//    RelativeLayout rlData;
////    @BindView(R.id.tv_logout)
////    TextView tvLogout;
//    @BindView(R.id.rl_update_pwd)
//RelativeLayout rlUpdatePwd;
//    @BindView(R.id.sw_night)
//    Switch swNight;
//
//
//    @Override
//    public int bindLayout() {
//        return R.layout.center_activity_setting;
//    }
//
//    @Override
//    public void initParms(Bundle parms) {
//
//    }
//
//    @Override
//    public void initView(View view) {
//        swNight.setChecked(SPUtils.getInstance().getBoolean(Constants.NIGHT_THEME));
//    }
//
//    @Override
//    public void doBusiness(Context mContext) {
//        swNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //应用内换肤——SKIN_LOADER_STRATEGY_BUILD_IN后缀加载，SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN前缀加载
//                    SkinCompatManager.getInstance().loadSkin("night", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
//                    SPUtils.getInstance().put(Constants.NIGHT_THEME, true);
//                    reload();
//                } else {
//                    //恢复默认皮肤
//                    SkinCompatManager.getInstance().restoreDefaultTheme();
//                    SPUtils.getInstance().put(Constants.NIGHT_THEME, false);
//                    reload();
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void initInjector() {
//
//    }
//
//    @OnClick({R.id.rl_data, R.id.rl_update_pwd})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.rl_data:
//                mOperation.showProgress("正在清除...", false);
//                Observable.timer(2, TimeUnit.SECONDS).subscribe(aLong -> {
//                    dissmissDialog();
//                    ToastUtils.showShort("清除成功");
//                });
//                break;
//            case R.id.rl_update_pwd:
//                break;
////            case R.id.tv_logout:
////                break;
//        }
//    }
//
//    @OnClick(R.id.sw_night)
//    public void onViewClicked() {
//    }
//}
