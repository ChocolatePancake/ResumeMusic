package com.yyjj.zixun.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.aries.ui.view.title.TitleBarView;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsFragmentAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.base.BaseTitleFragment;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class ZixunFragment extends BaseTitleFragment {

    private TabLayout newsSimpleTitleBar;
    private ViewPager fragmentsPage;

    private NewsFragmentAdapter newsFragmentAdapter;
    private NewsVideoFragment newsVideoFragment = new NewsVideoFragment();
    private NewsIndustryFragment newsIndustryFragment = new NewsIndustryFragment();
    private NewsCalendarFragment newsCalendarFragment = new NewsCalendarFragment();

    private static final String[] titles = {"资讯视频", "行业资讯", "资讯日历"};

    public static ZixunFragment newInstance() {
        ZixunFragment fragment = new ZixunFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("资讯")
                .setTextColor(Color.WHITE)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.zixun_fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        newsSimpleTitleBar = mContentView.findViewById(R.id.news_sliding_tab);
        fragmentsPage = mContentView.findViewById(R.id.news_sliding_fragment_page);
        buildFragmentViewPage();
    }

    private void buildFragmentViewPage() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(newsVideoFragment);
        fragments.add(newsIndustryFragment);
        fragments.add(newsCalendarFragment);
        FragmentManager fragmentManager = getChildFragmentManager();
        newsFragmentAdapter = new NewsFragmentAdapter(fragmentManager, fragments, titles);
        fragmentsPage.setOffscreenPageLimit(2);
        fragmentsPage.setAdapter(newsFragmentAdapter);
        newsSimpleTitleBar.setupWithViewPager(fragmentsPage);
    }
}
