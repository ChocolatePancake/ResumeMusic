package tech.com.commoncore.basecomponent.empty_service;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import tech.com.commoncore.basecomponent.service.ICircleService;

public class EmptyCircleFragment implements ICircleService {

    @Override
    public Fragment newCircleFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }

    @Override
    public Fragment newFindFragment(Bundle bundle) {
        return DefaultFragment.newInstance();
    }
}
