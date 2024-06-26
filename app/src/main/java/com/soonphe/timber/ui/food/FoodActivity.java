package com.soonphe.timber.ui.food;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.adapter.FoodAdapter;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.entity.TFood;
import com.soonphe.timber.entity.TFoodType;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.entity.TabEntity;
import com.soonphe.timber.ui.data.DataContract;
import com.soonphe.timber.ui.data.DataPresenter;
import com.soonphe.timber.view.widget.titlebar.AppTitle;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * 餐饮
 *
 * @author soonphe
 * @since 1.0
 */
public class FoodActivity extends BaseActivity implements FoodContract.View, DataContract.View  {

    @Inject
    FoodPresenter presenter;
    @Inject
    DataPresenter dataPresenter;

    @BindView(R.id.app_title_id)
    AppTitle appTitle;
    @BindView(R.id.tl_2)
    CommonTabLayout tl2;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sm_refresh)
    SmartRefreshLayout smRefresh;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private FoodAdapter foodAdapter;
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
        return R.layout.activity_food;
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

        foodAdapter = new FoodAdapter(R.layout.item_food);
        rvList.setLayoutManager(new GridLayoutManager(this, 4));
        rvList.addItemDecoration(new SpaceDecoration(10));
        rvList.setAdapter(foodAdapter);
        rvList.setNestedScrollingEnabled(false);
        foodAdapter.setOnItemClickListener((adapter, view1, position) -> {
//            mOperation.addParameter("book", ((TBook) adapter.getItem(position)).getId());
//            mOperation.forward(BookreadActivity.class);
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getTypeList();
    }

    @Override
    public void getTypeListSuccess(List<TFoodType> list) {

        for (int i = 0; i < list.size(); i++) {
            mTabEntities.add(new TabEntity(list.get(i).getName()));
        }
        mTabEntities.add(new TabEntity("餐饮类型1"));
        mTabEntities.add(new TabEntity("餐饮类型2"));
        mTabEntities.add(new TabEntity("餐饮类型3"));
        mTabEntities.add(new TabEntity("餐饮类型4"));
        tl2.setTabData(mTabEntities);

        tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                presenter.getFoodList(list.get(position).getTid());
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        presenter.getFoodList(list.get(0).getTid());
    }

    @Override
    public void getFoodListSuccess(List<TFood> list) {
        if (list.size() > 0) {
            foodAdapter.setNewData(list);
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
                tOpen.setFood(tOpen.getFood() + 1);
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
        LogUtils.e("点餐停留上传成功");
    }

}
