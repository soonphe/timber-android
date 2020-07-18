package com.soonphe.timber.ui.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.soonphe.timber.R;
import com.soonphe.timber.view.adapter.HomeHotAdapter;
import com.soonphe.timber.base.BaseFragmentV4;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.view.entity.MenuBarItem;
import com.soonphe.timber.view.entity.PCarousel;
import com.soonphe.timber.view.entity.PCategory;
import com.soonphe.timber.view.entity.PGoods;
import com.soonphe.timber.view.entity.PUser;
import com.soonphe.timber.view.widget.menubar.MenuBar;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zaaach.citypicker.CityPickerActivity;
import com.zaaach.citypicker.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.soonphe.timber.constants.Constants.AMAP_LOCATION;

/**
 * @Author anna
 * @Date 2017-11-21 15:33
 * @Description 首页fragment
 */
public class HomeFragment extends BaseFragmentV4 implements HomeContract.HomeView, SwipeRefreshLayout.OnRefreshListener{

    @Inject
    HomePresenter presenter;

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.input_search_query)
    EditText inputSearchQuery;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe_view)
    SwipeRefreshLayout swipeView;

    @BindView(R.id.menu_bar_view)
    MenuBar menuBar;

//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.points)
//    LinearLayout points;

    @BindView(R.id.iv_deploy_want)
    RelativeLayout ivDeployWant;
    @BindView(R.id.iv_competitive)
    RelativeLayout ivCompetitive;
    @BindView(R.id.iv_customer)
    RelativeLayout ivCustomer;
    @BindView(R.id.iv_store)
    RelativeLayout ivStore;
    @BindView(R.id.tv_store)
    TextView tvStore;

    /* 城市定位选择 */
    private static final int REQUEST_CODE_PICK_CITY = 0;
    private AMapLocationClient mLocationClient;
    String currentCity = "";
    String cou = "";

