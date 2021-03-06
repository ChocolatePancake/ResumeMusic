package com.resume.music.cn.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.resume.music.cn.R;
import com.resume.music.cn.adapter.PartAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleFragment;
import tech.com.commoncore.plog;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.PARTY_USER;
import static tech.com.commoncore.avdb.AVDbManager.TABLE_RESUME;
import static tech.com.commoncore.avdb.AVDbManager.TARGET_AVUSER;
import static tech.com.commoncore.avdb.AVDbManager.USER_NICK_NAME;

public class PartFragment extends BaseTitleFragment {
    private SmartRefreshLayout smartRefresh;
    private RecyclerView partRecycler;
    private PartAdapter partAdapter;
    private LinearLayout noDataLayout;

    private List<AVObject> dataAVObjectList;

    public static PartFragment newInstance() {
        PartFragment fragment = new PartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("活动一览")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(null)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_part;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        noDataLayout = mContentView.findViewById(R.id.not_data_layout);
        smartRefresh = mContentView.findViewById(R.id.smart_refresh_layout);
        partRecycler = mContentView.findViewById(R.id.part_list_recycler);
        partAdapter = new PartAdapter(R.layout.layout_part_item);
        partRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        partRecycler.setAdapter(partAdapter);

        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestData();
            }
        });

        if (dataAVObjectList == null || dataAVObjectList.isEmpty()) {
            smartRefresh.autoRefresh();
        }
    }

    private void requestData() {
        AVGlobal.getInstance().getAVImpl().requestPrat(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefresh.finishRefresh();
                if (e == null) {
                    if (list != null && !list.isEmpty()) {
                        dataAVObjectList = list;
                    } else {
                        ToastUtil.show("没有找到哦");
                    }
                } else {
                    ToastUtil.show("请求异常,请检查网络");
                }
                upDateView();
            }
        });
    }

    private void upDateView() {
        if (dataAVObjectList == null || dataAVObjectList.isEmpty()) {
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            noDataLayout.setVisibility(View.GONE);
            partAdapter.replaceData(dataAVObjectList);
            partAdapter.notifyDataSetChanged();
        }
    }
}
