package com.yyjj.my.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.aries.ui.view.title.TitleBarView;
import com.yyjj.my.R;

import tech.com.commoncore.base.BaseTitleActivity;

public class NotificationActivity extends BaseTitleActivity {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("消息通知")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
