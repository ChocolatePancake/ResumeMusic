package com.leancloud.circle.entity;

import java.io.Serializable;

/**
 * Anthor:NiceWind
 * Time:2019/3/26
 * Desc:The ladder is real, only the climb is all.
 */
public class ArticleEntity implements Serializable {


    /**
     * id : 1466
     * post_id : 1467
     * category_id : 27
     * list_order : 10000
     * status : 1
     * post_title : 分析师：黄金逆水行舟 1200*关口或独木难支
     * post_content : 　FX168财经报社（香港）讯 周二亚市盘中，现货黄金震荡交投于1222*水平附近，受*指数不断走强影响，金价的反弹趋势极为疲软，分析师表示，黄金短线很难看到上涨。 　　分析师表示，尽管黄金似乎正处于一个震荡上行通道中，但是这其实是之前一段下跌趋势的修正行情。金价目前录得越来越低的高点，这在很大程度上暗示，黄金的上行动能正在不断耗尽。 一旦金价有效跌穿这一通道，那么将意味着下行走势重新恢复。由于美债收益率不断走高，且美股目前似乎还没有开启持续跌势，因此金价并没有获得持续支撑。 　　此外，尽管市场预期美联储将在2019年放缓加息的步伐，不过，这一消息对*的打压较为短暂，市场目前依旧相信美联储将在年底继续加息一次。 　　晚间，美国将公布谘商会消费者信心指数，料将影响*走势，投资者需密切关注。 　　此外，分析师还指出，黄金投资者当前需保持谨慎，一旦金价开启下跌趋势，那么很可能会危及1200*这一关口，预计金价的下跌趋势短期内不太可能结束。
     * published_time : 2018-11-27 14:26:35
     * more :
     * post_like : 185
     * comment_count : 0
     * post_source :
     * post_excerpt :
     * post_hits : 121
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
