package com.yyjj.zixun.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsVideoAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.newsModel.VideoNews;
import tech.com.commoncore.utils.VideoUtil;
import tech.com.commoncore.view.SpaceGridDecoration;

import static tech.com.commoncore.manager.ModelPathManager.zixun_videoPath;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
@Route(path = zixun_videoPath)
public class VideoActivity extends BaseTitleActivity {
    private RecyclerView mRecyclerView;
    private NewsVideoAdapter videoAdapter;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("期货视频")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.zixun_activity_video;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.rv_contentFastLib);
        int leftRight = (int) getResources().getDimension(R.dimen.dp_10);
        int topBottom = (int) getResources().getDimension(R.dimen.dp_15);
        mRecyclerView.addItemDecoration(new SpaceGridDecoration(leftRight,topBottom));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        videoAdapter = new NewsVideoAdapter(R.layout.zixun_layout_item_video);
        mRecyclerView.setAdapter(videoAdapter);

        videoAdapter.replaceData(getVideos());
        videoAdapter.notifyDataSetChanged();
    }

    public ArrayList<VideoNews> getVideos() {
        ArrayList<VideoNews> v = new ArrayList<>();
        for (String address : VideoUtil.videos) {
            String url = VideoUtil.getRealUrl(address);
            v.add(new VideoNews(url, "", address, ""));
        }
        return v;
    }

}
