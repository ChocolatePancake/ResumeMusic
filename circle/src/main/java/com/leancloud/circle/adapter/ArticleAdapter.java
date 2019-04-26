package com.leancloud.circle.adapter;

import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.circle.R;
import com.leancloud.circle.entity.ArticleEntity;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.ImageGetterUtils;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleEntity,BaseViewHolder> {
    public ArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleEntity item) {
        helper.setText(R.id.tv_name,item.getPost_title())
                .setText(R.id.tv_time,item.getPublished_time())
                .setText(R.id.tv_content, Html.fromHtml(item.getPost_content_filtered(),new ImageGetterUtils.MyImageGetter(mContext, (TextView) helper.getView(R.id.tv_content)),null));
//                .setText(R.id.tv_content, Html.fromHtml(item.getPost_content_filtered()));

    }
}
