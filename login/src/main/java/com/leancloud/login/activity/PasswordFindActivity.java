package com.leancloud.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.leancloud.login.R;

import cn.control.c.com.ccontrol.TimerView;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.RegUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.login_findPwdPath;

/**
 * Desc:
 */

@Route(path = login_findPwdPath)
public class PasswordFindActivity extends BaseTitleActivity {
    private EditText mEtUsername;
    private EditText mEtVerifyCode;
    private TimerView mTvVerifyCode;
    private EditText mEtPassword, mEtPasswordRepeat;
    private ToggleButton mIvPasswordShow, mIvPasswordShowRepeat;
    private Button mBtnConfirm;
    private TextView mTvRegist;
    String phone = "";
    String password = "";
    String code = "";

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("找回密码")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.login_activity_password_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mEtUsername = findViewById(R.id.et_phone);
        mEtVerifyCode = findViewById(R.id.et_auth_code);
        mTvVerifyCode = findViewById(R.id.sms_timer_view);
        mEtPassword = findViewById(R.id.et_password);
        mIvPasswordShow = findViewById(R.id.iv_password_show);
        mIvPasswordShowRepeat = findViewById(R.id.iv_password_show_repeat);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mTvRegist = findViewById(R.id.tv_register);
        mEtPasswordRepeat = findViewById(R.id.et_password_repeat);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassWord();
            }
        });
        mTvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FastUtil.startActivity(mContext, RegisterActivity.class);
            }
        });
        mTvVerifyCode.setOnTimeClick(new TimerView.OnTimeClick() {
            @Override
            public boolean onTimeClick() {
                if (verifyPhone()) {
                    sendVerifyCode();
                    return true;
                }
                return false;
            }
        });
        mIvPasswordShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mIvPasswordShowRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEtPasswordRepeat.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEtPasswordRepeat.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private boolean verifyPhone() {
        phone = mEtUsername.getText().toString().trim();
        if (phone == null || phone.isEmpty()) {
            ToastUtil.show("请输入手机号");
            return false;
        }
        if (!RegUtils.isMobile(phone)) {
            ToastUtil.show("请输入有效手机号");
            return false;
        }
        return true;
    }

    private void sendVerifyCode() {
        AVUser.requestLoginSmsCodeInBackground(phone, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e != null)
                    ToastUtil.show("获取验证码失败");
            }
        });
    }

    private void resetPassWord() {
        if (DataUtils.isEmpty(mEtVerifyCode.getText())) {
            ToastUtil.show("请输入验证码");
            return;
        }
        code = mEtVerifyCode.getText().toString().trim();
        if (DataUtils.isEmpty(mEtPassword.getText())) {
            ToastUtil.show("请输入新密码");
            return;
        }
        password = mEtPassword.getText().toString().trim();
        if (DataUtils.isEmpty(mEtPasswordRepeat.getText().toString().trim())) {
            ToastUtil.show("请在此输入密码");
            return;
        }
        if (!password.equals(mEtPasswordRepeat.getText().toString().trim())) {
            ToastUtil.show("密码不一致");
            return;
        }
        AVUser.resetPasswordBySmsCodeInBackground(code, password, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                hideLoading();
                if (e == null) {
                    ToastUtil.show("修改成功");
                    FastUtil.startActivity(mContext, LoginActivity.class);
                    PasswordFindActivity.this.finish();
                } else {
                    ToastUtil.show("修改失败");
                }
            }
        });
    }

}
