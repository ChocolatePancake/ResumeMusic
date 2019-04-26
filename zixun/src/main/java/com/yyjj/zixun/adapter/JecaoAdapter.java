package com.yyjj.zixun.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyjj.zixun.R;
import com.yyjj.zixun.entity.VideoEntity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tech.com.commoncore.manager.GlideManager;

/**
 * Anthor:HeChuan
 * Time:2018/12/17
 * Desc:
 */
public class JecaoAdapter extends BaseQuickAdapter<VideoEntity,BaseViewHolder> {

    public JecaoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoEntity item) {

        JCVideoPlayerStandard playerStandard = helper.getView(R.id.videoplayer);
        playerStandard.setUp(item.onlineUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST,item.title);
//        playerStandard.thumbImageView.setImageResource(R.mipmap.zixun_img_default);
        GlideManager.loadImg(item.onlineUrl,playerStandard.thumbImageView,R.mipmap.zixun_img_default);
    }
}
