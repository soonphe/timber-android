package com.soonphe.timber.ui.city.city;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.CityAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.TCity;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.ui.city.citydetail.CitydetailActivity;
import com.soonphe.timber.ui.data.DataContract;
import com.soonphe.timber.ui.data.DataPresenter;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import org.litepal.LitePal;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * 城市activity
 *
 * @author soonphe
 * @since 1.0
 */
public class CityActivity extends BaseActivity implements CityContract.View, DataContract.View {

    @Inject
    CityPresenter presenter;
    @Inject
    DataPresenter dataPresenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    private CityAdapter cityAdapter;
    private long stayTime;//模块停留时长

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
    public int bindLayout() {
        return R.layout.activity_city;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        BarUtils.setStatusBarColor(this, 0);
        presenter.attachView(this);
        dataPresenter.attachView(this);
        stayTime = System.currentTimeMillis();

        cityAdapter = new CityAdapter(R.layout.item_city);
        rvList.setLayoutManager(new GridLayoutManager(this, 3));
        rvList.addItemDecoration(new SpaceDecoration(10));
        rvList.setAdapter(cityAdapter);
        rvList.setNestedScrollingEnabled(false);

        cityAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mOperation.addParameter("city", ((TCity) adapter.getItem(position)).getTid());
            mOperation.forward(CitydetailActivity.class);
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getCityList();
    }


    @Override
    public void getCityListSuccess(List<TCity> list) {
        if (list.size() > 0) {
            cityAdapter.setNewData(list);
        }
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //停留时长统计:单位S
        long stayDataTime = TimeUtils.getTimeSpan(stayTime, System.currentTimeMillis(), TimeConstants.SEC);
        String phone = SPUtils.getInstance().getString(Constants.REGIST_PHONE, "");
        //判断这里是否存在用户，如果存在则要记录数据
        if (!"111111".equals(phone)) {
            //判断今天是否创建过统计数据——有数据则更新数据+1
            TStats tOpen = LitePal.where("phone = ?", phone + "").order("id desc").findFirst(TStats.class);
            if (tOpen != null) {
                tOpen.setCity(tOpen.getCity() + 1);
                tOpen.setFoodtime((int) (tOpen.getFoodtime() + stayDataTime));
                boolean result = tOpen.save();
                //判断当前网络可用且用户数据保存成功
                if (result) {
                    //上传所有数据
                    dataPresenter.uploadData(tOpen);
                }
            }
        }
    }

    @Override
    public void uploadDataSuccess() {
        LogUtils.e("城市停留上传成功");
    }
}
