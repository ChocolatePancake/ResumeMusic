package com.yyjj.my.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVUser;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.yyjj.my.R;
import com.yyjj.my.activity.FeedBackActivity;
import com.yyjj.my.activity.NotificationActivity;
import com.yyjj.my.activity.OnLineServiceActivity;
import com.yyjj.my.activity.PersonalSettingActivity;
import com.yyjj.my.activity.SettingsActivity;
import com.yyjj.my.db.AVDbManager;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.OnNoDoubleClickListener;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class PersonalFragment extends BaseFragment {
    private LinearLayout mLlHead;
    private ImageView mIvIcon;
    private TextView mTvNick, mTvDec;
    private OnNoDoubleClickListener onNoDoubleClickListener;

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.my_fragmnet_personal;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setWindowStyle() {
        Window window = mContext.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(Bundle savedInstanceState) {
        setWindowStyle();
        mLlHead = mContentView.findViewById(R.id.ll_head);
        mIvIcon = mContentView.findViewById(R.id.iv_icon);
        mTvNick = mContentView.findViewById(R.id.tv_nick);
        mTvDec = mContentView.findViewById(R.id.tv_dec);

        onNoDoubleClickListener = new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                onNoDoubleClickListener(v.getId());
            }
        };
        mIvIcon.setOnClickListener(onNoDoubleClickListener);
        mTvNick.setOnClickListener(onNoDoubleClickListener);
        mTvDec.setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.persona_item_setting).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.persona_item_idea).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.persona_item_massage).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.persona_item_date).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.settings_collection_tx).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.settings_like_tx).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.title_bar_server_image).setOnClickListener(onNoDoubleClickListener);
        mContentView.findViewById(R.id.title_bar_search_image).setOnClickListener(onNoDoubleClickListener);
        setUserInfo();
    }

    public void onNoDoubleClickListener(int id) {
        AVUser avUser = AVUser.getCurrentUser();
        if (id == R.id.persona_item_date) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                FastUtil.startActivity(mContext, PersonalSettingActivity.class);
            }
            return;
        }
        if (id == R.id.persona_item_massage) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                FastUtil.startActivity(mContext, NotificationActivity.class);
            }
            return;
        }
        if (id == R.id.persona_item_idea) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                FastUtil.startActivity(mContext, FeedBackActivity.class);
            }
            return;
        }
        if (id == R.id.persona_item_setting) {
            FastUtil.startActivity(mContext, SettingsActivity.class);
            return;
        }
        if (id == R.id.iv_icon || id == R.id.tv_nick || id == R.id.tv_dec) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                FastUtil.startActivity(mContext, PersonalSettingActivity.class);
            }
            return;
        }
        if (id == R.id.settings_collection_tx) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                ARouter.getInstance().build(ModelPathManager.circle_collection).navigation();
            }
            return;
        }
        if (id == R.id.settings_like_tx) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                ARouter.getInstance().build(ModelPathManager.circle_like).navigation();
            }
            return;
        }
        if (id == R.id.title_bar_server_image) {
            if (avUser == null) {
                ARouter.getInstance().build(ModelPathManager.login_loginPath).navigation();
            } else {
                FastUtil.startActivity(mContext, OnLineServiceActivity.class);
            }
        }
        if (id == R.id.title_bar_search_image) {
            ARouter.getInstance().build(ModelPathManager.futures_searchValue).navigation();
        }
    }


    private void setUserInfo() {
        AVUser avUser = AVUser.getCurrentUser();
        String name = "立即登录";
        String headUrl = "";
        String number = "";
        String nikeName = "立即登录";
        String sign = "您还没有登录哦";
        if (avUser != null) {
            name = avUser.getUsername();
            headUrl = (String) avUser.get(AVDbManager.AUTHOR_ICON);
            number = avUser.getMobilePhoneNumber();
            nikeName = (String) avUser.get(AVDbManager.USER_NICKE_NAME);
            nikeName = (nikeName == null || nikeName.isEmpty()) ? "未设置昵称" : nikeName;
            sign = (String) avUser.get(AVDbManager.USER_SIGN);
            sign = (sign == null || sign.isEmpty()) ? "这个家伙很懒,什么也没留下" : sign;
        }
        GlideManager.loadCircleImg(headUrl, mIvIcon, R.mipmap.ic_default_head);
        mTvNick.setText(nikeName);
        mTvDec.setText(sign);
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    @Subscribe
    public void update(IEvent event) {
        setUserInfo();
    }

}
