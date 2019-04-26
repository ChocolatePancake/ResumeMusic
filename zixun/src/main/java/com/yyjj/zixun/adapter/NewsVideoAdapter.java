package com.yyjj.zixun.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyjj.zixun.R;
import com.yyjj.zixun.activity.PlayVideoActivity;


import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.newsModel.VideoNews;

public class NewsVideoAdapter extends BaseQuickAdapter<VideoNews, BaseViewHolder> {

    public NewsVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoNews item) {
        ImageView imageView = helper.itemView.findViewById(R.id.video_item_image);
        GlideManager.loadImg(item.url, imageView, R.mipmap.icon_video_placeholder);

        helper.setText(R.id.video_item_title, item.title)
                .setText(R.id.video_item_date, item.date)
                .setText(R.id.video_item_hist, item.volume);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayVideoActivity.jumpActivity(mContext, item);
            }
        });
    }
}
