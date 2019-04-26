package com.leancloud.circle.adapter;

import android.widget.ImageView;

import com.avos.avoscloud.AVObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.circle.R;

import java.util.Date;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.DateUtil;

import static tech.com.commoncore.avdb.AVDbManager.*;
import static tech.com.commoncore.utils.DateUtil.FORMAT_5;

public class DetailsCommitAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {


    public DetailsCommitAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AVObject item) {
        String haedUrl = (String) item.get(COMMENT_HEAD);
        Date date = item.getCreatedAt();
        String dateStr = DateUtil.formatDate(date, FORMAT_5);
        String name = (String) item.get(COMMENT_NAME);
        String content = (String) item.get(COMMENT_CONTENT);

        ImageView haedView = helper.itemView.findViewById(R.id.commit_item_user_head_image);
        GlideManager.loadRoundImg(haedUrl, haedView);

        helper.setText(R.id.commit_item_user_name, name)
                .setText(R.id.commit_item_date, dateStr)
                .setText(R.id.commit_item_content, content);
    }
}
