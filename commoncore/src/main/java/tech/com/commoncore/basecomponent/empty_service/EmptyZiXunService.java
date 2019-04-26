package tech.com.commoncore.basecomponent.empty_service;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import tech.com.commoncore.basecomponent.service.IFragmentService;
import tech.com.commoncore.basecomponent.service.IZiXunService;


/**
 * 默认的登陆模块接口
 */
public class EmptyZiXunService implements IZiXunService {

    @Override
    public Fragment newArticleFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }

    @Override
    public Fragment newFlashFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }

    @Override
    public Fragment newCalendarFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }

    @Override
    public Fragment newVideoFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }
}
