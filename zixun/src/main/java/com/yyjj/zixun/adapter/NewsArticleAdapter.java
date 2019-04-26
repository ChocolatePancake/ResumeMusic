package com.yyjj.zixun.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyjj.zixun.R;
import com.yyjj.zixun.activity.ArticleDetailActivity;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.newsModel.PageNews;

/**
 * Anthor:NiceWind
 * Time:2019/3/7
 * Desc:The ladder is real, only the climb is all.
 */
public class NewsArticleAdapter extends BaseQuickAdapter<PageNews, BaseViewHolder> {
    public NewsArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PageNews item) {
        helper.setText(R.id.tv_title, item.post_title)
                .setText(R.id.tv_desc, item.published_time);

        GlideManager.loadImg(item.thumbnail, (ImageView) helper.getView(R.id.iv_pic));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleDetailActivity.jumpActivity(mContext, item);
            }
        });
    }
}
