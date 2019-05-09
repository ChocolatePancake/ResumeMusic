package com.resume.music.cn.featuresAct;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.resume.music.cn.R;
import com.vise.utils.view.DialogUtil;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseActivity;
import tech.com.commoncore.plog;
import tech.com.commoncore.utils.DialogUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.MEDIA_TYPE_MUSIC;
import static tech.com.commoncore.avdb.AVDbManager.MEDIA_TYPE_VIDEO;
import static tech.com.commoncore.manager.ModelPathManager.main_recordVideo;
import static tech.com.commoncore.manager.ModelPathManager.main_videoPreview;

@Route(path = main_videoPreview)
public class VideoPreviewActivity extends BaseActivity implements View.OnClickListener {

    private String filePath;
    private String fileName;

    private JCVideoPlayerStandard videoPlayerStandard;

    private String videoName;

    @Override
    public int getContentLayout() {
        return R.layout.activity_video_preview;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(Bundle savedInstanceState) {
        setImmersiveWindowStyle();
        videoPlayerStandard = findViewById(R.id.video_player_standard);

        findViewById(R.id.video_studio_again).setOnClickListener(this);
        findViewById(R.id.video_studio_upload).setOnClickListener(this);

        filePath = getIntent().getStringExtra("videoPath");
        fileName = getIntent().getStringExtra("videoName");

        plog.paly("路径:" + filePath + "  名字" + fileName);

        videoPlayerStandard.setUp(filePath + "/" + fileName, JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN);
        videoPlayerStandard.startPlayLogic();
    }

    @Override
    protected void onDestroy() {
        videoPlayerStandard.releaseAllVideos();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.video_studio_upload) {
            if (videoName == null || videoName.isEmpty()) {
                showNameDialog();
                return;
            }
        }
        if (v.getId() == R.id.video_studio_again) {
            ARouter.getInstance().build(main_recordVideo).navigation();
            finish();
        }
    }

    private void showNameDialog() {
        final EditText editText = new EditText(mContext);
        int padding = (int) mContext.getResources().getDimension(R.dimen.dp_10);
        editText.setPadding(padding, padding, padding, padding);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setBackground(getResources().getDrawable(R.drawable.bg_dialog_edit));
        AlertDialog.Builder builder = DialogUtils.dialogBuilder(mContext, "请为视频命名", editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoName = editText.getText().toString();
                if (videoName == null || videoName.isEmpty()) {
                    showNameDialog();
                } else {
                    handlerUpDateVideo();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(false);
        builder.show();
    }

    private void handlerUpDateVideo() {
        if (AVUser.getCurrentUser() == null) {
            ToastUtil.show("您还没有登录,请先登录");
            return;
        }
        showLoading("正在上传");
        final AVFile avFile = AVGlobal.getInstance().getAVImpl().getAVFileByPath(filePath, fileName);
        avFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    hideLoading();
                    ToastUtil.show("文件上传失败,请检查网络");
                } else {
                    AVGlobal.getInstance().getAVImpl().addFile(avFile.getUrl(), 0, videoName, MEDIA_TYPE_VIDEO, "", new SaveCallback() {
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
}
