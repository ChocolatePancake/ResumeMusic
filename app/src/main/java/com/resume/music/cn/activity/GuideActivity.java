package com.resume.music.cn.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aries.ui.view.title.TitleBarView;
import com.resume.music.cn.R;
import com.resume.music.cn.entity.LoopBean;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.GlideViewPager;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.OnNoDoubleClickListener;

/**
 * Desc:
 */
public class GuideActivity extends BaseTitleActivity {

    private static final int[] guideImages = {R.mipmap.ic_guide_1, R.mipmap.ic_guide_2, R.mipmap.ic_guide_3};
    private static final String[] guideTexts = {"数据分析", "互动圈子", "热门资讯"};

    private GlideViewPager glideViewPager;
    private Button splashStartBtn;
    private ZoomIndicator splashBottomLayout;

    @Override
    public int getContentLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        glideViewPager = findViewById(R.id.glide_view_pager);
        splashStartBtn = findViewById(R.id.splash_start_btn);
        splashBottomLayout = findViewById(R.id.splash_bottom_layout);
        initViewPager();
    }

    private void initViewPager() {
        List<LoopBean> loopBeans = new ArrayList<>();
        for (int i = 0; i < guideTexts.length; i++) {
            LoopBean bean = new LoopBean();
            bean.img = guideImages[i];
            bean.text = guideTexts[i];
            loopBeans.add(bean);
        }

        PageBean bean = new PageBean.Builder<LoopBean>()
                .setDataObjects(loopBeans)
                .setOpenView(splashStartBtn)
                .setIndicator(splashBottomLayout)
                .builder();

        glideViewPager.setPageListener(bean, R.layout.vp_guide, new PageHelperListener<LoopBean>() {
            @Override
            public void getItemView(View view, final LoopBean data) {
                ImageView imageView = view.findViewById(R.id.imageview);
                imageView.setImageResource(data.img);
                view.setOnClickListener(new OnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (data.img == guideImages[guideImages.length - 1]) {
                            FastUtil.startActivity(mContext, MainActivity.class);
                            finish();
                        }
                    }
                });
            }
        });

        splashStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastUtil.startActivity(mContext, MainActivity.class);
                finish();
            }
        });

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setLeftTextDrawable(null)
                .setBackgroundColor(Color.TRANSPARENT);
    }
}
