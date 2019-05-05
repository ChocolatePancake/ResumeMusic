package com.resume.music.cn.featuresAct;

import android.os.Bundle;

import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseActivity;

public class VideoPlayActivity extends BaseActivity {

    private String id;

    @Override
    public int getContentLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("videoFileId");
    }
}
