package com.resume.music.cn.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.resume.music.cn.R;

import java.util.Date;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.DateUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.utils.DateUtil.FORMAT_3;

public class MusicAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {


    public MusicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AVObject item) {
        AVUser avUser = item.getAVUser(TARGET_AVUSER);

        String name = avUser.get(USER_NICK_NAME).toString();
        String headUrl = avUser.get(USER_HEAD_ICON).toString();

        String musicName = item.get(MEDIA_FILE_NAME).toString();
        String fileUrl = item.get(MEDIA_FILE).toString();
        int likeCount = Integer.valueOf(item.get(MEDIA_LIKE_COUNT) + "");
        int commentCount = Integer.valueOf(item.get(MEDIA_COMMENT_COUNT) + "");
        Date date = item.getCreatedAt();

        String time = DateUtil.formatDate(date, FORMAT_3);

        helper.setText(R.id.music_item_name, musicName);
        helper.setText(R.id.music_item_user_name, name + " - " + musicName);
        helper.setText(R.id.music_item_like, likeCount + "");
        helper.setText(R.id.music_item_date, time);

        ImageView imageView = helper.itemView.findViewById(R.id.music_item_user_head);
        GlideManager.loadRoundImg(headUrl, imageView,R.mipmap.ic_default_head);

        TextView commentTx = helper.itemView.findViewById(R.id.music_item_commit);
        TextView likeTx = helper.itemView.findViewById(R.id.music_item_like);

        Drawable commentDrawable = mContext.getResources().getDrawable(R.drawable.icon_comment);
        Drawable likeDrawable = mContext.getResources().getDrawable(R.drawable.icon_un_like);

        int size = (int) mContext.getResources().getDimension(R.dimen.dp_12);
        int drawablePadding = (int) mContext.getResources().getDimension(R.dimen.dp_6);
        commentDrawable.setBounds(0, 0, size, size);
        likeDrawable.setBounds(0, 0, size, size);

        commentTx.setCompoundDrawables(commentDrawable, null, null, null);
        commentTx.setCompoundDrawablePadding(drawablePadding);
        likeTx.setCompoundDrawables(likeDrawable, null, null, null);
        likeTx.setCompoundDrawablePadding(drawablePadding);

        likeTx.setText(likeCount + "");
        commentTx.setText(commentCount + "");
    }
}
