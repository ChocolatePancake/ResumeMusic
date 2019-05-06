package com.resume.music.cn.adapter;

import android.widget.ImageView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.resume.music.cn.R;

import tech.com.commoncore.manager.GlideManager;

import static tech.com.commoncore.avdb.AVDbManager.*;

public class PlanAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {

    public PlanAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AVObject item) {
        AVUser avUser = item.getAVUser(TARGET_AVUSER);

        String name = avUser.get(USER_NICK_NAME).toString();
        String headUrl = avUser.get(USER_HEAD_ICON).toString();

        String title = item.get(PLAN_TITLE).toString();
        String content = "策划详情 :\n" + item.get(PLAN_CONTENT).toString();
        String startTime = "开始时间 : " + item.get(PLAN_START_TIME).toString();
        String endTime = "开始时间 : " + item.get(PLAN_END_TIME).toString();

        helper.setText(R.id.plan_item_user_name, name);
        helper.setText(R.id.plan_item_title, title);
        helper.setText(R.id.plan_item_content, content);
        helper.setText(R.id.plan_item_start_tx, startTime);
        helper.setText(R.id.plan_item_end_tx, endTime);

        ImageView headImage = helper.itemView.findViewById(R.id.plan_item_user_head);
        GlideManager.loadRoundImg(headUrl, headImage,R.mipmap.ic_default_head);
    }
}
