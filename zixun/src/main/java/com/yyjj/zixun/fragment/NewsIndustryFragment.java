package com.yyjj.zixun.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsArticleAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.newsModel.NewsServer;
import tech.com.commoncore.newsModel.PageNews;
import tech.com.commoncore.newsModel.PageNewsEvent;


public class NewsIndustryFragment extends BaseFragment {
    private RecyclerView industryNewsRecycler;
    private SmartRefreshLayout smartRefreshLayout;

    private NewsArticleAdapter newsArticleAdapter;

    private ArrayList<PageNews> pageNews = new ArrayList<>();

    private int page = 1;

    public static NewsIndustryFragment newInstance() {
        NewsIndustryFragment fragment = new NewsIndustryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_industry;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        industryNewsRecycler = mContentView.findViewById(R.id.news_industry_recycler_view);
        smartRefreshLayout = mContentView.findViewById(R.id.news_industry_smart_refresh);

        industryNewsRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.zixun_news_recycler_divider));
        industryNewsRecycler.addItemDecoration(dividerItemDecoration);
        newsArticleAdapter = new NewsArticleAdapter(R.layout.zixun_layout_item_artical);
        industryNewsRecycler.setAdapter(newsArticleAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestPageNews();
            }
        });
        initData();
    }

    private void requestPageNews() {
        NewsServer.getInstance().requestPageNews(25, page);
        page++;
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void PageNewsEvent(PageNewsEvent event) {
        smartRefreshLayout.finishRefresh();
        if (event.code != 0) {
            pageNews.addAll(0, event.pageNews);
            newsArticleAdapter.replaceData(pageNews);
            newsArticleAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        if (pageNews.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
    }
}
