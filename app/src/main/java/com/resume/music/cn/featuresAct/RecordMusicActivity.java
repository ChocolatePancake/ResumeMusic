package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;
import com.resume.music.cn.recordingModel.RecordService;

import tech.com.commoncore.base.BaseTitleActivity;

import static tech.com.commoncore.manager.ModelPathManager.main_musicStudio;
import static tech.com.commoncore.manager.ModelPathManager.main_recordMusic;

@Route(path = main_recordMusic)
public class RecordMusicActivity extends BaseTitleActivity {
    private TextView startRecordTx;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("音乐录制")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_record_music;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerOnClickListener(v.getId());
            }
        };
        startRecordTx = findViewById(R.id.record_music_start_tx);

        findViewById(R.id.record_music_start_tx).setOnClickListener(click);
        findViewById(R.id.record_music_pause).setOnClickListener(click);
        findViewById(R.id.record_music_complete).setOnClickListener(click);
        findViewById(R.id.record_music_again).setOnClickListener(click);
    }

    private void handlerOnClickListener(int id) {
        if (id == R.id.record_music_start_tx)
            handlerRecordStart();
        if (id == R.id.record_music_pause)//暂停
            handlerRecordPause();
        if (id == R.id.record_music_complete) //完成
            handlerRecordComplete();
        if (id == R.id.record_music_again) //重录
            handlerRecordAgain();
    }

    private void handlerRecordStart() {
        startRecordTx.setVisibility(View.GONE);
        RecordService.getInstance().startRecord();
    }

    private void handlerRecordPause() {

    }

    private void handlerRecordComplete() {
        RecordService.getInstance().stopRecord();
        ARouter.getInstance().build(main_musicStudio).navigation();
        finish();
    }

    private void handlerRecordAgain() {
        if (RecordService.getInstance().isRecording()) {
            RecordService.getInstance().stopRecord();
            RecordService.getInstance().startRecord();
        } else {
            RecordService.getInstance().startRecord();
        }
    }
}
