package com.leancloud.circle.adapter;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.circle.R;
import com.leancloud.circle.activity.AuthorDetailActivity;
import com.leancloud.circle.entity.Author2Entity;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.FastUtil;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class Author2Adapter extends BaseQuickAdapter<Author2Entity,BaseViewHolder>{
    public Author2Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Author2Entity item) {
        helper.setText(R.id.tv_name,item.getPost_title())
                .setText(R.id.tv_focus, Html.fromHtml(item.getPost_like()+ "<font color='#898989'>关注</font>"))
                .setText(R.id.tv_content,item.getPost_content())
                .setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("detail", item);
//                        ARouter.getInstance().build("/circle/author/detail")
//                                .with(bundle)
//                                .navigation();
                        FastUtil.startActivity(mContext, AuthorDetailActivity.class,bundle);
                    }
                });

        GlideManager.loadImg(getUrl(item), (ImageView) helper.getView(R.id.iv_icon));
    }

    private String getUrl(Author2Entity item){
        if(item.getMore()!=null){
        return  item.getMore().getThumbnail();
        }
        return "";
    }
}
