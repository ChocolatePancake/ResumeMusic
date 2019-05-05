package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;

import static tech.com.commoncore.manager.ModelPathManager.main_myPlanList;

@Route(path = ModelPathManager.main_planList)
public class PlanListActivity extends BaseTitleActivity {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("策划任务")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setRightText("我发布的")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(main_myPlanList).navigation();
                    }
                });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_plan_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
