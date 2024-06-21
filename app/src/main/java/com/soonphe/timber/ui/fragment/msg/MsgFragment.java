package com.soonphe.timber.ui.fragment.msg;

import static android.app.Activity.RESULT_OK;
import static com.soonphe.timber.constants.Constants.AMAP_LOCATION;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocationClient;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.HomeHotAdapter;
import com.soonphe.timber.base.BaseFragmentV4;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.PCarousel;
import com.soonphe.timber.entity.PCategory;
import com.soonphe.timber.entity.PUser;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.view.entity.MenuBarItem;
import com.soonphe.timber.view.widget.menubar.MenuBar;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页fragment
 *
 * @author soonphe
 * @since 1.0
 */
public class MsgFragment extends BaseFragmentV4 implements MsgContract.MsgView {

    @Inject
    MsgPresenter presenter;

    @BindView(R.id.app_bar_title)
    TextView app_bar_title;

    /**
     * 单例创建对象
     */
    private static MsgFragment instance = new MsgFragment();
    public static MsgFragment getInstance() {
        return instance;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_msg;
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
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getUserInfo(1);
    }

    @Override
    public void lazyInitBusiness(Context mContext) {

    }

    @OnClick({R.id.app_bar_title})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_title:
                Log.d("","");
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

}
