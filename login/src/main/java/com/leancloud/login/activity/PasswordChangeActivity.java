package com.leancloud.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.leancloud.login.R;
import com.leancloud.login.cache.CacheData;
import tech.com.commoncore.avdb.AVDbManager;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.login_changePwdPath;

/**
 * Desc:
 */
@Route(path = login_changePwdPath)
public class PasswordChangeActivity extends BaseTitleActivity {
    private EditText mTvOldPassword;
    private EditText mTvNewPassword;
    private EditText mTvNewPasswordConfirm;

    private ToggleButton tbOldPasswordShow;
    private ToggleButton tbNewPasswordShow;
    private ToggleButton tbNewPasswordRepeatShow;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("修改密码")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.login_activity_password_change;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvOldPassword = findViewById(R.id.et_old_password);
        mTvNewPassword = findViewById(R.id.et_new_password);
        mTvNewPasswordConfirm = findViewById(R.id.et_new_password_confirm);

        tbOldPasswordShow = findViewById(R.id.tb_old_password_show);
        tbNewPasswordShow = findViewById(R.id.tb_new_password_show);
        tbNewPasswordRepeatShow = findViewById(R.id.tb_news_password_repeat_show);
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPasswordChange();
            }
        });

        tbOldPasswordShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setPasswordShowFoEditText(mTvOldPassword, isChecked);
            }
        });
        tbNewPasswordShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setPasswordShowFoEditText(mTvNewPassword, isChecked);
            }
        });
        tbNewPasswordRepeatShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setPasswordShowFoEditText(mTvNewPasswordConfirm, isChecked);
            }
        });
    }

    private void setPasswordShowFoEditText(EditText editText, boolean checked) {
        if (checked) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void doPasswordChange() {
        if (DataUtils.isEmpty(mTvOldPassword.getText())) {
            ToastUtil.show("请输入原始密码");
            return;
        }
        if (DataUtils.isEmpty(mTvNewPassword.getText())) {
            ToastUtil.show("请输入新密码");
            return;
        }
        if (DataUtils.isEmpty(mTvNewPasswordConfirm.getText())) {
            ToastUtil.show("请输入确认新密码");
            return;
        }
        String oldPass = mTvOldPassword.getText().toString();
        final String newpass = mTvNewPassword.getText().toString();
        String newPassConfirm = mTvNewPasswordConfirm.getText().toString();

        if (!newpass.equals(newPassConfirm)) {
            ToastUtil.show("密码不一致");
            return;
        }

        AVDbManager.changePassword(oldPass, newpass, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    CacheData.setLoginPassword(mContext, newpass);
                    ToastUtil.show("密码更改成功");
                    PasswordChangeActivity.this.finish();
                } else {
                    ToastUtil.show("旧密码不正确");
                }
            }
        });
    }
}
