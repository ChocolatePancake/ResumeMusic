package com.leancloud.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.leancloud.login.R;
import com.vise.xsnow.event.BusManager;

import cn.control.c.com.ccontrol.TimerView;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.event.LoginChangeEvent;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.OnNoDoubleClickListener;
import tech.com.commoncore.utils.RegUtils;
import tech.com.commoncore.utils.ToastUtil;

public class PhoneLoginActivity extends BaseTitleActivity {

    private EditText numberEt, smsCodeEt;
    private TimerView timerView;


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("手机号登录")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_phone_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        OnNoDoubleClickListener clickListener = new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                onDoubleNoClickListener(v.getId());
            }
        };

        numberEt = findViewById(R.id.et_phone_number);
        smsCodeEt = findViewById(R.id.et_sms_code);
        timerView = findViewById(R.id.sms_timer_view);

        timerView.setOnTimeClick(new TimerView.OnTimeClick() {
            @Override
            public boolean onTimeClick() {
                if (verifyNumber()) {
                    requestLoginSms();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(clickListener);
        findViewById(R.id.tv_registered).setOnClickListener(clickListener);
    }

    private void onDoubleNoClickListener(int viewId) {
        if (viewId == R.id.btn_login) {
            doLogin();
        } else if (viewId == R.id.tv_registered) {
            FastUtil.startActivity(this, RegisterActivity.class);
            finish();
        }
    }

    private boolean verifyNumber() {
        String number = numberEt.getText().toString().trim();
        if (number == null || number.isEmpty()) {
            ToastUtil.show("请输入手机号");
            return false;
        }
        if (!RegUtils.isMobile(number)) {
            ToastUtil.show("请输入正确的号码");
            return false;
        }
        return true;
    }

    private void requestLoginSms() {
        String number = numberEt.getText().toString();
        AVUser.requestLoginSmsCodeInBackground(number, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    ToastUtil.show("短信发送失败,可能太过频繁");
                }
            }
        });
    }

    private void doLogin() {
        if (DataUtils.isEmpty(numberEt.getText())) {
            ToastUtil.show("请输入账号");
            return;
        }

        if (DataUtils.isEmpty(smsCodeEt.getText())) {
            ToastUtil.show("请输入验证码");
            return;
        }

        final String number = numberEt.getText().toString().trim();
        final String smsCode = smsCodeEt.getText().toString().trim();

        AVUser.signUpOrLoginByMobilePhoneInBackground(number, smsCode, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    BusManager.getBus().post(new LoginChangeEvent(true)); //通知登录改变
                    PhoneLoginActivity.this.finish();
                } else {
                    ToastUtil.show("手机号或密码错误");
                }
            }
        });
    }
}
