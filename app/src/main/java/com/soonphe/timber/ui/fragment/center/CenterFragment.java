package com.soonphe.timber.ui.fragment.center;

import static android.app.Activity.RESULT_OK;

import static com.soonphe.timber.constants.Constants.IS_MOBILE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseFragmentV4;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.PCarousel;
import com.soonphe.timber.entity.PUser;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.entity.TUser;
import com.soonphe.timber.ui.advert.advertise.AdvertContentActivity;
import com.soonphe.timber.ui.article.ArticleActivity;
import com.soonphe.timber.ui.book.book.BookActivity;
import com.soonphe.timber.ui.city.city.CityActivity;
import com.soonphe.timber.ui.food.FoodActivity;
import com.soonphe.timber.ui.game.game.GameActivity;
import com.soonphe.timber.ui.video.CinemaActivity;
import com.soonphe.timber.utils.DeviceUtils;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城fragment
 *
 * @author soonphe
 * @since 1.0
 */
public class CenterFragment extends BaseFragmentV4 implements CenterContract.CenterView {

    @Inject
    CenterPresenter presenter;
    @BindView(R.id.app_bar_title)
    TextView app_bar_title;

    @BindView(R.id.advertise1)
    ImageView advertise1;
    @BindView(R.id.advertise2)
    ImageView advertise2;
    @BindView(R.id.ll_movie)
    LinearLayout llMovie;
    @BindView(R.id.ll_game)
    LinearLayout llGame;
    @BindView(R.id.ll_book)
    LinearLayout llBook;
    @BindView(R.id.ll_food)
    LinearLayout llFood;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.ll_subway)
    LinearLayout llSubway;

    MaterialDialog materialDialog;
    /**
     * 单例创建对象
     */
    private static CenterFragment instance = new CenterFragment();
    public static CenterFragment getInstance() {
        return instance;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_center;
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        presenter.attachView(this);
        //加载头部广告位
//        Glide.with(this.mContext).load(R.mipmap.main_header_01).into(advertise1);
//        Glide.with(this.mContext).load(R.mipmap.main_header_02).into(advertise2);
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getUserInfo(1);
    }

    @Override
    public void lazyInitBusiness(Context mContext) {

    }

    @OnClick({R.id.app_bar_title, R.id.advertise1, R.id.advertise2, R.id.ll_movie, R.id.ll_game, R.id.ll_book, R.id.ll_food, R.id.ll_city, R.id.ll_subway})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_title:
                Log.d(this.TAG,"头部标题点击");
                //弹出对话框
                onDialog();
                break;
            case R.id.advertise1:
                //数据埋点+更新统计信息+跳转activity
                //跳转加传参
                mOperation.addParameter("advert", 0);
                mOperation.forward(AdvertContentActivity.class);
                break;
            case R.id.advertise2:
                mOperation.forward(AdvertContentActivity.class);
                break;
            case R.id.ll_movie:
                mOperation.forward(CinemaActivity.class);
                break;
            case R.id.ll_game:
                mOperation.forward(GameActivity.class);
                break;
            case R.id.ll_book:
                mOperation.forward(BookActivity.class);
                break;
            case R.id.ll_food:
                mOperation.forward(FoodActivity.class);
                break;
            case R.id.ll_city:
                mOperation.forward(CityActivity.class);
                break;
            case R.id.ll_subway:
                mOperation.forward(ArticleActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {
                String obj = data.getStringExtra("key");
            }
        }
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

    @Override
    public void getUserInfoSuccess(PUser certify) {

    }

    /**
     * 轮播图加载图片
     */
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            String url = Constants.BASE_IMAGE_URL + ((PCarousel) path).getPicUrl();
            Glide.with(context).load(R.mipmap.banner_city).error(R.mipmap.banner_city).into(imageView);
        }
    }

    @Override
    public void onResume() {
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

        materialDialog = new MaterialDialog.Builder(this.getContext())
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
            //直接关闭
            materialDialog.cancel();

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
                        DeviceUtils.getUniqueId(this.getContext()) + "",
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
                    stats.setImcode(DeviceUtils.getUniqueId(this.getContext()) + "");
                    stats.setOpenlock(1);   //默认解锁次数为1
                    stats.save();
                }

                materialDialog.cancel();
            } else {
                ToastUtils.showShort("验证码不正确，请重新输入");
            }

        });

    }

}
