package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;

@Route(path = ModelPathManager.main_editResume)
public class EditResumeActivity extends BaseTitleActivity {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("简历编辑")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(null)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_edit_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
