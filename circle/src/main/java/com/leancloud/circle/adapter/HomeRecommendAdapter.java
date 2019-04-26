package com.leancloud.circle.adapter;

import android.widget.ImageView;

import com.avos.avoscloud.AVObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.circle.R;
import com.leancloud.circle.entity.AuthorEntity;

import tech.com.commoncore.manager.GlideManager;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 */
public class HomeRecommendAdapter extends BaseQuickAdapter<AVObject,BaseViewHolder> {

    public HomeRecommendAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, AVObject item) {
        AuthorEntity theItem = ((AuthorEntity) item);
        helper.setText(R.id.tv_name,theItem.getName())
                .setText(R.id.tv_fans,theItem.getFansCount()+"个粉丝")
                .setText(R.id.tv_article,theItem.getArticleCount()+"文章");
        GlideManager.loadCircleImg(theItem.getIcon(), (ImageView) helper.getView(R.id.iv_head_icon));
    }
}
