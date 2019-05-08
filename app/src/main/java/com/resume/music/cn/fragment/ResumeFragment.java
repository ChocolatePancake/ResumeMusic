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
import com.avos.avoscloud.FindCallback;
import com.resume.music.cn.R;
import com.resume.music.cn.adapter.ResumeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import tech.com.commoncore.base.BaseTitleFragment;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;

/**
 * 简历表界面
 */
public class ResumeFragment extends BaseTitleFragment {

    private SmartRefreshLayout smartRefresh;
    private RecyclerView resumeRecycler;
    private ResumeAdapter resumeAdapter;
    private LinearLayout noDataLayout;

    private List<AVObject> dataAVObjectList;


    public static ResumeFragment newInstance() {
        ResumeFragment fragment = new ResumeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("简历查看")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(null)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        noDataLayout = mContentView.findViewById(R.id.not_data_layout);
        smartRefresh = mContentView.findViewById(R.id.smart_refresh_layout);
        resumeRecycler = mContentView.findViewById(R.id.resume_list_recycler);
        resumeAdapter = new ResumeAdapter(R.layout.layout_resume_item);
        resumeRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        resumeRecycler.setAdapter(resumeAdapter);

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
        AVQuery<AVObject> avQuery = new AVQuery<>(TABLE_RESUME);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefresh.finishRefresh();
                if (e == null) {
                    if (list != null && !list.isEmpty()) {
                        dataAVObjectList = list;
                    } else {
                        ToastUtil.show("没有搜索到结果哦");
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
            resumeAdapter.replaceData(dataAVObjectList);
            resumeAdapter.notifyDataSetChanged();
        }
    }
}
