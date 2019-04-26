package com.resume.music.cn.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.resume.music.cn.R;
import com.resume.music.cn.entity.TabEntity;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import tech.com.commoncore.base.BaseActivity;
import tech.com.commoncore.basecomponent.ServiceFactory;
import tech.com.commoncore.event.SwitchEvent;
import tech.com.commoncore.utils.ToastUtil;

@Route(path = "/app/main")
public class MainActivity extends BaseActivity {
    public static final String TAG_F_FIRST = "TAG_FIRST_FLAG";
    public static final String TAG_F_SECOND = "TAG_SECOND_FLAG";
    public static final String TAG_F_THIRD = "TAG_THIRD_FLAG";
    public static final String TAG_F_FOUR = "TAG_FOUR_FLAG";
    public static final String TAG_F_LAST = "TAG_LAST_FLAG";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private int mCurrentIndex;

    private String[] mTitles = {"首页", "自选", "行情", "我的"};
    private int[] mIconUnSelectIds = {
            R.mipmap.icon_tab_un_home,
            R.mipmap.icon_tab_un_optional,
            R.mipmap.icon_tab_un_market,
            R.mipmap.icon_tab_un_me
    };
    private int[] mIconSelectIds = {
            R.mipmap.icon_tab_home,
            R.mipmap.icon_tab_optional,
            R.mipmap.icon_tab_market,
            R.mipmap.icon_tab_me
    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private Fragment mainFirstFragment;
    private Fragment mainSecondFragment;
    private Fragment mainThirdFragment;
    private Fragment mainFourFragment;
    private Fragment mainLastFragment;

    private FrameLayout mFlMainContent;
    private CommonTabLayout mMainTab;

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mFlMainContent = findViewById(R.id.fl_main_content);
        mMainTab = findViewById(R.id.main_tab);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }

        mMainTab.setTabData(mTabEntities);
        mMainTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchToFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragmentManager = getSupportFragmentManager();
        //如果是从崩溃中恢复，还需要加载之前的缓存
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState);
        } else {
            switchToFragment(0);
        }
    }

    /**
     * Activity被销毁的时候，要记录当前处于哪个页面
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mCurrentIndex);
        super.onSaveInstanceState(outState);
    }

    /**
     * 如果fragment因为内存不够或者其他原因被销毁掉，在这个方法中执行恢复操作
     */
    private void restoreFragment(Bundle savedInstanceState) {
        mCurrentIndex = savedInstanceState.getInt("index");

        mainFirstFragment = mFragmentManager.findFragmentByTag(TAG_F_FIRST);
        mainSecondFragment = mFragmentManager.findFragmentByTag(TAG_F_SECOND);
        mainThirdFragment = mFragmentManager.findFragmentByTag(TAG_F_THIRD);
        mainFourFragment = mFragmentManager.findFragmentByTag(TAG_F_FOUR);
        mainLastFragment = mFragmentManager.findFragmentByTag(TAG_F_LAST);

        switchToFragment(mCurrentIndex);
        mMainTab.setCurrentTab(mCurrentIndex);
    }

    /**
     * 切换fragment
     *
     * @param position
     */
    private void switchToFragment(int position) {
        mTransaction = mFragmentManager.beginTransaction();
        hideAllFragments(mTransaction);
        switch (position) {
            case 0:
                showFirstFragment();
                break;
            case 1:
                showSecondFragment();
                break;
            case 2:
                showThirdFragment();
                break;
            case 3:
                showFourFragment();
                break;
            case 4:
                showLastFragment();
                break;
        }
        mCurrentIndex = position;
        mTransaction.commit();
    }

    //隐藏所有的 fragment
    private void hideAllFragments(FragmentTransaction mTransaction) {
        if (mainFirstFragment != null) {
            mTransaction.hide(mainFirstFragment);
        }
        if (mainSecondFragment != null) {
            mTransaction.hide(mainSecondFragment);
        }
        if (mainThirdFragment != null) {
            mTransaction.hide(mainThirdFragment);
        }
        if (mainFourFragment != null) {
            mTransaction.hide(mainFourFragment);
        }
        if (mainLastFragment != null) {
            mTransaction.hide(mainLastFragment);
        }
    }

    /*第一个tab*/
    private void showFirstFragment() {
        if (mainFirstFragment == null) {
            mainFirstFragment = ServiceFactory.getInstance().getHomeService().newEntryFragment(null);
            mTransaction.add(R.id.fl_main_content, mainFirstFragment, TAG_F_FIRST);
        } else {
            mTransaction.show(mainFirstFragment);
        }
    }

    /*第二个tab*/
    private void showSecondFragment() {
        if (mainSecondFragment == null) {
            mainSecondFragment = ServiceFactory.getInstance().getZixunServiceService().newVideoFragment(null);
            mTransaction.add(R.id.fl_main_content, mainSecondFragment, TAG_F_THIRD);
        } else {
            mTransaction.show(mainSecondFragment);
        }
    }

    /*第三个tab*/
    private void showThirdFragment() {
        if (mainThirdFragment == null) {
            mainThirdFragment = ServiceFactory.getInstance().getCircleService().newFindFragment(null);
            mTransaction.add(R.id.fl_main_content, mainThirdFragment, TAG_F_SECOND);
        } else {
            mTransaction.show(mainThirdFragment);
        }
    }

    /*第四个tab*/
    private void showFourFragment() {
        if (mainFourFragment == null) {
            mainFourFragment = ServiceFactory.getInstance().getPerconalServiceService().newEntryFragment(null);
            mTransaction.add(R.id.fl_main_content, mainFourFragment, TAG_F_FOUR);
        } else {
            mTransaction.show(mainFourFragment);
        }
    }

    /*第五个tab*/
    private void showLastFragment() {
        if (mainLastFragment == null) {
//            mainLastFragment = ServiceFactory.getInstance().getPerconalServiceService().newEntryFragment(null);
            mTransaction.add(R.id.fl_main_content, mainLastFragment, TAG_F_LAST);
        } else {
            mTransaction.show(mainLastFragment);
        }
    }


    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    @Subscribe
    public void switchTab(IEvent event) {
        if (event instanceof SwitchEvent) {
            mCurrentIndex = ((SwitchEvent) event).position;
            switchToFragment(mCurrentIndex);
            mMainTab.setCurrentTab(mCurrentIndex);
        }
    }


    private long oneTime = 0;

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        long time = System.currentTimeMillis();
        if ((time - oneTime > 1000)) {
            ToastUtil.show("快速点击退出");
            oneTime = time;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
