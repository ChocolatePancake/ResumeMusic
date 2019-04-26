package com.yyjj.my.entity;

import com.avos.avoscloud.AVUser;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yyjj.my.adapter.OnLineServiceListAdapter;

/**
 * Author:ChenPengBo
 * Date:2018/10/30
 * Desc:
 * Version:1.0
 */
public class OnLineServiceMsg implements MultiItemEntity {

    public long time;
    public AVUser user;
    public String content;
    public boolean isReply;

    public OnLineServiceMsg(long time, AVUser user, String content, boolean isReply) {
        this.time = time;
        this.user = user;
        this.content = content;
        this.isReply = isReply;
    }

    @Override
    public int getItemType() {
        if (isReply) {
            return OnLineServiceListAdapter.TYPE_REPLY;
        } else {
            return OnLineServiceListAdapter.TYPE_SEND;
        }
    }
}
