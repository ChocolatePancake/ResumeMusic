package com.leancloud.home.adapter;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.home.R;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.newsModel.PageNews;
import tech.com.commoncore.utils.DataUtils;

import static tech.com.commoncore.manager.ModelPathManager.zixun_detailPath;

public class HomeNewsAdapter extends BaseQuickAdapter<PageNews, BaseViewHolder> {

    public HomeNewsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PageNews item) {
        ImageView imageView = helper.itemView.findViewById(R.id.home_news_item_image);
        GlideManager.loadImg(item.thumbnail, imageView);
        helper.setText(R.id.home_news_item_title, DataUtils.toSBC(item.post_title))
                .setText(R.id.home_news_item_more, DataUtils.toSBC(item.post_excerpt))
                .setText(R.id.home_news_item_date, DataUtils.toDBC(item.published_time))
                .setText(R.id.home_news_item_collection, DataUtils.toDBC(item.post_hits + ""))
                .setText(R.id.home_news_item_like, DataUtils.toDBC(item.post_like + ""));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pageNews", item);
                ARouter.getInstance()
                        .build(zixun_detailPath)
                        .withBundle("pageNews", bundle)
                        .navigation();
            }
        });
    }
}
