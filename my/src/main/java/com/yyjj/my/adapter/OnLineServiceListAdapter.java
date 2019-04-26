package com.yyjj.my.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyjj.my.R;
import com.yyjj.my.db.AVDbManager;
import com.yyjj.my.entity.OnLineServiceMsg;

import java.util.ArrayList;
import java.util.Date;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.DateUtil;

/**
 * Author:ChenPengBo
 * Date:2018/10/30
 * Desc:
 * Version:1.0
 */
public class OnLineServiceListAdapter extends BaseMultiItemQuickAdapter<OnLineServiceMsg, BaseViewHolder> {

    public static final int TYPE_SEND = 100;
    public static final int TYPE_REPLY = 200;

    public OnLineServiceListAdapter() {
        super(new ArrayList<OnLineServiceMsg>());
        addItemType(TYPE_SEND, R.layout.my_item_send_msg);
        addItemType(TYPE_REPLY, R.layout.my_item_reply_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, OnLineServiceMsg item) {
        switch (item.getItemType()) {
            case TYPE_SEND:
                helper.setText(R.id.tv_send_time, DateUtil.formatDate(new Date(item.time), DateUtil.FORMAT_9));
                helper.setText(R.id.tv_send_msg, item.content);
                ImageView ivSender = helper.getView(R.id.iv_sender);
                GlideManager.loadCircleImg(item.user.getString(AVDbManager.USER_HEAD_ICON), ivSender);
                break;
            case TYPE_REPLY:
                helper.setText(R.id.tv_reply_time, DateUtil.formatDate(new Date(item.time), DateUtil.FORMAT_9));
                helper.setText(R.id.tv_reply_msg, item.content);
                ImageView ivReplyer = helper.getView(R.id.iv_replyer);
                GlideManager.loadCircleImg(R.mipmap.my_ic_launcher_round, ivReplyer);
                break;
        }

    }
}
