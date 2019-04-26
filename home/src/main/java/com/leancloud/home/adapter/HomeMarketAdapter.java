package com.leancloud.home.adapter;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leancloud.home.R;

import tech.com.commoncore.futuresModel.Contract;
import tech.com.commoncore.manager.ModelPathManager;
import tech.com.commoncore.utils.DataUtils;
import tech.com.commoncore.utils.FastUtil;

import static tech.com.commoncore.manager.ModelPathManager.futures_chart;

public class HomeMarketAdapter extends BaseQuickAdapter<Contract, BaseViewHolder> {

    public HomeMarketAdapter(int layoutResId) {
        super(layoutResId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void convert(BaseViewHolder helper, final Contract item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contract", item);
                ARouter.getInstance().build(futures_chart).with(bundle).navigation();
//                ChartActivity.jumpActivity(mContext, item);
            }
        });

        helper.setText(R.id.contract_item_name_text, item.transactionName)
                .setText(R.id.contract_item_id_text, item.transactionId)
                .setText(R.id.contract_item_last_price_text, item.lastPrice)
                .setText(R.id.contract_item_extent_text, DataUtils.getAmountValue(item.extent) + "%");

        TextView priceText = helper.itemView.findViewById(R.id.contract_item_last_price_text);
        TextView extentText = helper.itemView.findViewById(R.id.contract_item_extent_text);

        if (item.extent > 0) {
            priceText.setTextColor(mContext.getColor(R.color.red));
            extentText.setTextColor(mContext.getColor(R.color.red));
//            extentText.setBackground(mContext.getDrawable(R.drawable.futures_market_bg_item_up));
        }

        if (item.extent <= 0) {
            priceText.setTextColor(mContext.getColor(R.color.green));
            extentText.setTextColor(mContext.getColor(R.color.green));
//            extentText.setBackground(mContext.getDrawable(R.drawable.futures_market_bg_item_low));
        }

        if (item.extent > 10 || item.extent < -10) {
            priceText.setTextColor(mContext.getColor(R.color.colorLoadMoreText));
            extentText.setTextColor(mContext.getColor(R.color.colorLoadMoreText));
//            extentText.setBackground(mContext.getDrawable(R.drawable.futures_market_bg_item_invalid));
            extentText.setText("-  -");
        }
    }
}
