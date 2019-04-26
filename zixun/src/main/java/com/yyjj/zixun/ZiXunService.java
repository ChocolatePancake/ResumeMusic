package com.yyjj.zixun;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yyjj.zixun.fragment.NewsVideoFragment;
import com.yyjj.zixun.fragment.ZiXunVideoFragment;
import com.yyjj.zixun.fragment.ZixunFragment;

import tech.com.commoncore.basecomponent.service.IZiXunService;


/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 */
public class ZiXunService implements IZiXunService {

    @Override
    public Fragment newArticleFragment(Bundle bundle) {
        return ZixunFragment.newInstance();
    }

    @Override
    public Fragment newFlashFragment(Bundle bundle) {
        return null;
    }

    @Override
    public Fragment newCalendarFragment(Bundle bundle) {
        return null;
    }

    @Override
    public Fragment newVideoFragment(Bundle bundle) {
        return ZiXunVideoFragment.newInstance();
    }
}
