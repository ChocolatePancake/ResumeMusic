package com.leancloud.circle.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.leancloud.circle.R;
import com.vise.xsnow.event.BusManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.bus.CircleEvent;
import tech.com.commoncore.plog;
import tech.com.commoncore.utils.FileUtils;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.widget.photoPicker.MyPhotoPickerActivity;

public class PublishCircleActivity extends BaseTitleActivity implements EasyPermissions.PermissionCallbacks {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 2;

    private EditText contentEt;

    private ArrayList<String> localImgList;
    private ArrayList<String> imageUrl;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("发表圈子")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setRightText("发表")
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handlerPublishCircle();
                    }
                });
    }

    private void handlerPublishCircle() {
        plog.paly("点击了发表");
        if (localImgList == null || localImgList.isEmpty()) {
            plog.paly("没有图片,直接发送");
            publish("");
        } else {
            imageUrl = new ArrayList<>();
            for (String url : localImgList) {
                upImageFile(url);
            }
        }
    }

    private void publish(String imgList) {
        String content = contentEt.getText().toString();
        if (content.isEmpty()) {
            ToastUtil.show("内容不能为空哦");
            return;
        }
        plog.paly("开始真正的发送");
        showLoading("发表中");
        AVDbGlobal.getInstance().getAVDb().publishCommunity(imgList, content, "", new SaveCallback() {
            @Override
            public void done(AVException e) {
                hideLoading();
                if (e == null) {
                    ToastUtil.show("表发成功");
                    BusManager.getBus().post(new CircleEvent());
                    finish();
                } else {
                    ToastUtil.show("发表失败" + e.getMessage());
                }
            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_publish_cirlce;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentEt = findViewById(R.id.circle_content_et);
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
//            File takePhotoDir = new File(Config.PHOTO_PATH);
            Intent photoPickerIntent = new MyPhotoPickerActivity.IntentBuilder(this)
//                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
//                    .selectedPhotos(mPhotoLayout.getData()) // 当前已选中的图片路径集合
                    .maxChooseCount(3) // 图片选择张数的最大值
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tip_photo_permissions_request), PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            ToastUtil.show(R.string.tip_photo_permissions_denied);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            localImgList = MyPhotoPickerActivity.getSelectedPhotos(data);
        }
    }

    private void upImageFile(String fileUrl) {
        try {
            String fileName = FileUtils.splitFileName(fileUrl);
            final AVFile avFile = AVFile.withAbsoluteLocalPath(fileName, fileUrl);
            avFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        imageUrl.add(avFile.getUrl());
                        if (localImgList.size() == imageUrl.size()) {
                            String l = "";
                            for (String s : imageUrl) {
                                l = l + s + ",";
                            }
                            publish(l);
                        }
                    } else {
                        hideLoading();
                        imageUrl.clear();
                        ToastUtil.show("图片上传失败,请重新发布");
                    }
                }
            });
        } catch (FileNotFoundException e) {
            hideLoading();
            e.printStackTrace();
        }
    }
}
