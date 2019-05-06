package com.resume.music.cn.adapter;

import android.widget.ImageView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.resume.music.cn.R;

import tech.com.commoncore.manager.GlideManager;

import static tech.com.commoncore.avdb.AVDbManager.*;

public class PartAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {


    public PartAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AVObject item) {
        AVUser avUser = item.getAVUser(TARGET_AVUSER);

        String name = avUser.get(USER_NICK_NAME).toString();
        String headUrl = avUser.get(USER_HEAD_ICON).toString();

        String title = item.get(PARTY_TITLE).toString();
        String content = "活动内容: " + item.get(PARTY_CONTENT).toString();
        String startTime = "开始时间: " + item.get(PARTY_START_TIME).toString();
        String endTime = "结束时间: " + item.get(PARTY_END_TIME).toString();
        String address = "活动地点: " + item.get(PARTY_ADDRESS).toString();
        String people = "参与人数: " + item.get(PARTY_PEOPLE).toString();

        helper.setText(R.id.party_item_user_name, name);
        helper.setText(R.id.party_item_title, title);
        helper.setText(R.id.party_item_content, content);
        helper.setText(R.id.party_item_start, startTime);
        helper.setText(R.id.party_item_end, endTime);
        helper.setText(R.id.party_item_address, address);
        helper.setText(R.id.party_item_people, people);

        ImageView headImage = helper.itemView.findViewById(R.id.party_item_user_head);
        GlideManager.loadRoundImg(headUrl, headImage, R.mipmap.ic_default_head);
    }
}
