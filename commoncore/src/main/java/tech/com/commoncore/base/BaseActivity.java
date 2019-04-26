package tech.com.commoncore.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;


import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.widget.progress.UIProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.vise.xsnow.event.BusManager;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.interf.ActivityKeyEventControl;
import tech.com.commoncore.interf.IBaseRefreshLoadView;
import tech.com.commoncore.interf.IBaseView;
import tech.com.commoncore.interf.QuitAppControl;
import tech.com.commoncore.manager.RxJavaManager;
import tech.com.commoncore.retrofit.FastObserver;
import tech.com.commoncore.utils.StackUtil;

/**
 * @Author: AriesHoo on 2018/7/13 17:38
 * @E-Mail: AriesHoo@126.com
 * Function: 所有Activity的基类
 * Description:
 * 1、2018-6-15 09:31:42 调整滑动返回类控制
 * 2、2018-6-20 17:15:12 调整主页back键操作逻辑
 * 3、2018-6-21 14:05:57 删除滑动返回控制改由全局控制
 * 4、2018-6-22 13:38:32 删除NavigationViewHelper控制方法改由全局控制
 * 5、2018-6-25 13:25:30 增加解决StatusLayoutManager与SmartRefreshLayout冲突解决方案
 * 6、2018-9-25 10:04:31 新增onActivityResult统一处理逻辑
 * 7、2018-9-26 16:59:59 新增按键监听统一处理
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {

    protected Activity mContext;
    protected View mContentView;
    protected Unbinder mUnBinder;

    protected boolean mIsViewLoaded = false;
    protected boolean mIsFirstShow = true;
    protected boolean mIsFirstBack = true;
    protected long mDelayBack = 2000;
    protected final String TAG = getClass().getSimpleName();
    private QuitAppControl mQuitAppControl;

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isRegisterEvent()) {
            BusManager.getBus().register(this);
        }
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mContext = this;
        beforeSetContentView();
        mContentView = View.inflate(mContext, getContentLayout(), null);
        //解决StatusLayoutManager与SmartRefreshLayout冲突
        if (this instanceof IBaseRefreshLoadView && mContentView.getClass() == SmartRefreshLayout.class) {
            FrameLayout frameLayout = new FrameLayout(mContext);
            if (mContentView.getLayoutParams() != null) {
                frameLayout.setLayoutParams(mContentView.getLayoutParams());
            }
            frameLayout.addView(mContentView);
            mContentView = frameLayout;
        }
        setContentView(mContentView);
        mUnBinder = ButterKnife.bind(this);
        mIsViewLoaded = true;
        beforeInitView();
        initView(savedInstanceState);
    }

    @Override
    protected void onResume() {
        beforeLazyLoad();
        super.onResume();
        //友盟统计
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (isRegisterEvent()) {
            BusManager.getBus().unregister(this);
        }
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> list = getSupportFragmentManager().getFragments();
        if (list == null || list.size() == 0) {
            return;
        }
        for (Fragment f : list) {
            f.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ActivityKeyEventControl control = FastManager.getInstance().getActivityKeyEventControl();
        if (control != null) {
            if (control.onKeyDown(this, keyCode, event)) {
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        ActivityKeyEventControl control = FastManager.getInstance().getActivityKeyEventControl();
        if (control != null) {
            if (control.onKeyUp(this, keyCode, event)) {
                return true;
            }
            return super.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        ActivityKeyEventControl control = FastManager.getInstance().getActivityKeyEventControl();
        if (control != null) {
            return control.onKeyLongPress(this, keyCode, event);
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        ActivityKeyEventControl control = FastManager.getInstance().getActivityKeyEventControl();
        if (control != null) {
            if (control.onKeyShortcut(this, keyCode, event)) {
                return true;
            }
            return super.onKeyShortcut(keyCode, event);
        }
        return super.onKeyShortcut(keyCode, event);

    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        ActivityKeyEventControl control = FastManager.getInstance().getActivityKeyEventControl();
        if (control != null) {
            if (control.onKeyMultiple(this, keyCode, repeatCount, event)) {
                return true;
            }
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public void beforeSetContentView() {
    }

    @Override
    public void beforeInitView() {
        if (FastManager.getInstance().getActivityFragmentControl() != null) {
            FastManager.getInstance().getActivityFragmentControl().setContentViewBackground(mContentView, this.getClass());
        }
    }

    @Override
    public void loadData() {

    }

    private void beforeLazyLoad() {
        //确保视图加载及视图绑定完成避免刷新UI抛出异常
        if (!mIsViewLoaded) {
            RxJavaManager.getInstance().setTimer(10)
                    .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(new FastObserver<Long>() {
                        @Override
                        public void _onNext(Long entity) {
                            beforeLazyLoad();
                        }
                    });
        } else {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (mIsFirstShow) {
            mIsFirstShow = false;
            loadData();
        }
    }

    /**
     * 退出程序
     */
    protected void quitApp() {
        mQuitAppControl = FastManager.getInstance().getQuitAppControl();
        mDelayBack = mQuitAppControl != null ? mQuitAppControl.quipApp(mIsFirstBack, this) : mDelayBack;
        //时延太小或已是第二次提示直接通知执行最终操作
        if (mDelayBack <= 0 || !mIsFirstBack) {
            if (mQuitAppControl != null) {
                mQuitAppControl.quipApp(false, this);
            } else {
                StackUtil.getInstance().exit();
            }
            return;
        }
        //编写逻辑
        if (mIsFirstBack) {
            mIsFirstBack = false;
            RxJavaManager.getInstance().setTimer(mDelayBack)
                    .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(new FastObserver<Long>() {
                        @Override
                        public void _onNext(Long entity) {
                            mIsFirstBack = true;
                        }
                    });
        }
    }


    private UIProgressDialog mProgressDialog;

    public void showLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
        mProgressDialog = new UIProgressDialog.WeBoBuilder(this)
                .setMessage("正在进行中...")
                .setCancelable(false)
                .create();
        mProgressDialog.setDimAmount(0.6f)
                .show();
    }

    public void showLoading(String str) {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
        mProgressDialog = new UIProgressDialog.WeBoBuilder(this)
                .setMessage(str)
                .setCancelable(false)
                .create();
        mProgressDialog.setDimAmount(0.6f)
                .show();
    }

    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }


}
