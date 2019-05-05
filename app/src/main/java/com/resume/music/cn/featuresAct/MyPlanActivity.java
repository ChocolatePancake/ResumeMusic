package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;

import static tech.com.commoncore.manager.ModelPathManager.main_myPlanList;

/**
 * 我的策划
 */
@Route(path = main_myPlanList)
public class MyPlanActivity extends BaseTitleActivity {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的策划")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_plan;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}