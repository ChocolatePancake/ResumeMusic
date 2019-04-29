package com.leancloud.circle.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.circle.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tech.com.commoncore.avdb.AVDbGlobal;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.DateUtil;
import tech.com.commoncore.utils.ToastUtil;

import static tech.com.commoncore.avdb.AVDbManager.ATTENTION_DELETE_FLAG;
import static tech.com.commoncore.avdb.AVDbManager.ATTENTION_OBSERVED_USER;
import static tech.com.commoncore.avdb.AVDbManager.ATTENTION_USER;
import static tech.com.commoncore.avdb.AVDbManager.COMMUNITY_CONTENT;
import static tech.com.commoncore.avdb.AVDbManager.COMMUNITY_IMG_LIST;
import static tech.com.commoncore.avdb.AVDbManager.COMMUNITY_NAME;
import static tech.com.commoncore.avdb.AVDbManager.COMMUNITY_USER;
import static tech.com.commoncore.avdb.AVDbManager.COMMUNITY_USER_HEAD;
import static tech.com.commoncore.avdb.AVDbManager.TABLE_ATTENTION;
import static tech.com.commoncore.utils.DateUtil.FORMAT_5;

public class LikeAndCollectionAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {
    private List<AVObject> myAttentionList = new ArrayList<>();    //关注对象集合

    public void setAttentionList(List<AVObject> attentions) {
        this.myAttentionList.clear();
        this.myAttentionList.addAll(attentions);
    }

    public LikeAndCollectionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AVObject item) {
        String headUrl = String.valueOf(item.get(COMMUNITY_USER_HEAD));
        String nike = String.valueOf(item.get(COMMUNITY_NAME));
        Date date = item.getCreatedAt();
        String dateStr = DateUtil.formatDate(date, FORMAT_5);
        String userId = String.valueOf(item.get(COMMUNITY_USER));
        String content = String.valueOf(item.get(COMMUNITY_CONTENT));
        String imgStr = String.valueOf(item.get(COMMUNITY_IMG_LIST));

        ImageView head = helper.itemView.findViewById(R.id.circle_item_head_image);
        GlideManager.loadRoundImg(headUrl, head);
        final TextView attentionTx = helper.itemView.findViewById(R.id.circle_item_attention_text);

        helper.setText(R.id.circle_item_name_text, nike);
        helper.setText(R.id.circle_item_date_text, dateStr);
        helper.setText(R.id.circle_item_content_text, content);

        attentionTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AVUser.getCurrentUser() == null) {
                    ToastUtil.show("您还没有登录哦");
                    return;
                }
                boolean isAttention = getAttentionOb((String) item.get(ATTENTION_OBSERVED_USER)) != null;
                handlerAttentionClick(attentionTx, isAttention, item);
            }
        });

        if (getAttentionOb(userId) != null) {
            handlerAttentionView(attentionTx, true);
        } else {
            handlerAttentionView(attentionTx, false);
        }
    }

    private void handlerAttentionClick(TextView tx, boolean isAttention, AVObject object) {
        String circleUserId = (String) object.get(COMMUNITY_USER);
        if (!isAttention) {
            AVObject attention = new AVObject(TABLE_ATTENTION);
            attention.put(ATTENTION_USER, AVUser.getCurrentUser().getObjectId());
            attention.put(ATTENTION_OBSERVED_USER, circleUserId);
            attention.put(ATTENTION_DELETE_FLAG, 0);
            myAttentionList.add(attention);
            AVDbGlobal.getInstance().getAVDb().addAttention(attention, null);
            handlerAttentionView(tx, true);
        } else {
            AVDbGlobal.getInstance().getAVDb().deleteAttention(circleUserId, null);
            for (int i = 0; i < myAttentionList.size(); i++) {
                AVObject attention = myAttentionList.get(i);
                String obUserId = (String) attention.get(ATTENTION_OBSERVED_USER);
                if (obUserId.equals(circleUserId)) {
                    myAttentionList.remove(i);
                }
            }
            handlerAttentionView(tx, false);
        }
    }

    private AVObject getAttentionOb(String obUserId) {
        if (myAttentionList == null) {
            return null;
        }
        for (AVObject object : myAttentionList) {
            String userId = (String) object.get(ATTENTION_OBSERVED_USER);
            if (userId == null) {
                return null;
            }
            if (userId.equals(obUserId)) {
                return object;
            }
            return null;
        }
        return null;
    }

    private void handlerAttentionView(TextView tx, boolean isAttention) {
        if (isAttention) {
            tx.setText("已关注");
            tx.setBackground(mContext.getResources().getDrawable(R.drawable.circle_bg_attention));
        } else {
            tx.setText("关注 +");
            tx.setBackground(mContext.getResources().getDrawable(R.drawable.circle_bg_un_attention));
        }
    }
}
