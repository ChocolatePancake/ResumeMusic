package com.yyjj.zixun.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsVideoAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.newsModel.VideoNews;
import tech.com.commoncore.utils.ScreenUtils;
import tech.com.commoncore.utils.VideoUtil;
import tech.com.commoncore.view.SpaceGridDecoration;

public class NewsVideoFragment extends BaseFragment {

    private ArrayList<VideoNews> voids = new ArrayList<>();

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView videoRecyclerView;
    private NewsVideoAdapter newsVideoAdapter;

    public static NewsVideoFragment newInstance() {
        NewsVideoFragment fragment = new NewsVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_video;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        smartRefreshLayout = mContentView.findViewById(R.id.news_video_smart_refresh);
        videoRecyclerView = mContentView.findViewById(R.id.news_video_recycler_view);

        videoRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        videoRecyclerView.addItemDecoration(new SpaceGridDecoration(ScreenUtils.dip2px(10), ScreenUtils.dip2px(10)));
        newsVideoAdapter = new NewsVideoAdapter(R.layout.zixun_layout_item_video);
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
