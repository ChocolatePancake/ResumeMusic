package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseTitleActivity;

public class ResumeDetailsActivity extends BaseTitleActivity {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("简历详情")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_resume_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
