package com.leancloud.circle.entity;

import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Time:2019/1/5
 * Desc:
 */
public class AuthorEntity extends AVObject implements Serializable{


    /**
     * fansCount : 202
     * articleCount : 1
     * name : 赵建华
     * icon : http://lc-hwbnvjc3.cn-n1.lcfile.com/50ed28be091097d29637.jpg
     */

    private int fansCount;
    private int articleCount;
    private String name;
    private String icon;
    private String dec;

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
