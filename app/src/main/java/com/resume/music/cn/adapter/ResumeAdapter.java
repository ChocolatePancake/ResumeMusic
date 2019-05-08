package com.resume.music.cn.adapter;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import tech.com.commoncore.plog;

import static tech.com.commoncore.manager.ModelPathManager.main_editResume;

public class ResumeAdapter extends BaseQuickAdapter<AVObject, BaseViewHolder> {


    public ResumeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AVObject item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plog.paly("点击了这里");
                ARouter.getInstance().build(main_editResume)
                        .withString("myResumeId", item.getObjectId())
                        .navigation();
            }
        });
    }
}
