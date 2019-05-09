package com.resume.music.cn.adapter;

import com.avos.avoscloud.AVObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.resume.music.cn.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tech.com.commoncore.manager.GlideManager;

import static tech.com.commoncore.avdb.AVDbManager.MEDIA_FILE;
import static tech.com.commoncore.avdb.AVDbManager.MEDIA_FILE_NAME;

public class VideoAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {


    public VideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AVObject item) {
        String fileUrl = item.get(MEDIA_FILE).toString();
        String videoName = item.get(MEDIA_FILE_NAME).toString();

        JCVideoPlayerStandard player = helper.itemView.findViewById(R.id.video_player_standard);
        player.setUp(fileUrl, JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, videoName);
        player.startPlayLogic();
        GlideManager.loadImg(fileUrl, player.thumbImageView);
    }
}
