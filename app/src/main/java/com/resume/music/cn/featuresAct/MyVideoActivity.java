package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;

@Route(path = ModelPathManager.main_myVideo)
public class MyVideoActivity extends BaseTitleActivity {
    private LinearLayout notDataLayout, haveDataLayout;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的视频")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setRightText("编辑")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(ModelPathManager.main_recordVideo).navigation();
                    }
                });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_video;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        notDataLayout = findViewById(R.id.not_data_layout);
        haveDataLayout = findViewById(R.id.have_data_layout);
    }
}
