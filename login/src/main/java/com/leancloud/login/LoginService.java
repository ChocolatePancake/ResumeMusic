package com.leancloud.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import tech.com.commoncore.basecomponent.service.ILoginService;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 */
public class LoginService implements ILoginService{
    @Override
    public String getUserId() {
        return "登录组件,登录ID";
    }

    @Override
    public boolean hasLogin() {
        return false;
    }

    @Override
    public Fragment newUserFragment(Bundle bundle) {
        return null;
    }
}
