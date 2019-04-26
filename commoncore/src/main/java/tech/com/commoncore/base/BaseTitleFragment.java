package tech.com.commoncore.base;

import com.aries.ui.view.title.TitleBarView;

import tech.com.commoncore.R;
import tech.com.commoncore.delegate.BaseTitleDelegate;
import tech.com.commoncore.interf.IBaseTitleView;

/**
 * @Author: AriesHoo on 2018/7/23 10:34
 * @E-Mail: AriesHoo@126.com
 * Function: 设置有TitleBar的Fragment
 * Description:
 */
public abstract class BaseTitleFragment extends BaseFragment implements IBaseTitleView {

    protected BaseTitleDelegate mBaseTitleDelegate;
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
        mBaseTitleDelegate = new BaseTitleDelegate(mContentView, this,this.getClass());
        mTitleBar = mBaseTitleDelegate.mTitleBar;
    }
}
