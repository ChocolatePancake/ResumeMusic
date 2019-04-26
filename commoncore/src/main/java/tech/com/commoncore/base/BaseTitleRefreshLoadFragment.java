package tech.com.commoncore.base;


import com.aries.ui.view.title.TitleBarView;

import tech.com.commoncore.R;
import tech.com.commoncore.delegate.BaseTitleDelegate;
import tech.com.commoncore.interf.IBaseTitleView;

/**
 * @Author: AriesHoo on 2018/7/23 10:34
 * @E-Mail: AriesHoo@126.com
 * Function: 设置有TitleBar及下拉刷新Fragment
 * Description:
 */
public abstract class BaseTitleRefreshLoadFragment<T> extends BaseRefreshLoadFragment<T> implements IBaseTitleView {

    protected BaseTitleDelegate mFastTitleDelegate;
    protected TitleBarView mTitleBar;

    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
        titleBar.setRightText("")
                .setLeftTextDrawable(null)
                .setTitleMainTextMarquee(true)
                .setTitleSubTextMarquee(true)
                .setTitleMainText("")
                .setDividerVisible(false)
                .setDividerHeight(0);
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();
        mFastTitleDelegate = new BaseTitleDelegate(mContentView, this, this.getClass());
        mTitleBar = mFastTitleDelegate.mTitleBar;
    }

}
