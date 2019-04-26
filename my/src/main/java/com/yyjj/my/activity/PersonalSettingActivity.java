package com.yyjj.my.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.vise.xsnow.event.BusManager;
import com.yyjj.my.R;
import com.yyjj.my.db.AVDbManager;

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
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.FileUtils;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.widget.photoPicker.MyPhotoPickerActivity;

import static tech.com.commoncore.manager.ModelPathManager.my_settingPath;

/**
 * Desc:
 */
@Route(path = my_settingPath)
public class PersonalSettingActivity extends BaseTitleActivity implements EasyPermissions.PermissionCallbacks {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 2;

    private ImageView ivHeadIcon;
    private TextView tvNickname, tvPhone, tvSinge;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("个人设置")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.my_activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerOnClick(v.getId());
            }
        };
        ivHeadIcon = findViewById(R.id.iv_head_icon);
        tvNickname = findViewById(R.id.tv_nickname);
        tvPhone = findViewById(R.id.tv_phone);
        tvSinge = findViewById(R.id.tv_singe);

        ivHeadIcon.setOnClickListener(clickListener);
        findViewById(R.id.ll_head_icon).setOnClickListener(clickListener);
        findViewById(R.id.ll_head_nick).setOnClickListener(clickListener);
        findViewById(R.id.ll_phone).setOnClickListener(clickListener);
        findViewById(R.id.ll_singe).setOnClickListener(clickListener);

        setUserInfo();
    }

    private void setUserInfo() {
        AVUser avUser = AVUser.getCurrentUser();
        String name = "";
        String headUrl = "";
        String number = "";
        String nikeName = "";
        String sign = "这个家伙很懒,什么也没留下";
        if (avUser == null) {
            name = "立即登录";
            nikeName = "立即登录";
            sign = "您还没有登录哦";
        } else {
            name = avUser.getUsername();
            headUrl = (String) avUser.get(AVDbManager.AUTHOR_ICON);
            number = avUser.getMobilePhoneNumber();
            nikeName = (String) avUser.get(AVDbManager.USER_NICKE_NAME);
            String s = (String) avUser.get(AVDbManager.USER_SIGN);
            if (s == null || s.isEmpty()) {
            } else {
                sign = s;
            }
        }
        GlideManager.loadCircleImg(headUrl, ivHeadIcon, R.mipmap.ic_default_head);
        tvNickname.setText(nikeName);
        tvPhone.setText(number);
        tvSinge.setText(sign);
    }

    private void handlerOnClick(int id) {
        if (id == R.id.ll_head_icon) {
            //nothing
        } else if (id == R.id.iv_head_icon) {
            choicePhotoWrapper();
        } else if (id == R.id.ll_head_nick) {
            showChangeNicknameDialog();
        } else if (id == R.id.ll_phone) {
            //nothing
        } else if (id == R.id.ll_singe) {
            FastUtil.startActivity(this, SingeInputActivity.class);
        }
    }

    //---------------------------------------------更改头像------------------------------------
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
            upLoadPic(MyPhotoPickerActivity.getSelectedPhotos(data).get(0)); //保存图片
        }
    }

    //上传图片
    private void upLoadPic(String picUrl) {
        showLoading("正在上传...");
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
        GlideManager.loadCircleImg(url, ivHeadIcon, R.mipmap.ic_default_head);
        BusManager.getBus().post(new PersonalChangeEvent());
    }


    //----------------------更改昵称------------------
    private EditText mEditTextNickName;

    private void showChangeNicknameDialog() {
        LinearLayout contentView = (LinearLayout) mContext.getLayoutInflater().inflate(R.layout.my_layout_edit, null);
        mEditTextNickName = contentView.findViewById(R.id.dialog_name_input_et);

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("修改昵称")
                .setView(contentView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        changeNick(mEditTextNickName.getText());
                    }
                }).create();
        dialog.show();
        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setTextColor(Color.BLACK);
    }

    private void changeNick(Editable nickName) {
        if (DataUtils.isEmpty(nickName)) {
            ToastUtil.show("昵称不能为空");
            return;
        }
        String afterName = nickName.toString().trim();
        if (afterName.length() < 1 || afterName.length() > 12) {
            ToastUtil.show("请输入4-12个字符");
            return;
        }
        AVUser user = AVUser.getCurrentUser();
        user.put(AVDbManager.USER_NICKE_NAME, afterName);
        user.saveInBackground();
        tvNickname.setText(afterName);
        BusManager.getBus().post(new PersonalChangeEvent());
    }
}
