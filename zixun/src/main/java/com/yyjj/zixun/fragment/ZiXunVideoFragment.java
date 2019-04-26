package com.yyjj.zixun.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aries.ui.view.title.TitleBarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsMainVideoAdapter;
import com.yyjj.zixun.adapter.NewsVideoAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseTitleFragment;
import tech.com.commoncore.newsModel.VideoNews;
import tech.com.commoncore.utils.ScreenUtils;
import tech.com.commoncore.utils.VideoUtil;
import tech.com.commoncore.view.SpaceGridDecoration;


public class ZiXunVideoFragment extends BaseTitleFragment {

    private ArrayList<VideoNews> voids = new ArrayList<>();

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView videoRecyclerView;
    private NewsMainVideoAdapter newsVideoAdapter;

    public static ZiXunVideoFragment newInstance() {
        ZiXunVideoFragment fragment = new ZiXunVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("视频专区")
                .setTextColor(Color.WHITE)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_zi_xun_video;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        smartRefreshLayout = mContentView.findViewById(R.id.news_video_smart_refresh);
        videoRecyclerView = mContentView.findViewById(R.id.news_video_recycler_view);

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        newsVideoAdapter = new NewsMainVideoAdapter(R.layout.zixun_layout_video_fragment_item);
        videoRecyclerView.setAdapter(newsVideoAdapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                setVoids();
            }
        });
        buildVideoData();
    }

    private void buildVideoData() {
        if (voids.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
    }

    private void setVoids() {
        voids.clear();
        voids.addAll(getVideos());
        smartRefreshLayout.finishRefresh();
        newsVideoAdapter.replaceData(voids);
        newsVideoAdapter.notifyDataSetChanged();
    }

    public ArrayList<VideoNews> getVideos() {
        ArrayList<VideoNews> v = new ArrayList<>();
        for (String address : VideoUtil.videos) {
            String url = VideoUtil.getRealUrl(address);
            v.add(new VideoNews(url, "", address, ""));
        }
        return v;
    }
}
