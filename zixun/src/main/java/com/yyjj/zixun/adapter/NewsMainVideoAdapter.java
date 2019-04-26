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

public class NewsMainVideoAdapter extends BaseQuickAdapter<VideoNews, BaseViewHolder> {


    public NewsMainVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoNews item) {
        ImageView imageView = helper.itemView.findViewById(R.id.video_item_image);
        GlideManager.loadImg(item.url, imageView);

        helper.setText(R.id.video_item_title, item.title);

        helper.itemView.findViewById(R.id.video_item_player_image)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayVideoActivity.jumpActivity(mContext, item);
                    }
                });
    }
}
