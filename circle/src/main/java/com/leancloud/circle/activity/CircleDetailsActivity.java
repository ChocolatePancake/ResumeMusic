package com.leancloud.circle.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aries.ui.view.radius.RadiusTextView;
import com.aries.ui.view.title.TitleBarView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.leancloud.circle.R;
import com.leancloud.circle.adapter.DetailsCommitAdapter;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;

import java.util.Date;
import java.util.List;

import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.bus.CommunityEvent;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.plog;
import tech.com.commoncore.utils.DateUtil;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.manager.ModelPathManager.circle_details;
import static tech.com.commoncore.utils.DateUtil.FORMAT_5;

@Route(path = circle_details)
public class CircleDetailsActivity extends BaseTitleActivity {

    private ImageView headView;
    private TextView userNameTx, dateTx, contentTx, collectionTx, commentTx, likeTx;
    private EditText commmitEd;
    private RadiusTextView submitTx;
    private String communityId;

    private LinearLayout communityContentlayout;

    private DetailsCommitAdapter commitAdapter;
    private RecyclerView commitRecyclerView;

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("发现详情")
                .setTextColor(Color.WHITE)
                .setLeftTextDrawable(R.mipmap.back_white)
                .setBgDrawable(getResources().getDrawable(R.drawable.bg_title_gradient))
                .setStatusBarLightMode(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_circle_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        headView = findViewById(R.id.circle_details_user_image);
        userNameTx = findViewById(R.id.circle_details_user_name);
        dateTx = findViewById(R.id.circle_details_user_name);
        contentTx = findViewById(R.id.circle_details_user_name);
        collectionTx = findViewById(R.id.circle_details_user_name);
        commentTx = findViewById(R.id.circle_details_user_name);
        likeTx = findViewById(R.id.circle_details_user_name);
        commmitEd = findViewById(R.id.et_content);
        submitTx = findViewById(R.id.tv_send);
        commitRecyclerView = findViewById(R.id.circle_details_commit_recycler);
        communityContentlayout = findViewById(R.id.community_content_layout);

        communityId = getIntent().getStringExtra("communityId");
        if (communityId != null) {
            plog.paly("有数据");
            requestData();
        }

        commitAdapter = new DetailsCommitAdapter(R.layout.circle_layout_details_comment_item);
        commitRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commitRecyclerView.setAdapter(commitAdapter);

        submitTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commit = commmitEd.getText().toString();
                if (commit.isEmpty()) {
                    ToastUtil.show("内容不能为空");
                } else {
                    AVDbGlobal.getInstance().getAVDb().publishComment(communityId, commit, new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {

                            } else {
                                ToastUtil.show("评论未成功");
                            }
                        }
                    });
                }
            }
        });
    }

    private void requestData() {
        AVQuery<AVObject> avQuery = new AVQuery<>("Todo");
        avQuery.getInBackground(communityId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    setViewData(avObject);
                    communityContentlayout.setVisibility(View.VISIBLE);
                } else {
                    communityContentlayout.setVisibility(View.GONE);
                }
            }
        });
        AVDbGlobal.getInstance().getAVDb().requestComment(communityId, new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    commitAdapter.replaceData(list);
                    commitAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setViewData(AVObject object) {
        String headUrl = String.valueOf(object.get(COMMUNITY_USER_HEAD));
        String nike = String.valueOf(object.get(COMMUNITY_NAME));
        Date date = object.getCreatedAt();
        String dateStr = DateUtil.formatDate(date, FORMAT_5);
        String userId = String.valueOf(object.get(COMMUNITY_USER));
        String content = String.valueOf(object.get(COMMUNITY_CONTENT));
        String imgStr = String.valueOf(object.get(COMMUNITY_IMG_LIST));
        String[] image = imgStr.split(",");
        String collectionCount = String.valueOf(object.get(COMMUNITY_COLLECTION_COUNT));
        String commitCount = String.valueOf(object.get(COMMUNITY_COMMENT_COUNT));
        String likeCount = String.valueOf(object.get(COMMUNITY_LIKE_COUNT));

        GlideManager.loadRoundImg(headUrl, headView);
        userNameTx.setText(object.get(COMMUNITY_NAME).toString());
        dateTx.setText(dateStr);
        contentTx.setText(content);
        collectionTx.setText(collectionCount);
        commentTx.setText(commitCount);
        likeTx.setText(likeCount);
    }
}
