package com.leancloud.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.aries.ui.widget.progress.UIProgressDialog;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.leancloud.login.R;
import com.leancloud.login.cache.CacheData;

import tech.com.commoncore.avdb.AVDbManager;

import cn.control.c.com.ccontrol.TimerView;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.others.UserAgreementActivity;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.RegUtils;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.login_registeredPath;

/**
 * Desc:
 */

@Route(path = login_registeredPath)
public class RegisterActivity extends BaseTitleActivity implements View.OnClickListener {
    private EditText etPhone;
    private EditText etVerifyCode;
    private TimerView timerView;
    private EditText etPassword;
    private EditText etPasswordRepeate;
    String phone = "";
    String verifyCode = "";
    String password = "";

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("注册账号")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.login_activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etPhone = findViewById(R.id.et_phone);
        timerView = findViewById(R.id.sms_timer_view);
        etVerifyCode = findViewById(R.id.et_verify_code);
        etPassword = findViewById(R.id.et_password);
        etPasswordRepeate = findViewById(R.id.et_password_rewrite);

        timerView.setOnTimeClick(new TimerView.OnTimeClick() {
            @Override
            public boolean onTimeClick() {
                if (verifyPhone()) {
                    senVerifyCode();
                    return true;
                }
                return false;
            }
        });
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
        findViewById(R.id.tv_xieyi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastUtil.startActivity(mContext, UserAgreementActivity.class);
            }
        });
    }

    private void doRegister() {
        if (!verifyPhone()) {
            return;
        }
        if (DataUtils.isEmpty(etVerifyCode.getText())) {
            ToastUtil.show("请输入验证码");
            return;
        }
        verifyCode = etVerifyCode.getText().toString().trim();

        if (etPassword.getText() == null) {
            ToastUtil.show("请输入密码");
            return;
        }
        password = etPassword.getText().toString().trim();

        if (DataUtils.isEmpty(etPasswordRepeate.getText())) {
            ToastUtil.show("请输入相同密码");
            return;
        }
        if (!password.equals(etPasswordRepeate.getText().toString().trim())) {
            ToastUtil.show("密码不一致");
            return;
        }
        showLoading();
        AVUser.signUpOrLoginByMobilePhoneInBackground(phone, verifyCode, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                mProgressDialog.hide();
                if (e == null) {
                    ToastUtil.show("注册成功");
                    //保存服务器密码+本地账号密码.
                    saveUserInfo(avUser.getUsername(), password);
                    avUser.put(AVDbManager.USER_NICK_NAME, phone);
                    avUser.saveInBackground();
                    //给一个默认昵称-手机号
                    setPw(avUser, password);
                    RegisterActivity.this.finish();
                } else {
                    ToastUtil.show("注册失败");
                }
            }
        });
    }

    /**
     * 保存用户登录资料
     */
    private void saveUserInfo(String username, String password) {
        CacheData.initLoginAccount(mContext, username, password);
    }

    private boolean verifyPhone() {
        phone = etPhone.getText().toString().trim();
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

    private void senVerifyCode() {
        AVOSCloud.requestSMSCodeInBackground(phone, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e != null)
                    ToastUtil.show("获取验证码失败");
            }
        });
    }

    /**
     * 保存密码
     *
     * @param user
     * @param password
     */
    private void setPw(AVUser user, String password) {
        user.updatePasswordInBackground(user.get("password") + "", password, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
            }
        });

    }

    private UIProgressDialog mProgressDialog;

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new UIProgressDialog.WeBoBuilder(this)
                    .setMessage("进行中...")
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
