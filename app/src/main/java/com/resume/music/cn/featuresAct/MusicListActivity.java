package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.resume.music.cn.R;

import java.util.Arrays;
import java.util.List;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.plog;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.manager.ModelPathManager.main_musicList;

@Route(path = main_musicList)
public class MusicListActivity extends BaseTitleActivity {

    private int type = 0;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("音乐试听")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_music_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("musicType", 0);
        plog.paly("类型是:" + type);

        AVQuery<AVObject> typeQuery = new AVQuery(TABLE_MEDIA);
        typeQuery.whereEqualTo(MEDIA_FILE_TYPE, MEDIA_TYPE_MUSIC);

        AVQuery<AVObject> gradeQuery = new AVQuery(TABLE_MEDIA);
        gradeQuery.whereEqualTo(MEDIA_GRADE, type);

        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(typeQuery, gradeQuery));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    
                }
            }
        });
    }
}
