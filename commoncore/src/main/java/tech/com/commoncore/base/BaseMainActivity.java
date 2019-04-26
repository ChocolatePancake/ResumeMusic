package tech.com.commoncore.base;

import android.support.v4.view.ViewPager;

import tech.com.commoncore.R;
import tech.com.commoncore.delegate.BaseMainTabDelegate;
import tech.com.commoncore.interf.IBaseMainView;


/**
 * @Author: AriesHoo on 2018/7/23 10:00
 * @E-Mail: AriesHoo@126.com
 * Function: 快速创建主页Activity布局
 * Description:
 */
public abstract class BaseMainActivity extends BaseActivity implements IBaseMainView {

    protected BaseMainTabDelegate mBaseMainTabDelegate;

    @Override
    public void setViewPager(ViewPager mViewPager) {

    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public int getContentLayout() {
        return isSwipeEnable() ? R.layout.base_main_view_pager : R.layout.base_main_view;
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();
        mBaseMainTabDelegate = new BaseMainTabDelegate(mContentView, this, this);
    }


    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onBackPressed() {
        quitApp();
    }
}
