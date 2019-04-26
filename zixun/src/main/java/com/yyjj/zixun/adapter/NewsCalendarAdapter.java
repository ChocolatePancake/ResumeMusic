package com.yyjj.zixun.adapter;

import android.content.res.Resources;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yyjj.zixun.R;

import tech.com.commoncore.newsModel.CalendarNews;

public class NewsCalendarAdapter extends BaseQuickAdapter<CalendarNews, BaseViewHolder> {


    public NewsCalendarAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CalendarNews item) {
        helper.setText(R.id.calendar_item_time_text, item.predicttime)
                .setText(R.id.calendar_item_title_text, item.title)
                .setText(R.id.calendar_item_city_text, item.country)
                .setText(R.id.calendar_item_importance_text, item.effect)
                .setText(R.id.calendar_item_previous_text, "前值: " + item.previous)
                .setText(R.id.calendar_item_reality_text, "公布: " + item.reality)
                .setText(R.id.calendar_item_forecast_text, "预期: " + item.forecast);

        RatingBar ratingBar = helper.itemView.findViewById(R.id.calendar_item_rating_bar);
        ratingBar.setRating(item.importance);

        TextView effectText = helper.itemView.findViewById(R.id.calendar_item_importance_text);
        Resources res = mContext.getResources();

        FrameLayout bg = helper.itemView.findViewById(R.id.calendar_item_importance_bg);
        if (item.importance == 0 || item.importance == 1) {
            effectText.setTextColor(res.getColor(R.color.zixun_color_importance_1));
            bg.setBackgroundColor(res.getColor(R.color.zixun_color_importance_1));
        }
        if (item.importance == 2) {
            effectText.setTextColor(res.getColor(R.color.zixun_color_importance_2));
            bg.setBackgroundColor(res.getColor(R.color.zixun_color_importance_2));
        }
        if (item.importance == 3) {
            effectText.setTextColor(res.getColor(R.color.zixun_color_importance_3));
            bg.setBackgroundColor(res.getColor(R.color.zixun_color_importance_3));
        }
        if (item.importance == 4) {
            effectText.setTextColor(res.getColor(R.color.zixun_color_importance_4));
            bg.setBackgroundColor(res.getColor(R.color.zixun_color_importance_4));
        }
        if (item.importance >= 5) {
            effectText.setTextColor(res.getColor(R.color.zixun_color_importance_5));
            bg.setBackgroundColor(res.getColor(R.color.zixun_color_importance_5));
        }
    }
}
