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
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.resume.music.cn.R;
import com.resume.music.cn.adapter.PlanAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.view.SpaceItemDecoration;

import static tech.com.commoncore.manager.ModelPathManager.login_loginPath;
import static tech.com.commoncore.manager.ModelPathManager.main_editPlan;
import static tech.com.commoncore.manager.ModelPathManager.main_myPlanList;

/**
 * 我的策划
 */
@Route(path = main_myPlanList)
public class MyPlanActivity extends BaseTitleActivity {

    private SmartRefreshLayout smartRefresh;
    private RecyclerView recyclerView;
    private PlanAdapter planAdapter;
    private LinearLayout noDataLayout;

    private List<AVObject> dataAVObjectList;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的策划")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setRightText("编辑")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(main_editPlan).navigation();
                    }
                });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_plan;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        noDataLayout = mContentView.findViewById(R.id.not_data_layout);
        smartRefresh = mContentView.findViewById(R.id.smart_refresh_layout);
        recyclerView = mContentView.findViewById(R.id.my_plan_recycler);

        if (AVUser.getCurrentUser() == null) {
            ARouter.getInstance().build(login_loginPath).navigation();
            finish();
            return;
        }

        planAdapter = new PlanAdapter(R.layout.layout_plan_item);
        int space = (int) getResources().getDimension(R.dimen.dp_10);
        SpaceItemDecoration decoration = new SpaceItemDecoration(space);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(planAdapter);

        planAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

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
        AVGlobal.getInstance().getAVImpl().requestPlan(AVUser.getCurrentUser().getObjectId(), new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefresh.finishRefresh();
                if (e != null) {
                    ToastUtil.show("请求异常,请检查网络");
                } else {
                    if (list != null && !list.isEmpty()) {
                        dataAVObjectList = list;
                    } else {
                        ToastUtil.show("搜索结果为空");
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
            planAdapter.replaceData(dataAVObjectList);
            planAdapter.notifyDataSetChanged();
        }
    }
}
