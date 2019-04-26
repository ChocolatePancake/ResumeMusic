package com.yyjj.zixun.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.yyjj.zixun.R;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.newsModel.PageNews;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.ImageGetterUtils;

import static tech.com.commoncore.manager.ModelPathManager.zixun_detailPath;

/**
 * Anthor:NiceWind
 * Time:2019/3/7
 * Desc:The ladder is real, only the climb is all.
 */
@Route(path = zixun_detailPath)
public class ArticleDetailActivity extends BaseTitleActivity {
    private TextView titleTx, dateTx, contentTx;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("文章详情")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.zixun_activity_article_deatil;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleTx = findViewById(R.id.article_detail_title_text);
        dateTx = findViewById(R.id.article_detail_data_text);
        contentTx = findViewById(R.id.article_detail_content_text);

        Bundle pageBundle = getIntent().getBundleExtra("pageNews");
        if (pageBundle != null) {
            PageNews pageNews = (PageNews) pageBundle.getSerializable("pageNews");
            if (pageNews != null && pageNews.post_title != null) {
                setNewsDataInfo(pageNews.post_title, pageNews.published_time, pageNews.post_content_filtered);
            }
        }

        PageNews pn = (PageNews) getIntent().getSerializableExtra("pageNews");
        if (pn != null && pn.post_title != null) {
            setNewsDataInfo(pn.post_title, pn.published_time, pn.post_content_filtered);
        }
    }

    private void setNewsDataInfo(String title, String data, String content) {
        titleTx.setText(title);
        dateTx.setText(data);
        contentTx.setText(Html.fromHtml(content, new ImageGetterUtils.MyImageGetter(this, contentTx), null));
    }

    public static void jumpActivity(Context context, PageNews pageNews) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("pageNews", pageNews);
        FastUtil.startActivity(context, ArticleDetailActivity.class, bundle);
    }
}
