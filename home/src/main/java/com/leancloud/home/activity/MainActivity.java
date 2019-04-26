package com.leancloud.home.activity;

import android.os.Bundle;

import com.leancloud.home.R;
import com.leancloud.home.fragment.HomeFragment;

import tech.com.commoncore.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.home_activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, HomeFragment.newInstance()).commit();
    }
}
