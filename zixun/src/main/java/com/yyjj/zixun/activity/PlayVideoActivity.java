package com.yyjj.zixun.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aries.ui.view.title.TitleBarView;
import com.bumptech.glide.Glide;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsAboutVideoAdapter;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tech.com.commoncore.base.BaseActivity;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.newsModel.VideoNews;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.VideoUtil;

public class PlayVideoActivity extends BaseActivity {

    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private RecyclerView newsAboutVideoRecycler;

    @Override
    public int getContentLayout() {
        return R.layout.activity_play_video;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setWindowStyle() {
        Window window = getWindow();
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
        jcVideoPlayerStandard = findViewById(R.id.video_player_view);
        newsAboutVideoRecycler = findViewById(R.id.news_about_video_recycler);

        VideoNews videoNews = (VideoNews) getIntent().getSerializableExtra("videoNews");
        playerSelectVideo(videoNews);

        newsAboutVideoRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        NewsAboutVideoAdapter videoAdapter = new NewsAboutVideoAdapter(R.layout.zixun_layout_item_about_video);
        newsAboutVideoRecycler.setAdapter(videoAdapter);
        videoAdapter.replaceData(getVideos());
        videoAdapter.notifyDataSetChanged();
        videoAdapter.setOnItemClick(new NewsAboutVideoAdapter.OnItemClick() {
            @Override
            public void setOnItemClick(VideoNews v) {
                playerSelectVideo(v);
            }
        });
    }

    private void playerSelectVideo(VideoNews videoNews) {
        jcVideoPlayerStandard.setUp(videoNews.url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, videoNews.title);
        Glide.with(this).load(videoNews.url).into(jcVideoPlayerStandard.thumbImageView);
    }

    public static void jumpActivity(Context context, VideoNews videoNews) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoNews", videoNews);
        FastUtil.startActivity(context, PlayVideoActivity.class, bundle);
    }

    public ArrayList<VideoNews> getVideos() {
        ArrayList<VideoNews> voids = new ArrayList<>();
        for (String address : VideoUtil.videos) {
            String url = VideoUtil.getRealUrl(address);
            voids.add(new VideoNews(url, "", address, ""));
        }
        return voids;
    }

}
