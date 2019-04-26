package com.leancloud.circle.entity;

import java.io.Serializable;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class Author2Entity implements Serializable{


    /**
     * id : 3694
     * parent_id : 0
     * post_type : 1
     * post_format : 1
     * user_id : 14
     * post_status : 1
     * comment_status : 1
     * is_top : 0
     * recommended : 0
     * post_hits : 1216
     * post_like : 1230
     * comment_count : 1
     * create_time : 1553503153
     * update_time : 1553582946
     * published_time : 2019-03-25 16:38:00
     * delete_time : 0
     * post_title : 肖玉航
     * post_keywords :
     * post_excerpt :
     * post_source :
     * post_content : 肖玉航，郑州市人，大学文化，高级经济师。获国家证券投资咨询和证券交易两种资格证书。自1994年开始对中国证券市场进行研究，以理性投资为主，注重公司基本面与技术面的结合，经历过中国股市多次的大行情，并把握较好。
     * post_content_filtered : <p><span style="font-family: 宋体; background-color: rgb(255, 255, 255); font-size: 12px;">肖玉航，郑州市人，大学文化，高级经济师。获国家证券投资咨询和证券交易两种资格证书。自1994年开始对中国证券市场进行研究，以理性投资为主，注重公司基本面与技术面的结合，经历过中国股市多次的大行情，并把握较好。</span></p>
     * more : {"thumbnail":"http://news1.jrj.com.cn/news/main/ver0701/images/sp/2008121811213381228.jpg","template":""}
     * article_flag :
     * aid : 0
     * report_status : 0
     * user_login : yladmin
     * user_nickname :
     * user_email : yladmin@tom.com
     */

    private int id;
    private int parent_id;
    private int post_type;
    private int post_format;
    private int user_id;
    private int post_status;
    private int comment_status;
    private int is_top;
    private int recommended;
    private int post_hits;
    private int post_like;
    private int comment_count;
    private int create_time;
    private int update_time;
    private String published_time;
    private int delete_time;
    private String post_title;
    private String post_keywords;
    private String post_excerpt;
    private String post_source;
    private String post_content;
    private String post_content_filtered;
    private MoreBean more;
    private String article_flag;
    private int aid;
    private int report_status;
    private String user_login;
    private String user_nickname;
    private String user_email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getPost_type() {
        return post_type;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

    public int getPost_format() {
        return post_format;
    }

    public void setPost_format(int post_format) {
        this.post_format = post_format;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_status() {
        return post_status;
    }

    public void setPost_status(int post_status) {
        this.post_status = post_status;
    }

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public int getPost_hits() {
        return post_hits;
    }

    public void setPost_hits(int post_hits) {
        this.post_hits = post_hits;
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getPublished_time() {
        return published_time;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
    }

    public int getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(int delete_time) {
        this.delete_time = delete_time;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_keywords() {
        return post_keywords;
    }

    public void setPost_keywords(String post_keywords) {
        this.post_keywords = post_keywords;
    }

    public String getPost_excerpt() {
        return post_excerpt;
    }

    public void setPost_excerpt(String post_excerpt) {
        this.post_excerpt = post_excerpt;
    }

    public String getPost_source() {
        return post_source;
    }

    public void setPost_source(String post_source) {
        this.post_source = post_source;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_content_filtered() {
        return post_content_filtered;
    }

    public void setPost_content_filtered(String post_content_filtered) {
        this.post_content_filtered = post_content_filtered;
    }

    public MoreBean getMore() {
        return more;
    }

    public void setMore(MoreBean more) {
        this.more = more;
    }

    public String getArticle_flag() {
        return article_flag;
    }

    public void setArticle_flag(String article_flag) {
        this.article_flag = article_flag;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getReport_status() {
        return report_status;
    }

    public void setReport_status(int report_status) {
        this.report_status = report_status;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public static class MoreBean implements Serializable{
        /**
         * thumbnail : http://news1.jrj.com.cn/news/main/ver0701/images/sp/2008121811213381228.jpg
         * template :
         */

        private String thumbnail;
        private String template;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }
    }
}
