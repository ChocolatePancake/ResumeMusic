package com.leancloud.circle.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.leancloud.circle.R;
import com.leancloud.circle.adapter.LikeAndCollectionAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.plog;
import tech.com.commoncore.view.SpaceItemDecoration;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.manager.ModelPathManager.circle_like;

@Route(path = circle_like)
public class MyLikeActivity extends BaseTitleActivity {

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private LikeAndCollectionAdapter andCollectionAdapter;
    private TextView noDataTx;

    private List<AVObject> communityAVObjectList = new ArrayList<>();

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的点赞")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_like;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.my_like_recycler);
        smartRefreshLayout = findViewById(R.id.circle_recommend_smart_refresh);
        noDataTx = findViewById(R.id.no_data_value);

        andCollectionAdapter = new LikeAndCollectionAdapter(R.layout.circle_layout_like_and_collection_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int space = (int) getResources().getDimension(R.dimen.dp_10);
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        recyclerView.setAdapter(andCollectionAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestLikeData();
            }
        });

        if (communityAVObjectList.isEmpty()) {
            smartRefreshLayout.autoRefresh();
        }
    }

    private void requestLikeData() {

        communityAVObjectList.clear();
        AVDbGlobal.getInstance().getAVDb().requestLikeByUser(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    requestCommunityData(list);
                } else {
                    smartRefreshLayout.finishRefresh();
                    noDataTx.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void requestCommunityData(final List<AVObject> likeAVObject) {
        AVDbGlobal.getInstance().getAVDb().requestCommunity(null, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefreshLayout.finishRefresh();
                if (e == null) {
                    for (AVObject communityAVObject : list) {
                        if (contain(communityAVObject, likeAVObject)) {
                            communityAVObjectList.add(communityAVObject);
                        }
                    }
                    requestAttentionData();
                    if (communityAVObjectList.size()>0){
                        noDataTx.setVisibility(View.GONE);
                    }else {
                        noDataTx.setVisibility(View.VISIBLE);
                    }
                    andCollectionAdapter.replaceData(communityAVObjectList);
                    andCollectionAdapter.notifyDataSetChanged();
                } else {
                    noDataTx.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void requestAttentionData() {
        AVDbGlobal.getInstance().getAVDb().requestAttentionList(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    andCollectionAdapter.setAttentionList(list);
                    andCollectionAdapter.notifyDataSetChanged();
                } else {
                    plog.paly("关注返回错误");
                }
            }
        });
    }

    private boolean contain(AVObject communityAVObject, List<AVObject> likeAVObjectList) {
        for (AVObject likeAVObject : likeAVObjectList) {
            String communityId = (String) likeAVObject.get(LIKE_COMMUNITY);
            if (communityId != null && !communityId.isEmpty() && communityId.equals(communityAVObject.getObjectId())) {
                return true;
            }
        }
        return false;
    }
}
