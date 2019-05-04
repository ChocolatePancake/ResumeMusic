package com.resume.music.cn.fragment;

import android.os.Bundle;

import com.resume.music.cn.R;

import tech.com.commoncore.base.BaseFragment;

public class MonadFragment extends BaseFragment {

    public static MonadFragment newInstance() {
        MonadFragment fragment = new MonadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_monad;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

}
