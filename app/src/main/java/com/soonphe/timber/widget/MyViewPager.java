package com.soonphe.timber.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 自定义ViewPager，用于禁止左右滑动
 *
 * ViewPager2 也继承自ViewGroup，但其内部可以明显的看到 RecyclerView 影子，所以可以说是基于 RecyclerView实现，那么这也意味着性能的提升，毕竟ViewHodelr减少了内存开销，同时RecyclerView相关方法在ViewPager2也可以看到类似封装
 * ViewPager2 新增功能是建立在 ViewPager 已有功能的基础上的扩展，例如ViewPager2支持垂直滑动，同时也是支持水平滑动的
 * ViewPager2 支持垂直方向滑动，而 ViewPager 仅支持水平方向滑动（扩展组件功能）
 * ViewPager 使用的Adapter，一般为PagerAdapter、及其子类 FragmentPagerAdapter、FragmentStatePagerAdapter
 * ViewPager2 既然基于RecyclerView实现，那么它所使用的Adapter同理也应该基于RecyclerView.Adapter，所以新增了 FragmentStateAdapter
 *
 * @author soonphe
 * @since 1.0
 */
public class MyViewPager extends ViewPager {

    private boolean enabled;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    // 触摸没有反应就可以了
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
