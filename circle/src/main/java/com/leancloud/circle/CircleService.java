package com.leancloud.circle;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.leancloud.circle.fragment.CircleFragment;
import com.leancloud.circle.fragment.FindFragment;

import tech.com.commoncore.basecomponent.service.ICircleService;


/**
 * Anthor:NiceWind
 * Time:2019/3/25
 * Desc:The ladder is real, only the climb is all.
 */
public class CircleService implements ICircleService {

    @Override
    public Fragment newCircleFragment(Bundle bundle) {
        return CircleFragment.newInstance();
    }

    @Override
    public Fragment newFindFragment(Bundle bundle) {
        return FindFragment.newInstance();
    }
}
