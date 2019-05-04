package com.leancloud.login.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.aries.ui.widget.progress.UIProgressDialog;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.leancloud.login.R;
import tech.com.commoncore.avdb.AVDbManager;
import com.vise.xsnow.event.BusManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import tech.com.commoncore.Config;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.event.PersonalChangeEvent;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.FileUtils;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.widget.photoPicker.MyPhotoPickerActivity;

import static tech.com.commoncore.manager.ModelPathManager.login_personalPath;

/**
 * Time:2018/12/13
 * Desc:
 */
@Route(path = login_personalPath)
public class PersonalActivity extends BaseTitleActivity implements EasyPermissions.PermissionCallbacks {
    private RelativeLayout rlHead;
    private ImageView ivIcon;
    private EditText etTitle;
    private RadioGroup radiogroup;
    private TextView tvPhone;
    private RadioButton radiobtn1;
    private RadioButton radiobtn2;
    private RadioButton radiobtn3;
    private Button btn;

    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private UIProgressDialog mProgressDialog;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("个人资料")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        rlHead = findViewById(R.id.rl_head);
        ivIcon = findViewById(R.id.iv_icon);
        etTitle = findViewById(R.id.et_title);
        radiogroup = findViewById(R.id.radiogroup);
        tvPhone = findViewById(R.id.tv_phone);
        btn = findViewById(R.id.btn);

        radiobtn1 = findViewById(R.id.radiobtn1);
        radiobtn2 = findViewById(R.id.radiobtn2);
        radiobtn3 = findViewById(R.id.radiobtn3);
        initViews();
    }

    private void initViews() {
        etTitle.setText(AVUser.getCurrentUser().getString(AVDbManager.USER_NICK_NAME));
        GlideManager.loadCircleImg(AVUser.getCurrentUser().getString(AVDbManager.USER_HEAD_ICON), ivIcon, R.mipmap.ic_default_head);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choicePhotoWrapper();
            }
        });

    }

    private void saveInfo() {
        if (DataUtils.isEmpty(etTitle.getText())) {
            ToastUtil.show("用户名不能为空");
            return;
        }

        String name = etTitle.getText().toString().trim();
        String male = getMale();

        BusManager.getBus().post(new PersonalChangeEvent());
        ToastUtil.show("修改成功");
        finish();
    }

    private String getMale() {

        if (radiogroup.getCheckedRadioButtonId() == R.id.radiobtn1) {
            return "男";
        } else if (radiogroup.getCheckedRadioButtonId() == R.id.radiobtn2) {
            return "女";

        } else {
            return "";
        }
    }

    //-------------------------------------图片选择器开始--------------------------------------
    //上传图片
    private void upLoadPic(String picUrl) {
        showLoading();
        try {
            String fileName = FileUtils.splitFileName(picUrl);
            final AVFile avFile = AVFile.withAbsoluteLocalPath(fileName, picUrl);
            avFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    hideLoading();
                    if (e == null) {
                        updateIcon(avFile.getUrl());
                    } else {
                        ToastUtil.show("图片上传失败,请重新发布");
                    }
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {

                }
            });
        } catch (FileNotFoundException e) {
            hideLoading();
            e.printStackTrace();

        }
    }

    private void updateIcon(String url) {
        AVUser user = AVUser.getCurrentUser();
        user.put(AVDbManager.USER_HEAD_ICON, url);
        user.saveInBackground();
        GlideManager.loadCircleImg(url, ivIcon);
        BusManager.getBus().post(new PersonalChangeEvent());
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Config.PHOTO_PATH);
            Intent photoPickerIntent = new MyPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
//                    .selectedPhotos(mPhotoLayout.getData()) // 当前已选中的图片路径集合
                    .maxChooseCount(1) // 图片选择张数的最大值
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
//            mPhotoLayout.setData(MyPhotoPickerActivity.getSelectedPhotos(data));
            upLoadPic(MyPhotoPickerActivity.getSelectedPhotos(data).get(0)); //保存图片

        }
    }


    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new UIProgressDialog.WeBoBuilder(this)
                    .setMessage("正在发布...")
                    .setCancelable(false)
                    .create();
        }
        mProgressDialog.setDimAmount(0.6f)
                .show();
    }

    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }
    //-------------------------------------图片选择器结束----------------------------------------
}
