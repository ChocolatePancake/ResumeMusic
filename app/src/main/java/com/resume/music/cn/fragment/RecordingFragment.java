package com.resume.music.cn.fragment;

import android.os.Bundle;
import android.view.View;

import com.resume.music.cn.R;
import com.resume.music.cn.recordingModel.RecordService;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.plog;

public class RecordingFragment extends BaseFragment {

    public static RecordingFragment newInstance() {
        RecordingFragment fragment = new RecordingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_recording;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mContentView.findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plog.paly("点击了录音");
                RecordService.getInstance().startRecord();
            }
        });
        mContentView.findViewById(R.id.stop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plog.paly("点击了停止录音");
                RecordService.getInstance().stopRecord();
            }
        });
        mContentView.findViewById(R.id.play_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
