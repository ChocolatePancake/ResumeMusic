package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.resume.music.cn.R;
import com.resume.music.cn.adapter.PartAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.main_editParty;

/**
 * 我的活动
 */
@Route(path = ModelPathManager.main_myPartList)
public class MyPartyActivity extends BaseTitleActivity {
    private SmartRefreshLayout smartRefresh;
    private RecyclerView partyRecycler;
    private PartAdapter partAdapter;
    private LinearLayout noDataLayout;

    private List<AVObject> dataAVObjectList;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的活动")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setRightText("发布")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(main_editParty).navigation();
                    }
                });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_party;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        noDataLayout = mContentView.findViewById(R.id.not_data_layout);
        smartRefresh = mContentView.findViewById(R.id.smart_refresh_layout);
        partyRecycler = mContentView.findViewById(R.id.my_part_recycler);
        partAdapter = new PartAdapter(R.layout.layout_part_item);
        partyRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        partyRecycler.setAdapter(partAdapter);

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
                if (e != null) {
                    ToastUtil.show("请求异常,请检查网络");
                } else {
                    if (list != null && !list.isEmpty()) {
                        dataAVObjectList = list;
                    } else {
                        ToastUtil.show("您还没有填写简历");
                    }
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
