package com.resume.music.cn.featuresAct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;

import java.io.File;

import tech.com.commoncore.avdb.AVDbManager;
import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;

import static tech.com.commoncore.manager.ModelPathManager.main_musicStudio;

@Route(path = main_musicStudio)
public class MusicStudioActivity extends BaseTitleActivity implements View.OnClickListener {

    private File musicStudioFile;

    @Override
    public void setTitleBar(TitleBarView titleBar) {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_music_studio;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        findViewById(R.id.music_studio_again).setOnClickListener(this);
        findViewById(R.id.music_studio_upload).setOnClickListener(this);
        findViewById(R.id.music_studio_save).setOnClickListener(this);

        String fileUrl = getIntent().getStringExtra("musicUrl");
        musicStudioFile = new File(fileUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_studio_again:

                break;
            case R.id.music_studio_upload:

                break;
            case R.id.music_studio_save:
                break;
        }
    }

    private void handlerMusicUpload(String alias) {
        AVGlobal.getInstance().getAVImpl().uploadMediaFiles(musicStudioFile, alias, AVDbManager.MEDIA_TYPE_MUSIC);
    }
}
