package com.leancloud.circle.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.aries.ui.view.title.TitleBarView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.leancloud.circle.R;
import com.leancloud.circle.activity.PublishCircleActivity;
import com.leancloud.circle.adapter.Author2Adapter;
import com.leancloud.circle.adapter.HomeRecommendAdapter;
import com.leancloud.circle.entity.Author2Entity;
import com.leancloud.circle.entity.AuthorEntity;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.ArrayList;
import java.util.List;

import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.base.BaseTitleRefreshLoadFragment;
import tech.com.commoncore.manager.LoggerManager;
import tech.com.commoncore.utils.AseetsUtil;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.ScreenUtils;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.view.SpaceItemDecoration;
import tech.com.commoncore.view.SpaceItemDecorationListHo;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class CircleFragment extends BaseTitleRefreshLoadFragment {

    private RecyclerView mRecyclerAuthor;
    private Button mBtnCommit;

    private HomeRecommendAdapter recommendAdapter;

    public static CircleFragment newInstance() {
        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("发现")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);

    }

    @Override
    public int getContentLayout() {
        return R.layout.circle_fragment_circle;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerClickListener(v.getId());
            }
        };
        mRecyclerAuthor = mContentView.findViewById(R.id.recycler_author);
        mBtnCommit = mContentView.findViewById(R.id.btn_commit);
        initAuthors();
        mBtnCommit.setOnClickListener(clickListener);

        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
    }

    private void handlerClickListener(int id) {
        if (id == R.id.btn_commit) {
            ToastUtil.showSuccess("关注成功");
            return;
        }
    }

    private void initAuthors() {
        mRecyclerAuthor.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerAuthor.addItemDecoration(new SpaceItemDecorationListHo(ScreenUtils.dip2px(10)));
        recommendAdapter = new HomeRecommendAdapter(R.layout.circle_item_recommend);
        mRecyclerAuthor.setAdapter(recommendAdapter);

        try {
            String json = AseetsUtil.getJson("authors.json", mContext);
            List<AuthorEntity> authorEntities = JSONArray.parseArray(json, AuthorEntity.class);
            LoggerManager.e("authorEntities" + authorEntities.toString());
            recommendAdapter.replaceData(authorEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public BaseQuickAdapter getAdapter() {
        return new Author2Adapter(R.layout.circle_item_author_url);
    }

    @Override
    public void loadData(int page) {

//  文章列表     https://data.fk7h.com/yapi/Gulin/index?type=34& page=1
//        http://data.fk7h.com/yapi/news/analyst_getlist/limit/1?limit=1&page=1
//http://data.fk7h.com/yapi/news/analyst_getlist/limit/1?limit=1

        if (page != 0) {
            FastManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), null, null);
            return;
        }

        ViseHttp.GET("/yapi/news/analyst_getlist/limit/1").baseUrl("http://data.fk7h.com")
                .addParam("limit", "1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(data);
                            if (jsonObject.getInt("code") == 1) {
                                List<Author2Entity> list = GsonUtil.gson().fromJson(jsonObject.getJSONObject("data").getString("data"), new TypeToken<ArrayList<Author2Entity>>() {
                                }.getType());
                                FastManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), list, null);
                            } else {
                                FastManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), null, null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            FastManager.getInstance().getHttpRequestControl().httpRequestError(getIHttpRequestControl(), e);
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        FastManager.getInstance().getHttpRequestControl().httpRequestError(getIHttpRequestControl(), errCode + "", errMsg);
                    }
                });

    }
}