//    /* 首页菜单 */
//    private List<View> viewPagerList;   //GridView作为一个View对象添加到ViewPager集合中
//    private ImageView[] ivPoints;   //小圆点图片的集合
//    private int totalPage;  //总的页数
//    private int mPageSize = 10;  //每页显示的最大的数量

    /* 收藏热门 */
    private HomeHotAdapter homeHotAdapter;
    private int start = 0;   //分页请求起始位置
    private boolean isLast = false;

    /**
     * 单例创建对象
     */
    private static HomeFragment instance = new HomeFragment();
    private HomeFragment (){}
    public static HomeFragment getInstance() {
        return instance;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
//        getComponent(FragmentComponent.class).inject(this);
        //获取FragmentComponent 原理同上
//        FragmentComponent.class.cast(((HasComponent<FragmentComponent>) getActivity()).getFragmentComponent()).inject(this);
        //或者DaggerFragmentComponent 作用同上
//        DaggerFragmentComponent.builder().applicationComponent((ApplicationComponent)getActivity()).
//                build().inject(this);
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        presenter.attachView(this);
//        PUser pUser = (PUser) CacheUtils.getInstance().getSerializable(USER);
        //当前登录用户存在店铺
//        if (pUser != null && pUser.getHasStore() > 2) {
//            tvStore.setText("我的店铺");
//        }
        //下滑刷新
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(getResources().getColor(R.color.swipe_color));
        //banner图
        banner.setImageLoader(new GlideImageLoader());

        //初始化底部recycleView
        homeHotAdapter = new HomeHotAdapter(R.layout.item_home_hot_collect);
        //开始加载的位置 默认1
//        homeHotAdapter.setStartUpFetchPosition(5);
        homeHotAdapter.setPreLoadNumber(5);
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        homeHotAdapter.setOnLoadMoreListener(() -> {
            homeHotAdapter.isLoading();
            rvList.postDelayed(() -> {
                homeHotAdapter.setEnableLoadMore(false);
                if (!isLast) {
                    //继续加载数据
                    presenter.getHotGoods(start);
                } else {
                    //数据全部加载完毕
                    homeHotAdapter.loadMoreEnd();
                }
            }, 2000);
        }, rvList);

        //adpter开启动画（ALPHAIN 渐显、SCALEIN 缩放、SLIDEIN_BOTTOM 从下到上，SLIDEIN_LEFT从左到右、SLIDEIN_RIGHT 从右到左）
        homeHotAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //自定义动画
//        homeHotAdapter.openLoadAnimation(new BaseAnimation() {
//            @Override
//            public Animator[] getAnimators(View view) {
//                return new Animator[]{
//                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
//                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
//                };
//            }
//        });
        //recycleView布局加载(LinearLayoutManager)
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //添加分割线
        rvList.addItemDecoration(new SpaceDecoration(20));
//        rvList.addItemDecoration(new DividerDecoration(R.color.divider_recycle,1));
//        rvList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        rvList.setAdapter(homeHotAdapter);
        //解决NestedScrollView和RecycleView滑动冲突
        rvList.setNestedScrollingEnabled(false);
        homeHotAdapter.setOnItemClickListener((adapter, view1, position) -> {
//            mOperation.addParameter("id", homeHotAdapter.getItem(position).getId());
//            mOperation.forward(GoodDetailActivity.class);
        });

    }

    @Override
    public void doBusiness(Context mContext) {

        initLocation();
        //从flash页面传递过来的数据
        List<PCategory> categoryMenuList = (List<PCategory>) mOperation.getListParameter("pCategoryList");
        List<PCarousel> carouselList = (List<PCarousel>) mOperation.getListParameter("pCarouselList");
        List<PGoods> pGoodsList = (List<PGoods>) mOperation.getListParameter("pGoodsList");
        //如果flash页面数据加载失败，则在这里重新加载
        if (categoryMenuList != null && categoryMenuList.size() > 0) {
            showCategoryMenuList(categoryMenuList);
        } else {
            presenter.getCategoryMenuList(0, 0);
        }
        if (carouselList != null && carouselList.size() > 0) {
            showBannerList(carouselList);
        } else {
            presenter.getBannerList("");
        }
        if (pGoodsList != null && pGoodsList.size() > 0) {
            start += pGoodsList.size();
            showHotGoodsList(pGoodsList);
        } else {
            presenter.getHotGoods(0);
        }
    }

    @Override
    public void lazyInitBusiness(Context mContext) {

    }

    @Override
    public void showCategoryMenuList(List<PCategory> categoryList) {

        List<MenuBarItem> list = new ArrayList<>();
        for (int i= 0;i<20;i++){
            MenuBarItem item =  new MenuBarItem();
            item.setName("测试"+i);
            list.add(item);
        }
        menuBar.setmDatas(list);
        //自定义view回调监听
        menuBar.setOnMenuItermClickListener(position -> {

            LogUtils.e("点击"+position);
        });

        //首页滚动菜单实现
        /* 1.使用ViewPager和GridView实现*/
        //总的页数向上取整
//        totalPage = (int) Math.ceil(categoryList.size() * 1.0 / mPageSize);
//        viewPagerList = new ArrayList<View>();
//        for (int i = 0; i < totalPage; i++) {
//            //每个页面都是inflate出一个新实例
//            final GridView gridView = (GridView) View.inflate(getContext(), R.layout.item_home_gridview, null);
//            gridView.setAdapter(new GridViewAdapter(getContext(), categoryList, i, mPageSize));
//            //添加item点击监听
//            int finalI = i;
//            gridView.setOnItemClickListener((arg0, arg1, position, arg3) -> {
////                LogUtils.e("\"点击了\" + (finalI * mPageSize + position)");
////                mOperation.addParameter("categoryId", categoryList.get(finalI * mPageSize + position).getId());
////                mOperation.forward(GoodsActivity.class);
//            });
//            //每一个GridView作为一个View对象添加到ViewPager集合中
//            viewPagerList.add(gridView);
//        }
//        //设置ViewPager适配器
//        viewpager.setAdapter(new MyViewPagerAdapter(viewPagerList));
//
//        /* 菜单底部圆点指示器实现 */
//        //移除子视图
//        points.removeAllViews();
//        //添加小圆点
//        ivPoints = new ImageView[totalPage];
//        for (int i = 0; i < totalPage; i++) {
//            //循坏加入点点图片组
//            ivPoints[i] = new ImageView(getContext());
//            if (i == 0) {
//                ivPoints[i].setImageResource(R.mipmap.dot_focus_gray);
//            } else {
//                ivPoints[i].setImageResource(R.mipmap.dot_normal_gray);
//            }
//            ivPoints[i].setPadding(8, 8, 8, 8);
//
//
//            points.addView(ivPoints[i]);
//        }
//
//        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //currentPage = position;
//                //循环，选择页蓝，其他为灰色
//                for (int i = 0; i < totalPage; i++) {
//                    if (i == position) {
//                        ivPoints[i].setImageResource(R.mipmap.dot_focus_gray);
//                    } else {
//                        ivPoints[i].setImageResource(R.mipmap.dot_normal_gray);
//                    }
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    @Override
    public void showBannerList(List<PCarousel> banners) {
        List<PCarousel> list = new ArrayList<>();
        for (int i=0;i<3;i++){
            PCarousel pCarousel= new PCarousel();
            pCarousel.setPicUrl("");
            list.add(pCarousel);
        }
        banner.update(list);
        banner.setImages(list);
        banner.setDelayTime(4000);
        banner.start();

        banner.setOnBannerListener(position -> {
            LogUtils.d("___________________" + banners.get(position).toString());
//            Logger.addLogAdapter(new AndroidLogAdapter());
//            Logger.d("___________________" + banners.get(position).toString());
//            if (banners.get(position).getState() == 1) {
//                BannerDetailsActivity.launch(mContext, banners.get(position).getBannerUrl());
//            }
        });
        swipeView.setRefreshing(false);

    }

    @Override
    public void showHotGoodsList(List<PGoods> pCard) {
        if (pCard.size() == 0) {
            isLast = true;
        } else {
            if (start > 0) {
                homeHotAdapter.addData(pCard);
                homeHotAdapter.loadMoreComplete();
            } else {
                homeHotAdapter.setNewData(pCard);
            }
            start += pCard.size();
            if (pCard.size() == 15) {
                isLast = false;
            } else {
                isLast = true;
            }
        }
        swipeView.setRefreshing(false);
    }

    @Override
    public void getUserInfoSuccess(View view, PUser pUser) {
//        //服务器数据与本地同步
//        CacheUtils.getInstance().put(Constants.USER, pUser);
//        //验证是否实名认证  0未申请 1审核中
//         if (pUser.getCertifyState()==0){
//            MaterialDialog materialDialog = mOperation.showCustomerDialog("", R.layout.dialog_confirm);
//            ((TextView) materialDialog.getCustomView().findViewById(R.id.tv_msg)).setText("您还没有进行实名认证,是否立即跳转实名认证");
//            materialDialog.getCustomView().findViewById(R.id.iv_cancle).setOnClickListener(v -> {
//                materialDialog.cancel();
//            });
//            materialDialog.getCustomView().findViewById(R.id.bt_fail).setOnClickListener(v -> {
//                materialDialog.cancel();
//            });
//            materialDialog.getCustomView().findViewById(R.id.bt_ok).setOnClickListener(v -> {
//                materialDialog.cancel();
//                mOperation.forward(CertifyActivity.class);
//
//            });
//            return;
//        }else if (pUser.getCertifyState()==1){    //审核中
//            mOperation.showBasicDialog("您的实名认证申请正在审核");
//            return;
//        }
//        switch (view.getId()) {
//            case R.id.iv_deploy_want:   //发布需求
//                mOperation.forward(PublishNeedActivity.class);
//                break;
//            case R.id.iv_competitive:   //商家竞标
//                mOperation.forward(NeedActivity.class);
//                break;
//            case R.id.iv_customer:  //客服咨询
//                break;
//            case R.id.iv_store:
//                if (pUser.getHasStore()==0){    //未申请店铺
//                    MaterialDialog materialDialog = mOperation.showCustomerDialog("", R.layout.dialog_confirm);
//                    ((TextView) materialDialog.getCustomView().findViewById(R.id.tv_msg)).setText("您还没有店铺,是否立即申请");
//                    materialDialog.getCustomView().findViewById(R.id.iv_cancle).setOnClickListener(v -> {
//                        materialDialog.cancel();
//                    });
//                    materialDialog.getCustomView().findViewById(R.id.bt_fail).setOnClickListener(v -> {
//                        materialDialog.cancel();
//                    });
//                    materialDialog.getCustomView().findViewById(R.id.bt_ok).setOnClickListener(v -> {
//                        materialDialog.cancel();
//                        mOperation.forward(ApplyStoreActivity.class);
//                    });
//                }else if (pUser.getHasStore() == 1) { //店铺审核中
//                    mOperation.showBasicDialog("您的店铺申请正在审核");
//                }else if ( pUser.getHasStore() > 2) {  //店铺审核通过
//                    tvStore.setText("我的店铺");
//                    mOperation.addParameter("storeId", pUser.getHasStore());
//                    mOperation.forward(StoreActivity.class);
//                }
//                break;
//        }

    }

    /**
     * 定位选择/商品搜索
     *
     * @param view
     */
    @OnClick({R.id.tv_city, R.id.tv_search})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:  //定位选择
                startActivityForResult(new Intent(this.mContext, CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            case R.id.tv_search:    //搜索
//                mOperation.addParameter("categoryId", 0);
//                mOperation.addParameter("name", inputSearchQuery.getText().toString());
//                mOperation.forward(GoodsActivity.class);
                break;
        }
    }

    /**
     * 发布需求/商家竞标/客服咨询/我的店铺
     *
     * @param view
     */
    @OnClick({R.id.iv_deploy_want, R.id.iv_competitive, R.id.iv_customer, R.id.iv_store})
    public void onViewClicked(View view) {
        //验证是否登录
//        PUser pUser = (PUser) CacheUtils.getInstance().getSerializable(USER);
//        if (pUser == null) {
//            mOperation.forward(LoginActivity.class);
//        }else{
            //同步服务器与本地数据
//            presenter.getUserInfo(view, Integer.parseInt(CacheUtils.getInstance().getString(Constants.USER_ID).toString()));
//        }
    }

    //重写onActivityResult方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                tvCity.setText("" + city);
                CacheUtils.getInstance().put(AMAP_LOCATION,city);
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
    public void onRefresh() {
        start = 0;
        presenter.getCategoryMenuList(0, 0);
        presenter.getBannerList("");
        presenter.getHotGoods(0);
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

    private void initLocation() {
        //声明AMapLocationClient类对象
        mLocationClient = new AMapLocationClient(this.getContext());
        //配置定位参数
        AMapLocationClientOption option = new AMapLocationClientOption();
        //高精度定位模式：会同时使用网络定位和GPS定位
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        //设置 定位回调监听器
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        /*
                        //基础定位信息
                         amapLocation.getCountry();//国家信息
                         amapLocation.getProvince();//省信息
                         amapLocation.getCity();//城市信息
                         amapLocation.getDistrict();//城区信息
                         amapLocation.getStreet();//街道信息
                         amapLocation.getStreetNum();//街道门牌号信息
                         //获取定位时间
                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         Date date = new Date(amapLocation.getTime());
                         */
                        String city = aMapLocation.getCity();
                        String cou = aMapLocation.getDistrict();
                        Log.e("onLocationChanged", "city: " + city);
                        Log.e("onLocationChanged", "district: " + cou);
                        String location = StringUtils.extractLocation(city, cou);
                        tvCity.setText(location);
                        CacheUtils.getInstance().put(AMAP_LOCATION, location);
                    } else {
                        //定位失败
                        currentCity = "定位失败";
                        tvCity.setText(currentCity);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }
}
