package com.yyjj.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.yyjj.my.R;

import tech.com.commoncore.avdb.AVDbManager;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.ToastUtil;

public class SingeInputActivity extends BaseTitleActivity {

    private EditText singeInputEt;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("编辑签名")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setRightText("确定")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputSinge();
                    }
                })
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_singe_input;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        singeInputEt = findViewById(R.id.singe_input_et);
    }

    private void inputSinge() {
        String singe = singeInputEt.getText().toString();
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            ToastUtil.show("账号未登录");
            return;
        }
        showLoading("正在修改中......");
        avUser.put(AVDbManager.USER_SIGN, singe);
        avUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                hideLoading();
                if (e == null) {
                    ToastUtil.show("修改成功");
                } else {
                    ToastUtil.show("修改失败");
                }
            }
        });
    }
}
