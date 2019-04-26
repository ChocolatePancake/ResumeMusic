package com.yyjj.zixun.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Desc:
 */
public class VideoEntity implements MultiItemEntity {
    public static final int TYPE_BIG = 1;
    public static final int TYPE_NOMAL = 2;
    public String title,imageResource,onlineUrl;
    public  int type;
    @Override
    public int getItemType() {
        return this.type;
    }


    public VideoEntity(String title, String imageResource, String onlineUrl, int type){
        this.type = type;
        this.title = title;
        this.onlineUrl = onlineUrl;
        this.imageResource = imageResource;
    }

}
