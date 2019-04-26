package com.leancloud.circle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.title.TitleBarView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.leancloud.circle.R;
import com.leancloud.circle.adapter.ArticleAdapter;
import com.leancloud.circle.entity.ArticleEntity;
import com.leancloud.circle.entity.Author2Entity;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.base.BaseRefreshLoadActivity;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.view.SpaceItemDecoration;
import tech.com.commoncore.widget.MyCircleImageView;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */

@Route(path = "/circle/author/detail")
public class AuthorDetailActivity extends BaseRefreshLoadActivity{
    Author2Entity entity;
    TitleBarView titleBar;
    private tech.com.commoncore.widget.MyCircleImageView mIvIcon;
    private android.widget.TextView mTvName;
    private android.widget.TextView mTvFans;
    private android.widget.TextView mTvFocus,mtvContent;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        this.titleBar = titleBar;
        titleBar.setTitleMainText("作者介绍");
    }

    @Override
    public int getContentLayout() {
        return R.layout.circle_activity_author_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        try {
            entity = (Author2Entity) getIntent().getSerializableExtra("detail");
        }catch (Exception e){
            e.printStackTrace();
        }
        mIvIcon = (MyCircleImageView) findViewById(R.id.iv_icon);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvFans = (TextView) findViewById(R.id.tv_fans);
        mTvFocus = (TextView) findViewById(R.id.tv_focus);
        mtvContent = (TextView) findViewById(R.id.tv_content);

        if(entity == null){
            finish();
        }else{
            mTvFans.setText("粉丝\t\t" + entity.getPost_like());
            mTvName.setText(entity.getPost_title());
            mTvFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTvFocus.setSelected(!v.isSelected());
                    mTvFocus.setText(v.isSelected()?"已关注":"+关注");
                }
            });
            GlideManager.loadImg(getUrl(entity),mIvIcon);
            mtvContent.setText(entity.getPost_content());
            titleBar.setTitleSubText(entity.getPost_title());
        }
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

    }

    private String getUrl(Author2Entity item){
        if(item.getMore()!=null){
            return  item.getMore().getThumbnail();
        }
        return "";
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new ArticleAdapter(R.layout.circle_item_article);
    }

    @Override
    public void loadData(int page) {

//  文章列表     http://data.fk7h.com/yapi/article/alist/?cid=27&limit=10
//        http://data.fk7h.com/yapi/news/analyst_getlist/limit/1?limit=1

        if(page!=0){
            FastManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(),null,null);
            return;
        }

        ViseHttp.GET("/yapi/article/alist/").baseUrl("http://data.fk7h.com")
                .addParam("cid","27")
                .addParam("limit","10")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(data);
                            if(jsonObject.getInt("code") == 1){
                                List<ArticleEntity> list = GsonUtil.gson().fromJson(jsonObject.getString("data"),new TypeToken<ArrayList<ArticleEntity>>(){}.getType());
                                FastManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(),list,null);
                            }else{
                                FastManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(),null,null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            FastManager.getInstance().getHttpRequestControl().httpRequestError(getIHttpRequestControl(),e);
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        FastManager.getInstance().getHttpRequestControl().httpRequestError(getIHttpRequestControl(),errCode+"",errMsg);
                    }
                });
    }
}
