package com.resume.music.cn.fragment;

import android.os.Bundle;
import android.view.View;

import com.resume.music.cn.R;
import com.resume.music.cn.featuresAct.RecordVideoActivity;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.utils.FastUtil;

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
        mContentView.findViewById(R.id.to_video_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastUtil.startActivity(mContext, RecordVideoActivity.class);
            }
        });
    }

}
