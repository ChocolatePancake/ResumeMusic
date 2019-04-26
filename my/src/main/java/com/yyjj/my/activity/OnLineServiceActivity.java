package com.yyjj.my.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.radius.RadiusTextView;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyjj.my.R;
import com.yyjj.my.adapter.OnLineServiceListAdapter;
import com.yyjj.my.entity.OnLineServiceMsg;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.constant.BussConstant;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.DisplayUtils;
import tech.com.commoncore.utils.OnNoDoubleClickListener;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.my_onLineServerPath;

/**
 * Desc:在线客服
 * Version:1.0
 */
@Route(path = my_onLineServerPath)
public class OnLineServiceActivity extends BaseTitleActivity {

    RecyclerView recyclerView;
    EditText etContent;
    RadiusTextView tvSend;

    private OnLineServiceListAdapter mAdapter;

    protected int mDefaultPage = 0;
    protected int mDefaultPageSize = 10;

    private View decorView;
    private View contentView;


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("在线客服")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.my_activity_online_service;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        adaptiveKeyboardPushUp();
        recyclerView = findViewById(R.id.recyclerView);
        etContent = findViewById(R.id.et_content);
        tvSend = findViewById(R.id.tv_send);

        mAdapter = new OnLineServiceListAdapter();
        //设置上拉加载
        mAdapter.setUpFetchEnable(true);

        mAdapter.setStartUpFetchPosition(2);
        mAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                startUpFetch();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        loadData(0);

        tvSend.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AVUser.getCurrentUser() == null) {
                    ARouter.getInstance().build("/login/login").navigation();

                } else {
                    String content = etContent.getText().toString().trim();
                    if (DataUtils.isEmptyString(content)) {
                        ToastUtil.show("亲,您还没输入内容...");
                        return;
                    }
                    sendMessage(content);
                }
            }
        });
    }

    private void adaptiveKeyboardPushUp() {
        decorView = getWindow().getDecorView();
        contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };
    }

    private void startUpFetch() {
        mDefaultPage++;
        mAdapter.setUpFetching(true);

        loadData(mDefaultPage);
    }

    private void sendMessage(final String content) {
//        AVObject user = AVObject.createWithoutData(BussConstant.TABLE_USER, userInfo.id);
        AVObject avObject = new AVObject(BussConstant.TABLE_ONLINE_SERVICE);
        avObject.put(BussConstant.CONTENT, content);
        avObject.put(BussConstant.USER, AVUser.getCurrentUser());
        avObject.put(BussConstant.IS_REPLY, false);
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    etContent.setText("");
                    DisplayUtils.hideSoftInput(mContext, etContent);
                    mDefaultPage = 0;
                    loadData(mDefaultPage);

                    if (content.contains("您好") || content.contains("你好")) {
                        autoReply("您好！我是客服‘小盈’,很高兴为您服务。");
                    } else if (content.contains("在？") || content.contains("在?") || content.contains("在吗") || content.contains("在不")) {
                        autoReply("在的哟,请问有什么能帮到您吗？");
                    }
                } else {
                    ToastUtil.show("消息发送失败");
                }
            }
        });
    }

    private void autoReply(String reply) {
//        AVObject user = AVObject.createWithoutData(BussConstant.TABLE_USER, userInfo.id);
        AVObject avObject = new AVObject(BussConstant.TABLE_ONLINE_SERVICE);
        avObject.put(BussConstant.CONTENT, reply);
        avObject.put(BussConstant.USER, AVUser.getCurrentUser());
        avObject.put(BussConstant.IS_REPLY, true);
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mDefaultPage = 0;
                    loadData(mDefaultPage);
                }
            }
        });
    }

    private void loadData(int page) {
        mAdapter.setUpFetching(false);

//        AVObject user = AVObject.createWithoutData(BussConstant.TABLE_USER, userInfo.id);
        AVQuery<AVObject> query = new AVQuery<>(BussConstant.TABLE_ONLINE_SERVICE);
        query.include(BussConstant.USER);
        query.setLimit(mDefaultPageSize);
        query.setSkip(page * mDefaultPageSize);
        query.orderByDescending("createdAt");
        query.whereEqualTo(BussConstant.USER, AVUser.getCurrentUser());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null && list != null && list.size() > 0) {
                    List<OnLineServiceMsg> msgs = new ArrayList<>();
                    for (AVObject object : list) {
                        boolean isReply = object.getBoolean(BussConstant.IS_REPLY);
                        AVUser avUser = object.getAVUser(BussConstant.USER);
                        String content = object.getString(BussConstant.CONTENT);
                        long time = object.getCreatedAt().getTime();

                        msgs.add(0, new OnLineServiceMsg(time, avUser, content, isReply));
                    }

                    if (mDefaultPage == 0) {
                        mAdapter.replaceData(msgs);
                        if (mAdapter.getItemCount() > 0) {
                            recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                        }
                    } else {
                        mAdapter.addData(0, msgs);
                    }
                    mAdapter.setUpFetchEnable(true);
                } else {
//                    ToastUtil.show("获取数据出错,请检查网络后重试");
                    mAdapter.setUpFetchEnable(true);
                    mAdapter.setEmptyView(R.layout.layout_status_layout_manager_empty, recyclerView);
                }
            }
        });
    }

}
