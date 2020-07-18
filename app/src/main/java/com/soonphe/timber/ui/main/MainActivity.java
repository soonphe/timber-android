package com.soonphe.timber.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.view.entity.TStats;
import com.soonphe.timber.view.entity.TUser;
import com.soonphe.timber.view.entity.TabEntity;
import com.soonphe.timber.view.adapter.ViewPagerAdapter;
import com.soonphe.timber.ui.fragment.mine.MineFragment;
import com.soonphe.timber.ui.fragment.home.HomeFragment;
import com.soonphe.timber.ui.jpush.ExampleUtil;
import com.soonphe.timber.view.utils.DeviceUtils;
import com.soonphe.timber.view.utils.ShowPushMessageUtils;
import com.soonphe.timber.view.widget.MyViewPager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

import static com.soonphe.timber.constants.Constants.IS_MOBILE;
import static com.soonphe.timber.constants.Constants.NETWORK_AVAILABLE;

/**
 * @Author soonphe
 * @Date 2018-08-30 10:37
 * @Description 首页
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainPresenter presenter;
    @BindView(R.id.viewpager)
    MyViewPager viewpager;
    @BindView(R.id.tl_2)
    CommonTabLayout mTabLayout_2;

    private String[] mTitles = {"首页", "促销", "消息", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_card_normal,
            R.mipmap.ic_msg_normal, R.mipmap.ic_center_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_card_selected,
            R.mipmap.ic_msg_selected, R.mipmap.ic_center_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ArrayList<Fragment> fragments = new ArrayList<>();
    public static boolean isForeground = false; //receiver接收消息时使用
    MaterialDialog materialDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    public void initView(View view) {

        BarUtils.setStatusBarAlpha(this, 0);
        setTouchDissIm(true);
        presenter.attachView(this);

        fragments.add(HomeFragment.getInstance());
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        fragments.add(MineFragment.getInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.setOffscreenPageLimit(3); //设置ViewPager的缓存界面数，每一侧的界面数(默认是缓存相邻的)
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    mTabLayout_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(0);
    }

    @Override
    public void doBusiness(Context mContext) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        super.onResume();
    }

    /**
     * 展示注册对话框
     */
    @SuppressLint("ResourceAsColor")
    public void onDialog() {
        int[] gender = {0};//1男2女
        int[] age = {0};//0：20以下，1:20-40，2:40-60,3:60以上

        materialDialog = new MaterialDialog.Builder(this)
//                .content(content)
//                .positiveText(R.string.ok)
//                .negativeText(R.string.cancel)
                .autoDismiss(false)
                .customView(R.layout.dialog_register, true)
                .backgroundColor(R.color.transparent)
                .show();
        //不允许点击外侧关闭
        materialDialog.setCanceledOnTouchOutside(false);
        //        设置关闭监听
//        materialDialog.setOnCancelListener(dialog -> {
//            KeyboardUtils.hideSoftInput(this);
//        });

        CheckBox chMan = materialDialog.getCustomView().findViewById(R.id.ck_man);
        CheckBox chWoman = materialDialog.getCustomView().findViewById(R.id.ck_woman);
        CheckBox below20 = materialDialog.getCustomView().findViewById(R.id.ck_below20);
        CheckBox twentyTofourth = materialDialog.getCustomView().findViewById(R.id.ck_twentyTofourth);
        CheckBox fourthToSixty = materialDialog.getCustomView().findViewById(R.id.ck_fourthToSixty);
        CheckBox aboveSixty = materialDialog.getCustomView().findViewById(R.id.ck_aboveSixty);
        TextView etPhone = materialDialog.getCustomView().findViewById(R.id.et_phone);
        TextView etCode = materialDialog.getCustomView().findViewById(R.id.et_Code);
        Button button = materialDialog.getCustomView().findViewById(R.id.bt_Save);
        ImageView imageView = materialDialog.getCustomView().findViewById(R.id.QRCode);
        imageView.setOnClickListener(v -> {
            int click = SPUtils.getInstance().getInt(Constants.REGIST_CLICK, 0);
            SPUtils.getInstance().put(Constants.REGIST_CLICK, click + 1);
            //如果点击为5的倍数，则为管理员操作
            if ((click + 1) % 5 == 0) {
                KeyboardUtils.hideSoftInput(v);
                materialDialog.cancel();
                SPUtils.getInstance().put(Constants.REGIST_PHONE, "111111");
            }
        });
        chMan.setOnClickListener(v -> {
            chMan.setChecked(true);
            chWoman.setChecked(false);
            gender[0] = 1;
        });
        chWoman.setOnClickListener(v -> {
            chWoman.setChecked(true);
            chMan.setChecked(false);
            gender[0] = 2;
        });
        below20.setOnClickListener(v -> {
            below20.setChecked(true);
            twentyTofourth.setChecked(false);
            fourthToSixty.setChecked(false);
            aboveSixty.setChecked(false);
            age[0] = 0;
        });
        twentyTofourth.setOnClickListener(v -> {
            below20.setChecked(false);
            twentyTofourth.setChecked(true);
            fourthToSixty.setChecked(false);
            aboveSixty.setChecked(false);
            age[0] = 1;
        });
        fourthToSixty.setOnClickListener(v -> {
            below20.setChecked(false);
            twentyTofourth.setChecked(false);
            fourthToSixty.setChecked(true);
            aboveSixty.setChecked(false);
            age[0] = 2;
        });
        aboveSixty.setOnClickListener(v -> {
            below20.setChecked(false);
            twentyTofourth.setChecked(false);
            fourthToSixty.setChecked(false);
            aboveSixty.setChecked(true);
            age[0] = 3;
        });

        button.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            String phone = etPhone.getText().toString();
            String code = etCode.getText().toString();
            //管理员账户
            if ("111111".equals(phone)) {
                materialDialog.cancel();
                SPUtils.getInstance().put(Constants.REGIST_PHONE, phone + "");
                return;
            } else if (phone.length() != 11 || code.length() != 6) {
                ToastUtils.showShort("输入格式不正确，请重新输入");
                return;
            }
            //微信验证码
            if ("973570".equals(code)
                    || "217560".equals(code)
                    || "285973".equals(code)
                    || "579134".equals(code)
                    || "497186".equals(code)) {
                SPUtils.getInstance().put(Constants.REGIST_PHONE, phone);

                String date = TimeUtils.millis2String(System.currentTimeMillis(),
                        new SimpleDateFormat("yyyy-MM-dd"));
                String dataAndTime = TimeUtils.millis2String(System.currentTimeMillis());
                //新建用户数据并保存
                TUser tUser = new TUser(
                        age[0],
                        dataAndTime + "",
                        DeviceUtils.getUniqueId(this) + "",
                        phone + "",
                        gender[0]
                );
                if (tUser.save()) {
                    SPUtils.getInstance().put(Constants.REGIST_PHONE, phone + "");
                    //上传所有数据
//                    presenter.uploadUser(tUser);

                    //用户注册成功后创建统计数据
                    TStats stats = new TStats();
                    stats.setPhone(phone);
                    stats.setRegtime(dataAndTime);
                    stats.setCreatedate(date);
                    stats.setIsmobile(SPUtils.getInstance().getBoolean(IS_MOBILE));
                    stats.setImcode(DeviceUtils.getUniqueId(this) + "");
                    stats.setOpenlock(1);   //默认解锁次数为1
                    stats.save();
                }

                materialDialog.cancel();
            } else {
                ToastUtils.showShort("验证码不正确，请重新输入");
            }

        });

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public void onError(String error) {

    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.ywb.tuyue.ui.main.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    /**
     * 注册接受广播
     */
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String message = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    if (!ExampleUtil.isEmpty(message)) {
                        try {
                            JSONObject jsonObject = new JSONObject(message);
                            String picurl = jsonObject.getString("picurl");
                            if (!picurl.contains("http")) {
                                picurl = Constants.BASE_IMAGE_URL + picurl;
                            }
                            String name = jsonObject.getString("name");
                            int id = jsonObject.getInt("id");
                            if (SPUtils.getInstance().getBoolean(NETWORK_AVAILABLE)) {
                                ShowPushMessageUtils.showPushDialog(
                                        MainActivity.this,
                                        picurl + "",
                                        isForeground == true, isFinishing());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }
            } catch (Exception e) {
            }
        }
    }


}
