package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.resume.music.cn.R;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.plog;
import tech.com.commoncore.utils.FileUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.manager.ModelPathManager.*;

@Route(path = main_musicStudio)
public class MusicStudioActivity extends BaseTitleActivity implements View.OnClickListener {

    private EditText nameEt, contentEt;

    private String filePath = "";
    private String fileName = "";
    private int grade = 0;
    private String name = "";
    private String content = "";

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("音乐上传")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgColor(getResources().getColor(R.color.colorStudioBg))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_music_studio;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        nameEt = findViewById(R.id.music_studio_name_ed);
        contentEt = findViewById(R.id.music_studio_content_ed);
        findViewById(R.id.music_studio_again).setOnClickListener(this);
        findViewById(R.id.music_studio_upload).setOnClickListener(this);
        findViewById(R.id.music_studio_save).setOnClickListener(this);

        filePath = getIntent().getStringExtra("musicFilePath");
        fileName = getIntent().getStringExtra("musicFileName");
        plog.paly("路径:" + filePath + "   名称:" + fileName);
        grade = getIntent().getIntExtra("musicGrade", GRADE_80_100);
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
        name = nameEt.getText().toString();
        content = contentEt.getText().toString();
        if (name.isEmpty()) {
            ToastUtil.show("音乐名不能为空");
            return;
        }

        showLoading();
        final AVFile avFile = AVGlobal.getInstance().getAVImpl().getAVFileByPath(filePath, fileName);
        avFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    hideLoading();
                    ToastUtil.show("文件上传失败,请检查网络");
                } else {
                    AVGlobal.getInstance().getAVImpl().addFile(avFile.getUrl(), grade, name, MEDIA_TYPE_MUSIC, content, new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            hideLoading();
                            if (e == null) {
                                ToastUtil.show("上传完毕");
                            } else {
                                ToastUtil.show("上传失败,请检查网络后重试");
                            }
                        }
                    });
                }
            }
        });
    }

    private void handlerMusicSave() {
        //
    }
}
