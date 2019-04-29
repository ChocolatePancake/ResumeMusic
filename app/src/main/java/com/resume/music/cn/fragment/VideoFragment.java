package com.resume.music.cn.fragment;

import android.os.Bundle;

import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseFragment;

public class VideoFragment extends BaseFragment {

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_viode;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

}
