package com.resume.music.cn.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.resume.music.cn.R;
import com.resume.music.cn.adapter.HomeColumnAdapter;
import com.resume.music.cn.entity.LoopBean;
import com.zhengsr.viewpagerlib.anim.ZoomOutPageTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.plog;
import tech.com.commoncore.view.SpaceItemDecoration;

import static tech.com.commoncore.manager.ModelPathManager.*;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final int[] guideImages = {R.mipmap.ic_guide_1, R.mipmap.ic_guide_2, R.mipmap.ic_guide_3};

    private static final int[] columnImages = {R.drawable.icon_home_column_resume, R.drawable.icon_home_column_video, R.drawable.icon_home_column_utli, R.drawable.icon_home_column_activity};
    private static final String[] columnText = {"简历", "小视频", "策划", "活动"};

    private BannerViewPager bannerViewPager;
    private ZoomIndicator zoomIndicator;
    private RecyclerView columnRecycler;
    private HomeColumnAdapter homeColumnAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_recording;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(Bundle savedInstanceState) {
        setImmersiveWindowStyle();
        bannerViewPager = mContentView.findViewById(R.id.loop_viewpager_arc);
        zoomIndicator = mContentView.findViewById(R.id.bottom_zoom_arc);
        columnRecycler = mContentView.findViewById(R.id.home_column_recycler);
        int space = (int) getResources().getDimension(R.dimen.dp_12);
        SpaceItemDecoration decoration = new SpaceItemDecoration(space);
        columnRecycler.addItemDecoration(decoration);
        homeColumnAdapter = new HomeColumnAdapter(R.layout.layout_home_column_recycler_item);
        columnRecycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        columnRecycler.setAdapter(homeColumnAdapter);

        mContentView.findViewById(R.id.home_audition_type_1).setOnClickListener(this);
        mContentView.findViewById(R.id.home_audition_type_2).setOnClickListener(this);
        mContentView.findViewById(R.id.home_audition_type_3).setOnClickListener(this);

        homeColumnAdapter.setOnClickItem(new HomeColumnAdapter.OnClickItem() {
            @Override
            public void clickItem(int position) {
                plog.paly("点击了:" + position);
                switch (position) {
                    case 0:
                        ARouter.getInstance().build(main_myResumeList).navigation();
                        break;
                    case 1:
                        ARouter.getInstance().build(main_videoList).navigation();
                        break;
                    case 2:
                        ARouter.getInstance().build(main_planList).navigation();
                        break;
                    case 3:
                        ARouter.getInstance().build(main_myPartList).navigation();
                        break;
                }
            }
        });

        ARouter.getInstance().build(main_editResume).navigation();//TODO 用完记得删
        initViewPager();
    }

    private void initViewPager() {
        bannerViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
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

        List<LoopBean> columnBean = new ArrayList<>();
        for (int i = 0; i < columnImages.length; i++) {
            LoopBean loopBean = new LoopBean();
            loopBean.img = columnImages[i];
            loopBean.text = columnText[i];
            columnBean.add(loopBean);
        }
        homeColumnAdapter.replaceData(columnBean);
        homeColumnAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_audition_type_1:
                ARouter.getInstance().build(ModelPathManager.main_musicList).withInt("musicType", 0).navigation();
                break;
            case R.id.home_audition_type_2:
                ARouter.getInstance().build(ModelPathManager.main_musicList).withInt("musicType", 1).navigation();
                break;
            case R.id.home_audition_type_3:
                ARouter.getInstance().build(ModelPathManager.main_musicList).withInt("musicType", 2).navigation();
                break;
        }
    }
}
