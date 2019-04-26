package com.yyjj.zixun.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyjj.zixun.R;


import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.newsModel.VideoNews;

public class NewsAboutVideoAdapter extends BaseQuickAdapter<VideoNews, BaseViewHolder> {

    private OnItemClick onItemClick;

    public NewsAboutVideoAdapter(int layoutResId) {
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
                if (onItemClick != null) {
                    onItemClick.setOnItemClick(item);
                }
            }
        });
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public abstract static class OnItemClick {
        public abstract void setOnItemClick(VideoNews videoNews);
    }
}
