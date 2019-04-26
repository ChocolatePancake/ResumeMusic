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
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.leancloud.login.R;
import com.vise.xsnow.event.BusManager;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.event.LoginChangeEvent;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.manager.ModelPathManager.login_loginPath;

/**
 * Anthor:NiceWind
 * Time:2019/3/21
 * Desc:The ladder is real, only the climb is all.
 */

@Route(path = login_loginPath)
public class LoginActivity extends BaseTitleActivity {
    private EditText etPhoneNumber, etPassword;
    private ToggleButton toggleButton;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("登录")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.login_activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoubleNoClickListener(v.getId());
            }
        };
        etPhoneNumber = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        toggleButton = findViewById(R.id.tb_password_show);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setPasswordShowFoEditText(etPassword, isChecked);
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(clickListener);
        findViewById(R.id.tv_forget_password).setOnClickListener(clickListener);
        findViewById(R.id.tv_registered).setOnClickListener(clickListener);
        findViewById(R.id.tv_phone_login).setOnClickListener(clickListener);
    }

    private void setPasswordShowFoEditText(EditText editText, boolean checked) {
        if (checked) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void onDoubleNoClickListener(int viewId) {
        if (viewId == R.id.btn_login) {
            doLogin();
        } else if (viewId == R.id.tv_registered) {
            FastUtil.startActivity(this, RegisterActivity.class);
            finish();
        } else if (viewId == R.id.tv_forget_password) {
            FastUtil.startActivity(mContext, PasswordFindActivity.class);
        } else if (viewId == R.id.tv_phone_login) {
            FastUtil.startActivity(this, PhoneLoginActivity.class);
            finish();
        }
    }


    private void doLogin() {
        if (DataUtils.isEmpty(etPhoneNumber.getText())) {
            ToastUtil.show("请输入账号");
            return;
        }
        if (DataUtils.isEmpty(etPassword.getText())) {
            ToastUtil.show("请输入密码");
            return;
        }
        final String number = etPhoneNumber.getText().toString().trim();
        final String smsCode = etPassword.getText().toString().trim();
        showLoading();
        AVUser.loginByMobilePhoneNumberInBackground(number, smsCode, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                hideLoading();
                if (e == null) {
                    BusManager.getBus().post(new LoginChangeEvent(true)); //通知登录改变
                    LoginActivity.this.finish();
                } else {
                    ToastUtil.show("手机号或密码错误");
                }
            }
        });
    }
}
