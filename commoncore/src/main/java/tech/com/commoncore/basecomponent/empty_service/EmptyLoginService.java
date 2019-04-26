package tech.com.commoncore.basecomponent.empty_service;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import tech.com.commoncore.basecomponent.service.ILoginService;


/**
 * 默认的登陆模块接口
 */
public class EmptyLoginService implements ILoginService {

    @Override
    public Fragment newUserFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public boolean hasLogin() {
        return false;
    }
}
