package com.yyjj.zixun.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseFragment;

public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> baseFragments;
    private String[] titles;

    public NewsFragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> baseFragments, String[] titles) {
        super(fm);
        this.baseFragments = baseFragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return baseFragments.get(i);
    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
