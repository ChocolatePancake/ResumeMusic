package com.resume.music.cn.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.resume.music.cn.R;
import com.resume.music.cn.entity.LoopBean;

public class HomeColumnAdapter extends BaseQuickAdapter<LoopBean, BaseViewHolder> {

    private OnClickItem onClickItem;

    public HomeColumnAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, LoopBean item) {
        ImageView imageView = helper.itemView.findViewById(R.id.home_column_item_image);
        helper.setText(R.id.home_column_item_text, item.text);
        imageView.setImageResource(item.img);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem != null) {
                    onClickItem.clickItem(helper.getLayoutPosition());
                }
            }
        });
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public static abstract class OnClickItem {
        public abstract void clickItem(int position);
    }
}
