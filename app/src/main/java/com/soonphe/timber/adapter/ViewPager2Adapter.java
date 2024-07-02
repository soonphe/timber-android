package com.soonphe.timber.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * viewPager2 Fragment适配器
 * FragmentStatePagerAdapter已过时
 *
 * @author soonphe
 * @since 1.0
 */
public class ViewPager2Adapter extends FragmentStateAdapter {
    private List<Fragment> mFragmentList      = new ArrayList<>();
    private List<String>   mFragmentTitleList = new ArrayList<String>();

    public ViewPager2Adapter(FragmentActivity fm) {
        super(fm);
    }

    public ViewPager2Adapter(FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.mFragmentList = fragments;
    }

    public ViewPager2Adapter(FragmentActivity fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.mFragmentList = fragments;
        this.mFragmentTitleList = titles;
    }

    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);

    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}