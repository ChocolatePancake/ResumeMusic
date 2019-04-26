package com.yyjj.zixun.entity;

import java.io.Serializable;

/**
 * Anthor:NiceWind
 * Time:2019/3/7
 * Desc:The ladder is real, only the climb is all.
 */
public class ArticalEntity implements Serializable{


    /**
     * id : 1431
     * post_id : 1430
     * category_id : 25
     * list_order : 10000
     * status : 1
     * post_title : 比特币跌破5000*，李笑来张健销声匿迹，陈伟星说该认错了
     * post_content : 在跌破6000*关口仅五天后，比特币再次发生暴跌，击穿5000*关口。
     * published_time : 2018-11-21 17:58:00
     * more : http://i1.go2yd.com/image.php?url=0KcvHeflHP
     * post_like : 255
     * comment_count : 1
     * post_source :
     * post_excerpt :
     * post_hits : 239
     * post_content_filtered :
     */

    private int id;
    private int post_id;
    private int category_id;
    private int list_order;
    private int status;
    private String post_title;
    private String post_content;
    private String published_time;
    private String more;
    private int post_like;
    private int comment_count;
    private String post_source;
    private String post_excerpt;
    private int post_hits;
    private String post_content_filtered;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getList_order() {
        return list_order;
    }

    public void setList_order(int list_order) {
        this.list_order = list_order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPublished_time() {
        return published_time;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public int getPost_like() {
        return post_like;
    }

    public void setPost_like(int post_like) {
        this.post_like = post_like;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getPost_source() {
        return post_source;
    }

    public void setPost_source(String post_source) {
        this.post_source = post_source;
    }

    public String getPost_excerpt() {
        return post_excerpt;
    }

    public void setPost_excerpt(String post_excerpt) {
        this.post_excerpt = post_excerpt;
    }

    public int getPost_hits() {
        return post_hits;
    }

    public void setPost_hits(int post_hits) {
        this.post_hits = post_hits;
    }

    public String getPost_content_filtered() {
        return post_content_filtered;
    }

    public void setPost_content_filtered(String post_content_filtered) {
        this.post_content_filtered = post_content_filtered;
    }
}
