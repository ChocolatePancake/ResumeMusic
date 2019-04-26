package tech.com.commoncore;

import android.accounts.AccountsException;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.aries.ui.helper.navigation.NavigationBarUtil;
import com.aries.ui.helper.navigation.NavigationViewHelper;
import com.aries.ui.helper.status.StatusViewHelper;
import com.aries.ui.util.StatusBarUtil;
import com.aries.ui.view.title.TitleBarView;
import com.aries.ui.widget.progress.UIProgressDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import retrofit2.HttpException;
import tech.com.commoncore.BuildConfig;
import tech.com.commoncore.impl.ActivityLifecycleCallbacks;
import tech.com.commoncore.interf.ActivityFragmentControl;
import tech.com.commoncore.interf.ActivityKeyEventControl;
import tech.com.commoncore.interf.HttpRequestControl;
import tech.com.commoncore.interf.IBaseRefreshLoadView;
import tech.com.commoncore.interf.IHttpRequestControl;
import tech.com.commoncore.interf.LoadMoreFoot;
import tech.com.commoncore.interf.LoadingDialog;
import tech.com.commoncore.interf.MultiStatusView;
import tech.com.commoncore.interf.OnHttpRequestListener;
import tech.com.commoncore.interf.QuitAppControl;
import tech.com.commoncore.interf.SwipeBackControl;
import tech.com.commoncore.interf.TitleBarViewControl;
import tech.com.commoncore.manager.LoggerManager;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.NetworkUtils;
import tech.com.commoncore.utils.SizeUtil;
import tech.com.commoncore.utils.SnackBarUtil;
import tech.com.commoncore.utils.StackUtil;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.widget.LoadDialog;

/**
 * @Author: AriesHoo on 2018/7/30 11:34
 * @E-Mail: AriesHoo@126.com
 * Function: 应用全局配置管理实现
 * Description:
 * 1、新增友盟统计功能对接
 */
