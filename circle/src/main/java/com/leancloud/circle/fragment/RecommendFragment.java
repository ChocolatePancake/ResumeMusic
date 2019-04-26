package com.leancloud.circle.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.leancloud.circle.R;
import com.leancloud.circle.adapter.CircleAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.bus.CircleEvent;
import tech.com.commoncore.plog;

/**
 * 推荐界面
 */
public class RecommendFragment extends BaseFragment {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView circleRecyclerView;
    private CircleAdapter circleAdapter;

    private ArrayList<AVObject> circles;   //圈子列表

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        smartRefreshLayout = mContentView.findViewById(R.id.circle_recommend_smart_refresh);
        circleRecyclerView = mContentView.findViewById(R.id.circle_recommend_recycler_view);

        circleAdapter = new CircleAdapter(R.layout.circle_layout_circlr_item);

        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        circleRecyclerView.addItemDecoration(decoration);
        circleRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        circleRecyclerView.setAdapter(circleAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestAllData();
            }
        });

        if (circles == null || circles.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void event(CircleEvent event) {
        smartRefreshLayout.autoRefresh();
    }

    private void requestAllData() {
        circles = new ArrayList<>();
        AVDbGlobal.getInstance().getAVDb().requestCommunity(null, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefreshLayout.finishRefresh();
                circles.addAll(list);
                refreshCircleView(circles);
            }
        });
        requestAttentionData();
        requestMyLikeData();
        requestCollectData();
    }

    private void requestAttentionData() {
        AVDbGlobal.getInstance().getAVDb().requestAttentionList(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    circleAdapter.setAttentionList(list);
                    circleAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }

    private void requestMyLikeData() {
        AVDbGlobal.getInstance().getAVDb().requestLikeByUser(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    circleAdapter.setMyLikeList(list);
                    circleAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }

    private void requestCollectData() {
        AVDbGlobal.getInstance().getAVDb().requestCollect(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    circleAdapter.setMyCollectionList(list);
                    circleAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }


    private void refreshCircleView(ArrayList<AVObject> attCircles) {
        if (attCircles == null || attCircles.isEmpty()) {

        } else {
            circleAdapter.replaceData(attCircles);
            circleAdapter.notifyDataSetChanged();
        }
    }
}
