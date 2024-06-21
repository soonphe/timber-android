package com.soonphe.timber.view.widget.menubar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.soonphe.timber.view.R;
import com.soonphe.timber.view.adapter.MenuBarViewPagerAdapter;
import com.soonphe.timber.view.adapter.MenuGridViewAdapter;
import com.soonphe.timber.view.entity.MenuBarItem;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * 自定义多页小菜单
 *
 * @author soonphe
 * @since 1.0
 */
public class MenuBar extends RelativeLayout {

    private Context mContext;
    private ViewPager viewpager;
    private LinearLayout points;

    private List<MenuBarItem> mDatas;   //菜单集合
    private List<View> viewPagerList;   //GridView作为一个View对象添加到ViewPager集合中
    private ImageView[] ivPoints;   //小圆点图片的集合
    private int totalPage = 1;  //总的页数
    private int mPageSize = 10;  //每页显示的最大的数量
    private int mPageRowSize = 5;  //每页横排显示的最大的数量

    private OnMenuItermClickListener onMenuItermClickListener;

    /**
     * 设置监听回调
     *
     * @param onLockListener
     */
    public void setOnMenuItermClickListener(OnMenuItermClickListener onLockListener) {
        this.onMenuItermClickListener = onLockListener;
    }

    /**
     * 在xml布局文件中使用时自动调用
     *
     * @param context
     * @param attrs
     */
    public MenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
        initAttrs(context, attrs);
    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_menu_bar, this);
        viewpager = (ViewPager) findViewById(R.id.menu_viewpager);
        points = (LinearLayout) findViewById(R.id.menu_points);

        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (totalPage > 1) {
                    //循环，选择页蓝，其他为灰色
                    for (int i = 0; i < totalPage; i++) {
                        if (i == position) {
                            ivPoints[i].setImageResource(R.mipmap.dot_focus_gray);
                        } else {
                            ivPoints[i].setImageResource(R.mipmap.dot_normal_gray);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initAttrs(Context context, AttributeSet attrs) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitle);
//        Boolean titleBack = ResourceUtils.getBoolean(typedArray, R.styleable.AppTitle_showBack, R.bool.default_showBack);
//        if (titleBack){
//            appBarBack.setVisibility(VISIBLE);
//        }
//        canFinish = ResourceUtils.getBoolean(typedArray, R.styleable.AppTitle_canFinish, R.bool.default_canFinish);
//
//
//        typedArray.recycle();
//        if (appBarCenterBtn.isShown() || appBarRightBtn.isShown()) {
//            appBarTitle.setMaxWidth(ConvertUtils.dp2px(120));
//        } else if (appBarCenterBtn.isShown() && appBarRightBtn.isShown()) {
//            appBarTitle.setMaxWidth(ConvertUtils.dp2px(150));
//        } else {
//            appBarTitle.setMaxWidth(ConvertUtils.dp2px(200));
//        }

    }

    /**
     * 初始化菜单数据
     * 页面总数:totalPage = size/mPageSize 向上取整
     * 小圆点总数 = totalPage
     *
     * @param mDatas
     */
    public void setmDatas(List<MenuBarItem> mDatas) {
        this.mDatas = mDatas;

        //总的页数向上取整
        totalPage = (int) Math.ceil(mDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView) View.inflate(getContext(), R.layout.item_menu_gridview, null);
            gridView.setAdapter(new MenuGridViewAdapter(getContext(), mDatas, i, mPageSize));
            //添加item点击监听
            int finalI = i;
            gridView.setOnItemClickListener((arg0, arg1, position, arg3) -> {
                Log.e("MenuBar", "点击了" + finalI * mPageSize + position);
                //监听是否解锁 true代表解锁
                if (onMenuItermClickListener != null) {
                    onMenuItermClickListener.onMenuItermClickListener(finalI * mPageSize + position);    //回调监听结果
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewpager.setAdapter(new MenuBarViewPagerAdapter(viewPagerList));

        /* 菜单底部圆点指示器实现 */
        //移除子视图
        points.removeAllViews();
        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(getContext());
            if (i == 0) {
                ivPoints[i].setImageResource(R.mipmap.dot_focus_gray);
            } else {
                ivPoints[i].setImageResource(R.mipmap.dot_normal_gray);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            points.addView(ivPoints[i]);
        }


    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    public List<MenuBarItem> getmDatas() {
        return mDatas;
    }

}