public class AppImplDefault implements DefaultRefreshHeaderCreator, LoadMoreFoot, MultiStatusView, LoadingDialog,
        TitleBarViewControl, SwipeBackControl, ActivityFragmentControl, ActivityKeyEventControl, HttpRequestControl, QuitAppControl {

    private Context mContext;
    private String TAG = this.getClass().getSimpleName();

    public AppImplDefault(@Nullable Context context) {
        this.mContext = context;
    }

    /**
     * 下拉刷新头配置
     *
     * @param context
     * @param layout
     * @return
     */
    @NonNull
    @Override
    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        layout.setEnableHeaderTranslationContent(true)
                .setEnableOverScrollDrag(true);
        RefreshHeader header = new StoreHouseHeader(mContext);
        return header;
    }

    /**
     * Adapter加载更多配置
     *
     * @param adapter
     * @return
     */
    @Nullable
    @Override
    public LoadMoreView createDefaultLoadMoreView(BaseQuickAdapter adapter) {
        if (adapter != null) {
            //设置动画是否一直开启
            adapter.isFirstOnly(false);
            //设置动画
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        }
        return new SimpleLoadMoreView();
    }

    @Override
    public void setMultiStatusView(StatusLayoutManager.Builder statusView, IBaseRefreshLoadView iFastRefreshLoadView) {
    }

    @Nullable
    @Override
    public LoadDialog createLoadingDialog(@Nullable Activity activity, String text) {
        return new LoadDialog(activity, new UIProgressDialog.WeChatBuilder(activity)
                .setBackgroundColor(Color.parseColor("#FCFCFC"))
                .setTextSizeUnit(TypedValue.COMPLEX_UNIT_PX)
                .setMessage(text == null ? "加载中..." : text)
                .setLoadingSize(SizeUtil.dp2px(30))
                .setTextSize(SizeUtil.dp2px(16f))
                .setTextPadding(SizeUtil.dp2px(10))
                .setTextColor(Color.parseColor("#666666"))
                .setIndeterminateDrawable(FastUtil.getTintDrawable(ContextCompat.getDrawable(mContext, tech.com.commoncore.R.drawable.dialog_loading), ContextCompat.getColor(mContext, tech.com.commoncore.R.color.colorTitleText)))
                .setBackgroundRadius(SizeUtil.dp2px(6f))
                .create());
    }

    /**
     * 控制全局TitleBarView
     *
     * @param titleBar
     * @return
     */
    @Override
    public boolean createTitleBarViewControl(TitleBarView titleBar, Class<?> cls) {
        //默认的MD风格返回箭头icon如使用该风格可以不用设置
        Drawable mDrawable = FastUtil.getTintDrawable(ContextCompat.getDrawable(mContext, tech.com.commoncore.R.drawable.common_icon_back),
                ContextCompat.getColor(mContext, tech.com.commoncore.R.color.colorTitleText));
        //是否支持状态栏白色
        boolean isSupport = StatusBarUtil.isSupportStatusBarFontChange();
        boolean isActivity = Activity.class.isAssignableFrom(cls);
        Activity activity = StackUtil.getInstance().getActivity(cls);
        //设置TitleBarView 所有TextView颜色
        titleBar.setStatusBarLightMode(isSupport)
                //不支持黑字的设置白透明
                .setStatusAlpha(isSupport ? 0 : 102)
                .setLeftTextDrawable(isActivity ? mDrawable : null)
                .setDividerHeight(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? SizeUtil.dp2px(0.5f) : 0);
        if (activity != null) {
            titleBar.setTitleMainText(activity.getTitle());
        }
        ViewCompat.setElevation(titleBar, mContext.getResources().getDimension(tech.com.commoncore.R.dimen.dp_elevation));
        return false;
    }

    /**
     * 设置当前Activity是否支持滑动返回(用于控制是否添加一层{@link BGASwipeBackLayout})
     * 返回为true {@link #setSwipeBack(Activity, BGASwipeBackHelper)}才有设置的意义
     *
     * @param activity
     * @return
     */
    @Override
    public boolean isSwipeBackEnable(Activity activity) {
        return true;
    }

    /**
     * 设置Activity 全局滑动属性--包括三方库
     *
     * @param activity
     * @param swipeBackHelper BGASwipeBackHelper 控制详见{@link com.aries.library.fast.FastManager}
     */
    @Override
    public void setSwipeBack(Activity activity, BGASwipeBackHelper swipeBackHelper) {
        //以下为默认设置
        //需设置activity window背景为透明避免滑动过程中漏出背景也可减少背景层级降低过度绘制
        activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        swipeBackHelper.setSwipeBackEnable(true)
                .setShadowResId(tech.com.commoncore.R.color.colorSwipeBackBackground)
                //底部导航条是否悬浮在内容上设置过NavigationViewHelper可以不用设置该属性
                .setIsNavigationBarOverlap(isControlNavigation());
    }

    @Override
    public void onSwipeBackLayoutSlide(Activity activity, float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel(Activity activity) {

    }

    @Override
    public void onSwipeBackLayoutExecuted(Activity activity) {

    }

    /**
     * Audio管理器，用了控制音量
     */
    private AudioManager mAudioManager = null;
    private int mMaxVolume = 0;
    private int mMinVolume = 0;
    private int mCurrentVolume = 0;

    private void volume(int value, boolean plus) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            // 获取最大音乐音量
            mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            // 获取最小音乐音量
            mMinVolume = mAudioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        }
        if (plus) {
            if (mCurrentVolume >= mMaxVolume) {
                ToastUtil.show("当前音量已最大");
                return;
            }
            mCurrentVolume += value;
        } else {
            if (mCurrentVolume <= mMinVolume) {
                ToastUtil.show("当前音量已最小");
                return;
            }
            mCurrentVolume -= value;
        }
        if (mCurrentVolume < mMinVolume) {
            mCurrentVolume = mMinVolume;
        }
        if (mCurrentVolume > mMaxVolume) {
            mCurrentVolume = mMaxVolume;
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, AudioManager.FLAG_PLAY_SOUND);
        // 获取当前音乐音量
        mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        LoggerManager.i(TAG, "max:" + mMaxVolume + ";min:" + mMinVolume + ";current:" + mCurrentVolume);
        SnackBarUtil.with(StackUtil.getInstance().getCurrent().getWindow().getDecorView())
                .setBgColor(Color.LTGRAY)
                .setMessageColor(Color.MAGENTA)
                .setMessage("当前音量:" + mCurrentVolume)
                .setBottomMargin(NavigationBarUtil.getNavigationBarHeight(StackUtil.getInstance().getCurrent().getWindowManager()))
                .show();
    }

    @Override
    public boolean onKeyDown(Activity activity, int keyCode, KeyEvent event) {
        //演示拦截系统音量键控制--类似抖音
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                volume(1, false);
                LoggerManager.i(TAG, "volumeDown-activity:" + activity.getClass().getSimpleName());
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                volume(1, true);
                LoggerManager.i(TAG, "volumeUp-activity:" + activity.getClass().getSimpleName());
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(Activity activity, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyLongPress(Activity activity, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyShortcut(Activity activity, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(Activity activity, int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    /**
     * 设置Fragment/Activity根布局背景
     *
     * @param contentView
     * @param cls
     */
    @Override
    public void setContentViewBackground(View contentView, Class<?> cls) {
        //&&!android.app.Fragment.class.isAssignableFrom(cls)
        //compileSdkVersion 28已废弃
        //避免背景色重复
        if (!Fragment.class.isAssignableFrom(cls)
                && contentView.getBackground() == null) {
            contentView.setBackgroundResource(tech.com.commoncore.R.color.colorBackground);
        }
    }

    /**
     * 设置屏幕方向--注意targetSDK设置27以上不能设置windowIsTranslucent=true属性不然应用直接崩溃
     * 错误为 Only fullscreen activities can request orientation
     * 默认自动 ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
     * 竖屏 ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
     * 横屏 ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
     * {@link ActivityInfo#screenOrientation ActivityInfo.screenOrientation}
     *
     * @param activity
     */
    @Override
    public void setRequestedOrientation(Activity activity) {
        //全局控制屏幕横竖屏
        //先判断xml没有设置屏幕模式避免将开发者本身想设置的覆盖掉
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            try {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } catch (Exception e) {
                e.printStackTrace();
                LoggerManager.e(TAG, "setRequestedOrientation:" + e.getMessage());
            }
        }
    }

    /**
     * 设置非FastLib且未实现Activity 状态栏功能的三方Activity 状态栏沉浸
     *
     * @param activity
     * @param helper
     * @return
     */
    @Override
    public boolean setStatusBar(Activity activity, StatusViewHelper helper, View topView) {
        boolean isSupportStatusBarFont = StatusBarUtil.isSupportStatusBarFontChange();
        StatusBarUtil.setStatusBarLightMode(activity);
        helper.setTransEnable(isSupportStatusBarFont)
                .setPlusStatusViewEnable(true)
                .setStatusLayoutColor(Color.WHITE);
        setStatusBarActivity(activity);
        return true;
    }

    /**
     * {@link FastLifecycleCallbacks#onActivityStarted(Activity)}
     *
     * @param activity
     * @param helper
     */
    @Override
    public boolean setNavigationBar(Activity activity, NavigationViewHelper helper, View bottomView) {
        //暂时注释掉
        //其它默认属性请参考FastLifecycleCallbacks
        helper.setLogEnable(BuildConfig.DEBUG)
                .setTransEnable(isTrans(activity))
                .setPlusNavigationViewEnable(isTrans(activity))
                .setNavigationViewColor(Color.argb(isTrans(activity) ? 0 : 102, 0, 0, 0))
                .setNavigationLayoutColor(Color.WHITE);

        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            }
        });
        return isControlNavigation();
    }

    /**
     * Activity 生命周期监听--可用于三方统计页面数据
     * 示例仅为参考如无需添加自己代码可回调null
     *
     * @return
     */
    @Override
    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                //统一于滑动返回动画
                if (activity.isFinishing()) {
                    activity.overridePendingTransition(0, tech.com.commoncore.R.anim.bga_sbl_activity_swipeback_exit);
                }
            }
        };
    }

    /**
     * Fragment 生命周期回调
     *
     * @return
     */
    @Override
    public FragmentManager.FragmentLifecycleCallbacks getFragmentLifecycleCallbacks() {
        return new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);
                LoggerManager.i(TAG, "onFragmentResumed:统计Fragment:" + f.getClass().getSimpleName());
            }

            @Override
            public void onFragmentPaused(FragmentManager fm, Fragment f) {
                super.onFragmentPaused(fm, f);
                LoggerManager.i(TAG, "onFragmentPaused:统计Fragment:" + f.getClass().getSimpleName());
            }
        };
    }

    @Override
    public void httpRequestSuccess(IHttpRequestControl httpRequestControl, List<?> list, OnHttpRequestListener listener) {
        if (httpRequestControl == null) {
            return;
        }
        SmartRefreshLayout smartRefreshLayout = httpRequestControl.getRefreshLayout();
        BaseQuickAdapter adapter = httpRequestControl.getRecyclerAdapter();
        StatusLayoutManager statusLayoutManager = httpRequestControl.getStatusLayoutManager();
        int page = httpRequestControl.getCurrentPage();
        int size = httpRequestControl.getPageSize();

        LoggerManager.i(TAG, "smartRefreshLayout:" + smartRefreshLayout + ";adapter:" + adapter + ";status:" + ";page:" + page + ";class:" + httpRequestControl.getRequestClass());
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
        }
        if (adapter == null) {
            return;
        }
        adapter.loadMoreComplete();
        if (list == null || list.size() == 0) {
            //第一页没有
            if (page == 0) {
                adapter.setNewData(new ArrayList());
                statusLayoutManager.showEmptyLayout();
                if (listener != null) {
                    listener.onEmpty();
                }
            } else {
                adapter.loadMoreEnd();
                if (listener != null) {
                    listener.onNoMore();
                }
            }
            return;
        }
        statusLayoutManager.showSuccessLayout();
        if (smartRefreshLayout.isRefreshing() || page == 0) {
            adapter.setNewData(new ArrayList());
        }
        adapter.addData(list);
        if (listener != null) {
            listener.onNext();
        }
        if (list.size() < size) {
            adapter.loadMoreEnd();
            if (listener != null) {
                listener.onNoMore();
            }
        }
    }

    @Override
    public void httpRequestError(IHttpRequestControl httpRequestControl, Throwable e) {
        int reason = tech.com.commoncore.R.string.exception_other_error;
        //        int code = FastError.EXCEPTION_OTHER_ERROR;
        if (!NetworkUtils.isConnected(mContext)) {
            reason = tech.com.commoncore.R.string.exception_network_not_connected;
        } else {
            //网络异常--继承于AccountsException
            if (e instanceof NetworkErrorException) {
                reason = tech.com.commoncore.R.string.exception_network_error;
                //账户异常
            } else if (e instanceof AccountsException) {
                reason = tech.com.commoncore.R.string.exception_accounts;
                //连接异常--继承于SocketException
            } else if (e instanceof ConnectException) {
                reason = tech.com.commoncore.R.string.exception_connect;
                //socket异常
            } else if (e instanceof SocketException) {
                reason = tech.com.commoncore.R.string.exception_socket;
                // http异常
            } else if (e instanceof HttpException) {
                reason = tech.com.commoncore.R.string.exception_http;
                //DNS错误
            } else if (e instanceof UnknownHostException) {
                reason = tech.com.commoncore.R.string.exception_unknown_host;
            } else if (e instanceof JsonSyntaxException
                    || e instanceof JsonIOException
                    || e instanceof JsonParseException) {
                //数据格式化错误
                reason = tech.com.commoncore.R.string.exception_json_syntax;
            } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
                reason = tech.com.commoncore.R.string.exception_time_out;
            } else if (e instanceof ClassCastException) {
                reason = tech.com.commoncore.R.string.exception_class_cast;
            }
        }

        if (httpRequestControl == null || httpRequestControl.getStatusLayoutManager() == null) {
            ToastUtil.show(reason);
            return;
        }
        SmartRefreshLayout smartRefreshLayout = httpRequestControl.getRefreshLayout();
        BaseQuickAdapter adapter = httpRequestControl.getRecyclerAdapter();
        StatusLayoutManager statusLayoutManager = httpRequestControl.getStatusLayoutManager();
        int page = httpRequestControl.getCurrentPage();
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh(false);
        }
        if (adapter != null) {
            adapter.loadMoreComplete();
            if (statusLayoutManager == null) {
                return;
            }
            //初始页
            if (page == 0) {
                if (!NetworkUtils.isConnected(mContext)) {
                    //可自定义网络错误页面展示
                    statusLayoutManager.showCustomLayout(tech.com.commoncore.R.layout.layout_status_layout_manager_error);
                } else {
                    statusLayoutManager.showErrorLayout();
                }
                return;
            }
            //可根据不同错误展示不同错误布局  showCustomLayout(R.layout.xxx);
            statusLayoutManager.showErrorLayout();
        }
    }

    @Override
    public void httpRequestError(IHttpRequestControl httpRequestControl, @NonNull String errorCode, String errorMsg) {
    }

    /**
     * @param isFirst  是否首次提示
     * @param activity 操作的Activity
     * @return 延迟间隔--如不需要设置两次提示可设置0--最佳方式是直接在回调中执行你想要的操作
     */
    @Override
    public long quipApp(boolean isFirst, Activity activity) {
        if (activity.getClass().getName().contains("MainActivity")) {
            //默认配置
            if (isFirst) {
                Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                StackUtil.getInstance().exit();
            }
        }
        return 2000;
    }

    /**
     * 根据程序使用的三方库进行改造:本示例使用的三方库实现了自己的沉浸式状态栏及导航栏但和Demo的滑动返回不搭配故做相应调整
     *
     * @param activity
     */
    private void setStatusBarActivity(Activity activity) {
    }

    /**
     * 是否全透明-华为4.1以上可根据导航栏位置颜色设置导航图标颜色
     *
     * @return
     */
    protected boolean isTrans(Activity activity) {
        return false;
    }

    /**
     * 是否控制底部导航栏---目前发现小米8上检查是否有导航栏出现问题
     *
     * @return
     */
    private boolean isControlNavigation() {
        LoggerManager.i(TAG, "mode:" + Build.MODEL);
        return false;
    }
}
