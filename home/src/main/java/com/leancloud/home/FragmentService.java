package com.leancloud.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.leancloud.home.fragment.HomeFragment;

import tech.com.commoncore.basecomponent.service.IFragmentService;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 */
public class FragmentService implements IFragmentService {

    @Override
    public Fragment newEntryFragment(Bundle bundle) {
        return HomeFragment.newInstance();
    }
}
