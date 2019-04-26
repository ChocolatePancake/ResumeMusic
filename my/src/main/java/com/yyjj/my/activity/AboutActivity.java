package com.yyjj.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.yyjj.my.BuildConfig;
import com.yyjj.my.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.DataUtils;

import static tech.com.commoncore.manager.ModelPathManager.my_aboutPath;

/**
 * Anthor:NiceWind
 * Time:2019/3/7
 * Desc:The ladder is real, only the climb is all.
 */

@Route(path = my_aboutPath)
public class AboutActivity extends BaseTitleActivity {
    TextView versionCodeText, introductionText;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("关于我们")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.my_activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        versionCodeText = findViewById(R.id.version_code_text);
        introductionText = findViewById(R.id.introduction_text);
        setData();
    }
    
    private void setData() {
        versionCodeText.setText(BuildConfig.VERSION_NAME);
        String introduction = getResources().getString(R.string.my_string_introduction);
        introductionText.setText(DataUtils.toSBC(introduction));
    }
}
