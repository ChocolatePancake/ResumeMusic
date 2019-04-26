package com.yyjj.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yyjj.my.fragment.PersonalFragment;

import tech.com.commoncore.basecomponent.service.IFragmentService;
import tech.com.commoncore.basecomponent.service.ILoginService;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 */
public class MyService implements IFragmentService{

    @Override
    public Fragment newEntryFragment(Bundle bundle) {
        return PersonalFragment.newInstance();
    }
}
