package com.yyjj.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVUser;
import com.vise.xsnow.event.BusManager;
import com.yyjj.my.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.event.LoginChangeEvent;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.OnNoDoubleClickListener;
import tech.com.commoncore.utils.SPHelper;
import tech.com.commoncore.utils.ToastUtil;

public class SettingsActivity extends BaseTitleActivity {
    private static final String PUSH_SWITCH_KEY = "push_switch_key";

    private Switch pushMassageSwitch;
    private OnNoDoubleClickListener clickListener;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                hideLoading();
                ToastUtil.show("清理完毕");
            }
        }
    };

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("设置")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        pushMassageSwitch = findViewById(R.id.push_massage_switch);
        pushMassageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handlerPushSetting(isChecked);
            }
        });
        pushMassageSwitch.setChecked(SPHelper.getBooleanSF(this, PUSH_SWITCH_KEY, true));

        clickListener = new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                noDoubleClick(v);
            }
        };
        findViewById(R.id.setting_menu_check_update).setOnClickListener(clickListener);
        findViewById(R.id.setting_menu_password).setOnClickListener(clickListener);
        findViewById(R.id.setting_menu_clear).setOnClickListener(clickListener);
        findViewById(R.id.setting_menu_push).setOnClickListener(clickListener);
        findViewById(R.id.logout_btn).setOnClickListener(clickListener);
    }

    private void noDoubleClick(View view) {
        if (view.getId() == R.id.setting_menu_check_update) {
            ToastUtil.show("当前已经是最新版本");
        } else if (view.getId() == R.id.setting_menu_password) {
            ARouter.getInstance().build(ModelPathManager.login_changePwdPath).greenChannel().navigation();
        } else if (view.getId() == R.id.setting_menu_clear) {
            handlerClearCache();
        } else if (view.getId() == R.id.logout_btn) {
            handlerLogout();
        }
    }

    private void handlerClearCache() {
        showLoading("清理中,请稍后......");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(10086);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handlerPushSetting(boolean isChecked) {
        SPHelper.setBooleanSF(this, PUSH_SWITCH_KEY, isChecked);
    }

    private void handlerLogout() {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            ToastUtil.show("已经是退出状态");
        } else {
            avUser.logOut();
            BusManager.getBus().post(new LoginChangeEvent(false));
            ToastUtil.show("账号退出成功");
        }
    }
}
