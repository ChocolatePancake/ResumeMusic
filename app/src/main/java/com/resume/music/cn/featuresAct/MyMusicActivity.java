package com.resume.music.cn.featuresAct;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.resume.music.cn.R;
import com.resume.music.cn.adapter.MusicAdapter;
import com.resume.music.cn.recordingModel.PlayView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import tech.com.commoncore.avdb.AVGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.MEDIA_TYPE_MUSIC;

@Route(path = ModelPathManager.main_myMusic)
public class MyMusicActivity extends BaseTitleActivity {
    private SmartRefreshLayout smartRefresh;
    private RecyclerView musicRecycler;
    private MusicAdapter musicAdapter;
    private LinearLayout noDataLayout;

    private List<AVObject> dataAVObjectList;

    private PlayView playView;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的音乐")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false)
                .setRightText("编辑")
                .setOnRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(ModelPathManager.main_recordMusic).navigation();
                    }
                });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_music;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        noDataLayout = mContentView.findViewById(R.id.not_data_layout);
        smartRefresh = mContentView.findViewById(R.id.smart_refresh_layout);
        musicRecycler = mContentView.findViewById(R.id.my_music_recycler);
        playView = mContentView.findViewById(R.id.my_music_play_view);

        musicAdapter = new MusicAdapter(R.layout.layout_music_item);
        musicRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        musicRecycler.setAdapter(musicAdapter);

        musicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                playView.switchMusic(position);
                playView.playAVObjectPosition(position);
                playView.playMusic();
            }
        });

        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestData();
            }
        });

        if (dataAVObjectList == null || dataAVObjectList.isEmpty()) {
            smartRefresh.autoRefresh();
        }
    }

    private void requestData() {
        AVGlobal.getInstance().getAVImpl().requestFile(AVUser.getCurrentUser().getObjectId(), MEDIA_TYPE_MUSIC, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                smartRefresh.finishRefresh();
                if (e != null) {
                    ToastUtil.show("请求异常,请检查网络");
                } else {
                    if (list != null && !list.isEmpty()) {
                        dataAVObjectList = list;
                        playView.setMusicAVObject(list);
                        playView.playAVObjectPosition(0);
                    } else {
                        ToastUtil.show("您还没有填写简历");
                    }
                }
                upDateView();
            }
        });
    }

    private void upDateView() {
        if (dataAVObjectList == null || dataAVObjectList.isEmpty()) {
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            noDataLayout.setVisibility(View.GONE);
            musicAdapter.replaceData(dataAVObjectList);
            musicAdapter.notifyDataSetChanged();
        }
    }
}
