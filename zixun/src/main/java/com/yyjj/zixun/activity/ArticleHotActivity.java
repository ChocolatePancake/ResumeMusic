package com.yyjj.zixun.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsArticleAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.newsModel.NewsServer;
import tech.com.commoncore.newsModel.PageNews;
import tech.com.commoncore.newsModel.PageNewsEvent;

import static tech.com.commoncore.manager.ModelPathManager.zixun_articlePath;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */

@Route(path = zixun_articlePath)
public class ArticleHotActivity extends BaseTitleActivity {
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;

    private NewsArticleAdapter newsArticleAdapter;

    private ArrayList<PageNews> pageNews = new ArrayList<>();


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("实时新闻").setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.zixun_activity_article_hot;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        smartRefreshLayout = findViewById(R.id.smartLayout_rootFastLib);
        recyclerView = findViewById(R.id.rv_contentFastLib);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsArticleAdapter = new NewsArticleAdapter(R.layout.zixun_layout_item_artical);
        recyclerView.setAdapter(newsArticleAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                NewsServer.getInstance().requestPageNews(25, 1);
            }
        });

        if (pageNews == null || pageNews.isEmpty()) {
            smartRefreshLayout.autoRefresh();
            NewsServer.getInstance().requestPageNews(25, 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void newsEvent(PageNewsEvent event) {
        smartRefreshLayout.finishRefresh();
        if (event.code == 1) {
            pageNews.clear();
            pageNews.addAll(event.pageNews);
            newsArticleAdapter.replaceData(pageNews);
            newsArticleAdapter.notifyDataSetChanged();
        }
    }
}
