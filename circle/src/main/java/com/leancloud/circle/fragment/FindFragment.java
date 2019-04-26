package com.leancloud.circle.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.radius.RadiusTextView;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leancloud.circle.R;
import com.leancloud.circle.activity.PublishCircleActivity;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;

import java.lang.reflect.Method;
import java.util.ArrayList;

import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.base.BaseTitleFragment;
import tech.com.commoncore.bus.CommitEvent;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.plog;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.ToastUtil;

public class FindFragment extends BaseTitleFragment {

    private final static String[] title = {"今日推荐", "我的关注"};

    private SegmentTabLayout segmentTabLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private RecommendFragment recommendFragment;
    private AttentionFragment attentionFragment;

    private LinearLayout inputLayout;
    private EditText commitEt;
    private RadiusTextView sendCommitTx;

    private View contentView;

    private InputMethodManager inputMethodManager;

    private AVObject communityAVObject;

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("发现")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setRightText("发布")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AVUser.getCurrentUser() == null) {
                            ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
                        } else {
                            FastUtil.startActivity(mContext, PublishCircleActivity.class);
                        }
                    }
                });
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        segmentTabLayout = mContentView.findViewById(R.id.circle_find_tab_layout);
        inputLayout = mContentView.findViewById(R.id.circle_find_input_layout);
        commitEt = mContentView.findViewById(R.id.et_content);
        sendCommitTx = mContentView.findViewById(R.id.tv_send);

        inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        fragmentManager = getChildFragmentManager();
        selectFragmentByIndex(0);
        segmentTabLayout.setTabData(title);
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                selectFragmentByIndex(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        adaptiveKeyboardPushUp();

        sendCommitTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = commitEt.getText().toString();
                if (content.isEmpty()) {
                    ToastUtil.show("输入不能为空哦");
                    return;
                }
                AVDbGlobal.getInstance().getAVDb().publishComment(communityAVObject.getObjectId(), content, new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            commitEt.setText("");
                            inputMethodManager.hideSoftInputFromWindow(commitEt.getWindowToken(), 0);
                        }
                    }
                });
            }
        });
    }

    private void adaptiveKeyboardPushUp() {
        contentView = mContentView.findViewById(R.id.circle_content_view);
        commitEt.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(commitEt, contentView));
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int navigationBarHeight = (int) mContext.getResources().getDimension(R.dimen.dp_49);
                int diff;
                if (isNavigationBarShow()) {
                    diff = height - r.bottom - navigationBarHeight + getStateBarHeight();
                } else {
                    diff = height - r.bottom - navigationBarHeight;
                }


                if (diff > navigationBarHeight) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                        inputLayout.setVisibility(View.GONE);
                    }
                }
            }
        };
    }

    private int getStateBarHeight() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public boolean isNavigationBarShow() {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = mContext.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int screenHeight = dm.heightPixels;
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }
        int screenRealHeight = realDisplayMetrics.heightPixels;
        return (screenRealHeight - screenHeight) > 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void commitEvent(CommitEvent event) {
        communityAVObject = event.object;
        inputLayout.setVisibility(View.VISIBLE);
        commitEt.setFocusable(true);
        commitEt.setFocusableInTouchMode(true);
        commitEt.requestFocus();
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void selectFragmentByIndex(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (index == 0) {
            showRecommendFragment();
        } else {
            showAttentionFragment();
        }
        fragmentTransaction.commit();
    }

    private void showRecommendFragment() {
        hideFragment(attentionFragment);
        if (recommendFragment == null) {
            recommendFragment = RecommendFragment.newInstance();
            fragmentTransaction.add(R.id.circle_find_fragment, recommendFragment);
        } else {
            fragmentTransaction.show(recommendFragment);
        }
    }

    private void showAttentionFragment() {
        hideFragment(recommendFragment);
        if (attentionFragment == null) {
            attentionFragment = AttentionFragment.newInstance();
            fragmentTransaction.add(R.id.circle_find_fragment, attentionFragment);
        } else {
            fragmentTransaction.show(attentionFragment);
        }
    }

    private void hideFragment(BaseFragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }
}
