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

import static tech.com.commoncore.avdb.AVDbManager.ATTENTION_OBSERVED_USER;

/**
 * 关注界面
 */
public class AttentionFragment extends BaseFragment {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView circleRecyclerView;
    private CircleAdapter circleAdapter;

    private ArrayList<String> attentionUserList; //关注用户的列表
    private ArrayList<AVObject> attentionCircles;   //圈子列表

    public static AttentionFragment newInstance() {
        AttentionFragment fragment = new AttentionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_attention;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        smartRefreshLayout = mContentView.findViewById(R.id.circle_attention_smart_refresh);
        circleRecyclerView = mContentView.findViewById(R.id.circle_attention_recycler_view);

        circleAdapter = new CircleAdapter(R.layout.circle_layout_circlr_item);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        circleRecyclerView.addItemDecoration(decoration);
        circleRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        circleRecyclerView.setAdapter(circleAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestAttentionData();
            }
        });

        if (attentionCircles == null || attentionCircles.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void event(CircleEvent event) {
        smartRefreshLayout.autoRefresh();
    }

    private void requestAttentionData() {
        attentionUserList = new ArrayList<>();
        attentionCircles = new ArrayList<>();
        AVDbGlobal.getInstance().getAVDb().requestAttentionList(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    handlerRequestAttentionSuccess(list);
                } else {
                    smartRefreshLayout.finishRefresh();
                }
            }
        });
        requestMyLikeData();
        requestCollectData();
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

    private void handlerRequestAttentionSuccess(List<AVObject> list) {
        if (list == null || list.isEmpty()) {
            smartRefreshLayout.finishRefresh();
            return;
        }
        for (AVObject avObject : list) {
            String id = (String) avObject.get(ATTENTION_OBSERVED_USER);
            plog.paly(id);
            attentionUserList.add(id);
        }
        circleAdapter.setAttentionList(list);
        String[] strings = new String[attentionUserList.size()];
        attentionUserList.toArray(strings);
        AVDbGlobal.getInstance().getAVDb().requestCommunity(strings, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefreshLayout.finishRefresh();
                if (e == null) {
                    attentionCircles.addAll(list);
                    refreshCircleView(attentionCircles);
                } else {
                    refreshCircleView(null);
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
