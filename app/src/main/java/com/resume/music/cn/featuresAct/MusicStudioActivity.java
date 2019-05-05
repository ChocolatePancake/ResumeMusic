package com.resume.music.cn.featuresAct;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.resume.music.cn.R;

import java.io.FileNotFoundException;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.FileUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.manager.ModelPathManager.*;

@Route(path = main_musicStudio)
public class MusicStudioActivity extends BaseTitleActivity implements View.OnClickListener {

    private EditText nameEt, contentEt;

    private String filePath = "";
    private int grade = 0;
    private String name = "";
    private String content = "";

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

        filePath = getIntent().getStringExtra("musicFilePath");
        grade = getIntent().getIntExtra("musicGrade", 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_studio_again:
                handlerMusicAgain();
                break;
            case R.id.music_studio_upload:
                handlerMusicUpload();
                break;
            case R.id.music_studio_save:
                handlerMusicSave();
                break;
        }
    }

    private void handlerMusicAgain() {
        FileUtils.deleteDir(filePath);
        ARouter.getInstance().build(main_recordMusic).navigation();
        finish();
    }

    private void handlerMusicUpload() {
        showLoading();
        try {
            String fileName = FileUtils.splitFileName(filePath);
            final AVFile avFile;
            avFile = AVFile.withAbsoluteLocalPath(fileName, filePath);
            avFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e != null) {
                        hideLoading();
                        ToastUtil.show("文件上传失败,请检查网络");
                    } else {
                        AVGlobal.getInstance().getAVImpl().uploadMediaFiles(FileUtils.getFileByPath(filePath), new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e != null) {
                                    hideLoading();
                                    ToastUtil.show("上传失败.请检查网络后重试");
                                } else {
                                    AVObject avObject = new AVObject(TABLE_MEDIA);
                                    avObject.put(MEDIA_USER, AVUser.getCurrentUser().getObjectId());
                                    avObject.put(MEDIA_FILE, avFile.getUrl());
                                    avObject.put(MEDIA_GRADE, grade);
                                    avObject.put(MEDIA_FILE_NAME, name);
                                    avObject.put(MEDIA_FILE_TYPE, MEDIA_TYPE_MUSIC);
                                    avObject.put(MEDIA_CONTENT, content);
                                    avObject.put(MEDIA_LIKE_COUNT, 0);
                                    avObject.put(MEDIA_COMMENT_COUNT, 0);
                                    avObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                ToastUtil.show("上传完毕");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handlerMusicSave() {
        //
    }
}
