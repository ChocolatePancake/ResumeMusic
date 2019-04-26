package com.leancloud.home.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.leancloud.home.R;
import com.leancloud.home.adapter.HomeCircleAdapter;
import com.leancloud.home.adapter.HomeMarketAdapter;
import com.leancloud.home.adapter.HomeNewsAdapter;
import com.vise.xsnow.event.BusManager;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.event.SwitchEvent;
import tech.com.commoncore.futuresModel.FuturesEvent;
import tech.com.commoncore.futuresModel.MarketServerImpl;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.newsModel.NewsServer;
import tech.com.commoncore.newsModel.PageNews;
import tech.com.commoncore.newsModel.PageNewsEvent;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 */
public class HomeFragment extends BaseFragment {
    private NestedScrollView nestedScrollView;
    private LinearLayout customTitleBar;

    private RecyclerView newsRecyclerView, marketRecyclerView, circleRecyclerView;
    private HomeNewsAdapter homeNewsAdapter;
    private HomeMarketAdapter homeMarketAdapter;
    private HomeCircleAdapter homeCircleAdapter;


    private float titleHarHeight = 0;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.home_fragment_home;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setWindowStyle() {
        Window window = mContext.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(Bundle savedInstanceState) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerOnClick(v.getId());
            }
        };
        titleHarHeight = getResources().getDimension(R.dimen.dp_120);
        setWindowStyle();
        nestedScrollView = mContentView.findViewById(R.id.nested_scroll_view);
        customTitleBar = mContentView.findViewById(R.id.home_custom_title_bar);
        newsRecyclerView = mContentView.findViewById(R.id.home_news_recycler_view);
        marketRecyclerView = mContentView.findViewById(R.id.home_market_recycler_view);
        circleRecyclerView = mContentView.findViewById(R.id.home_circle_recycler_view);

        mContentView.findViewById(R.id.to_novice_school_image).setOnClickListener(clickListener);
        mContentView.findViewById(R.id.to_video_guile_image).setOnClickListener(clickListener);
        mContentView.findViewById(R.id.title_bar_search_image).setOnClickListener(clickListener);
        mContentView.findViewById(R.id.title_bar_server_image).setOnClickListener(clickListener);
        mContentView.findViewById(R.id.news_check_more_tx).setOnClickListener(clickListener);
        mContentView.findViewById(R.id.market_check_more_tx).setOnClickListener(clickListener);
        mContentView.findViewById(R.id.circle_check_more_tx).setOnClickListener(clickListener);

        customTitleBar.setAlpha(0);
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                customTitleBar.setAlpha(scrollY / titleHarHeight);
            }
        });
        initPage();
        requestRecommendFutures();
    }

    private void initPage() {
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.default_divider));
        newsRecyclerView.addItemDecoration(dividerItemDecoration);
        homeNewsAdapter = new HomeNewsAdapter(R.layout.home_layout_item_news);
        newsRecyclerView.setAdapter(homeNewsAdapter);

        homeMarketAdapter = new HomeMarketAdapter(R.layout.home_layout_market_item);
        marketRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        marketRecyclerView.setAdapter(homeMarketAdapter);

        homeCircleAdapter = new HomeCircleAdapter(R.layout.home_layout_circlr_item);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        circleRecyclerView.addItemDecoration(decoration);
        circleRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        circleRecyclerView.setAdapter(homeCircleAdapter);
    }

    private void handlerOnClick(int id) {
        if (id == R.id.to_novice_school_image) {
            ARouter.getInstance().build(ModelPathManager.futures_noviceSchoolPath).navigation();
            return;
        }
        if (id == R.id.to_video_guile_image) {
            ARouter.getInstance().build(ModelPathManager.zixun_videoPath).navigation();
            return;
        }
        if (id == R.id.title_bar_search_image) {
            ARouter.getInstance().build(ModelPathManager.futures_searchValue).navigation();
            return;
        }
        if (id == R.id.title_bar_server_image) {
            if (AVUser.getCurrentUser() != null) {
                ARouter.getInstance().build(ModelPathManager.my_onLineServerPath).navigation();
            } else {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            }
            return;
        }
        if (id == R.id.news_check_more_tx) {
            ARouter.getInstance().build(ModelPathManager.zixun_articlePath).navigation();
            return;
        }
        if (id == R.id.circle_check_more_tx) {
            BusManager.getBus().post(new SwitchEvent(2));
            return;
        }
        if (id == R.id.market_check_more_tx) {
            BusManager.getBus().post(new SwitchEvent(1));
            return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void newsEvent(PageNewsEvent event) {
        if (event.code != 0) {
            if (event.pageNews.size() > 5) {
                homeNewsAdapter.replaceData(event.pageNews.subList(0, 5));
            } else {
                homeNewsAdapter.replaceData(event.pageNews);
            }
            homeNewsAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void marketEvent(FuturesEvent.MarketEventOptional eventOptional) {
        if (eventOptional.code != 0) {
            homeMarketAdapter.replaceData(eventOptional.date);
            homeMarketAdapter.notifyDataSetChanged();
        }
    }

    private void requestRecommendFutures() {
        NewsServer.getInstance().requestPageNews(25, 1);
        MarketServerImpl.getInstance().requestOptionalMarket("SC0", "FU0", "AU0", "AG0", "ZC0");
        requestAllData();
    }

    private void requestAllData() {
        AVDbGlobal.getInstance().getAVDb().requestCommunity(null, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() > 5) {
                        homeCircleAdapter.replaceData(list.subList(0, 5));
                    } else {
                        homeCircleAdapter.replaceData(list);
                    }
                }
            }
        });
        AVDbGlobal.getInstance().getAVDb().requestAttentionList(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    homeCircleAdapter.setAttentionList(list);
                    homeCircleAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
        AVDbGlobal.getInstance().getAVDb().requestLikeByUser(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    homeCircleAdapter.setMyLikeList(list);
                    homeCircleAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
        AVDbGlobal.getInstance().getAVDb().requestCollect(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    homeCircleAdapter.setMyCollectionList(list);
                    homeCircleAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }

}
