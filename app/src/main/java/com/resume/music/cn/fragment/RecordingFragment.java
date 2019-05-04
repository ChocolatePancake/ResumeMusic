package com.resume.music.cn.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.resume.music.cn.R;
import com.resume.music.cn.activity.MainActivity;
import com.resume.music.cn.entity.LoopBean;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.OnNoDoubleClickListener;

public class RecordingFragment extends BaseFragment {

    private static final int[] guideImages = {R.mipmap.ic_guide_1, R.mipmap.ic_guide_2, R.mipmap.ic_guide_3};
    private static final String[] guideTexts = {"数据分析", "互动圈子", "热门资讯"};

    private BannerViewPager bannerViewPager;
    private ZoomIndicator zoomIndicator;

    public static RecordingFragment newInstance() {
        RecordingFragment fragment = new RecordingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_recording;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bannerViewPager = mContentView.findViewById(R.id.loop_viewpager_arc);
        zoomIndicator = mContentView.findViewById(R.id.bottom_zoom_arc);

        bannerViewPager.setPageTransformer(false, new MzTransformer());
        initViewPager();
    }

    private void initViewPager() {
        List<LoopBean> loopBeans = new ArrayList<>();
        for (int i = 0; i < guideImages.length; i++) {
            LoopBean bean = new LoopBean();
            bean.img = guideImages[i];
            bean.text = "";
            loopBeans.add(bean);
        }

        PageBean bean = new PageBean.Builder<LoopBean>()
                .setDataObjects(loopBeans)
                .setIndicator(zoomIndicator)
                .builder();
        bannerViewPager.setPageListener(bean, R.layout.vp_guide, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object o) {
                final LoopBean data = (LoopBean) o;
                ImageView imageView = view.findViewById(R.id.imageview);
                imageView.setImageResource(data.img);
                
            }
        });
    }
}
