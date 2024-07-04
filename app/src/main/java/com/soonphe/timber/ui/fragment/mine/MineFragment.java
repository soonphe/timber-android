package com.soonphe.timber.ui.fragment.mine;

import static com.soonphe.timber.constants.Constants.USER_INFO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseFragmentV4;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.PUser;
import com.soonphe.timber.services.hotspot.ServiceUtil;
import com.soonphe.timber.ui.about.AboutActivity;
import com.soonphe.timber.ui.login.LoginActivity;
import com.soonphe.timber.ui.setting.SettingActivity;
import com.soonphe.timber.utils.ShowPushMessageUtils;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 *
 * @author soonphe
 * @since 1.0
 */
public class MineFragment extends BaseFragmentV4 {

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.app_bar_title)
    TextView appBarTile;
    @BindView(R.id.ci_user_pic)
    CircleImageView ciUserPic;
    @BindView(R.id.tv_nikeName)
    TextView tvNikeName;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_reg)
    TextView tvReg;
    @BindView(R.id.li_nologin)
    LinearLayout liNologin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_invite_friends)
    RelativeLayout rlInviteFriends;
    @BindView(R.id.rl_send_card)
    RelativeLayout rlSendCard;
    @BindView(R.id.rl_certification)
    RelativeLayout rlCertification;
    @BindView(R.id.rl_store_order)
    RelativeLayout rlStoreOrder;
    @BindView(R.id.rl_my_collect)
    RelativeLayout rlMyCollect;
    @BindView(R.id.rl_my_order)
    RelativeLayout rlMyOrder;
    @BindView(R.id.rl_my_trace)
    RelativeLayout rlMyTrace;
    @BindView(R.id.rl_my_carder)
    RelativeLayout rlMyCarder;
    @BindView(R.id.rl_my_want)
    RelativeLayout rlMyWant;
    @BindView(R.id.rl_help_feedback)
    RelativeLayout rlHelpFeedback;
    @BindView(R.id.rl_my_setting)
    RelativeLayout rlMySetting;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R.id.logout)
    LinearLayout logout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    private static MineFragment instance = new MineFragment();
    public static MineFragment getInstance() {
        return instance;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        //动态波浪线（测试失败）
//        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
//        lp.gravity = Gravity.CENTER;
//        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
//            @Override
//            public void OnWaveAnimation(float y) {
//                lp.setMargins(0, 0, 0, (int) y + 2);
////                ciUserPic.setLayoutParams(lp);
//            }
//        });

        //设置CollapsingToolbarLayout显示样式
//        toolbarLayout.setTitle("");
//        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this.getContext(), R.color.white));
//        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this.getContext(), R.color.white));

        //appBarLayout监听滑动，设置title的显示与隐藏
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            //初始verticalOffset为0，不能参与计算。
            if (verticalOffset == 0) {
                appBarTile.setAlpha(0.0f);
            } else {
                //保留一位小数
                float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
                appBarTile.setAlpha(alpha);
            }
        });
        setHeaderImageAndName();
    }

    @Override
    public void onResume() {
        super.onResume();
        setHeaderImageAndName();
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void lazyInitBusiness(Context mContext) {

    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    /**
     * 登录/注册/帮助/设置/关于
     *
     * @param view
     */
    @OnClick({R.id.tv_login, R.id.tv_reg, R.id.rl_help_feedback, R.id.rl_my_setting, R.id.rl_about_us})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                mOperation.forward(LoginActivity.class);
                break;
            case R.id.tv_reg:
//                mOperation.forward(RegisterActivity.class);
                break;
            case R.id.rl_help_feedback:
                ShowPushMessageUtils.showPushDialog(this.mContext,"测试消息MMMMMMMMMMMMM", null);
//                mOperation.forward(HelpAndFeedbackActivity.class);
                break;
            case R.id.rl_my_setting:
                //启动Service（如果是Android 8.0或更高版本，确保你的应用有必要的权限来在后台运行服务，可能需要使用前台服务或者通过系统的JobScheduler、WorkManager来替代后台服务。）
//                ServiceUtil.startService(getContext());
                //进入设置页面
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                mOperation.forward(SettingActivity.class);
                break;
            case R.id.rl_about_us:
                mOperation.forward(AboutActivity.class);
                break;
        }
    }

    @OnClick({R.id.ci_user_pic, R.id.rl_send_card, R.id.rl_certification, R.id.rl_store_order, R.id.rl_my_collect, R.id.rl_my_order, R.id.rl_my_trace, R.id.rl_my_carder, R.id.rl_my_want, R.id.logout})
    public void onViewClicked(View view) {
        //验证是否登录
//        PUser pUser = (PUser) CacheUtils.getInstance().getSerializable(USER);
//        if (pUser == null) {
//            mOperation.forward(LoginActivity.class);
//            return;
//        }
        switch (view.getId()) {
            case R.id.ci_user_pic:
                break;
            case R.id.rl_send_card:
                break;
            case R.id.rl_certification:
//                mOperation.forward(CertifyActivity.class);
                break;
            case R.id.rl_store_order:
//                mOperation.forward(StoreOrderActivity.class);
                break;
            case R.id.rl_my_collect:
//                mOperation.forward(MyCollectActivity.class);
                break;
            case R.id.rl_my_order:
//                mOperation.forward(MyOrderActivity.class);
                break;
            case R.id.rl_my_trace:
//                mOperation.forward(MyClickActivity.class);
                break;
            case R.id.rl_my_carder:
//                startActivity(new Intent(getActivity(), MyCardActivity.class));
                break;
            case R.id.rl_my_want:
//                mOperation.forward(MyNeedActivity.class);
                break;
            case R.id.logout:
//                mOperation.showBasicDialog("退出登录?", (dialog, which) -> {
//                    mOperation.forwardAndFinish(LoginActivity.class);
//                });
                break;
        }
    }

    /**
     * 设置用户头像和昵称
     */
    private void setHeaderImageAndName(){
        PUser pUser = (PUser) CacheMemoryUtils.getInstance().get(USER_INFO);
        if (pUser != null) {
            Log.d("MineFragment", JSON.toJSONString(pUser));
            if (pUser.getType() == 3) {
                Glide.with(getActivity())
                        .load(Constants.BASE_IMAGE_URL + pUser.getHeadPic())
                        .into(ciUserPic);
            } else {
                Glide.with(getActivity())
                        .load(pUser.getHeadPic())
                        .into(ciUserPic);
            }
            tvNikeName.setText(pUser.getName());
        } else {
            tvNikeName.setVisibility(View.GONE);
            liNologin.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        }
    }

}
