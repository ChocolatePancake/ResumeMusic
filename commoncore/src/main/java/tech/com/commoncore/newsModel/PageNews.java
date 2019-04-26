package tech.com.commoncore.newsModel;

import java.io.Serializable;

public class PageNews implements Serializable {
    public int id;
    public int parent_id;
    public int post_type;
    public int post_format;
    public int user_id;
    public int post_status;
    public int comment_status;
    public int is_top;
    public int recommended;
    public int post_hits;
    public int post_like;
    public int comment_count;
    public int create_time;
    public int update_time;
    public String published_time;
    public int delete_time;
    public String post_title;
    public String post_keywords;
    public String post_excerpt;
    public String post_source;
    public String post_content;
    public String post_content_filtered;
    public String thumbnail;
    public String template;
    public String article_flag;
    public int aid;
    public int post_category_id;
    public int list_order;
    public int category_id;
    public String user_login;
    public String user_nickname;
    public String user_email;

    public PageNews() {

    }

    public PageNews(
            int id,
            int parent_id,
            int post_type,
            int post_format,
            int user_id,
            int post_status,
            int comment_status,
            int is_top,
            int recommended,
            int post_hits,
            int post_like,
            int comment_count,
            int create_time,
            int update_time,
            String published_time,
            int delete_time,
            String post_title,
            String post_keywords,
            String post_excerpt,
            String post_source,
            String post_content,
            String post_content_filtered,
            String thumbnail,
            String template,
            String article_flag,
            int aid,
            int post_category_id,
            int list_order,
            int category_id,
            String user_login,
            String user_nickname,
            String user_email
    ) {
        this.id = id;
        this.parent_id = parent_id;
        this.post_type = post_type;
        this.post_format = post_format;
        this.user_id = user_id;
        this.post_status = post_status;
        this.comment_status = comment_status;
        this.is_top = is_top;
        this.recommended = recommended;
        this.post_hits = post_hits;
        this.post_like = post_like;
        this.comment_count = comment_count;
        this.create_time = create_time;
        this.update_time = update_time;
        this.published_time = published_time;
        this.delete_time = delete_time;
        this.post_title = post_title;
        this.post_keywords = post_keywords;
        this.post_excerpt = post_excerpt;
        this.post_source = post_source;
        this.post_content = post_content;
        this.post_content_filtered = post_content_filtered;
        this.thumbnail = thumbnail;
        this.template = template;
        this.article_flag = article_flag;
        this.aid = aid;
        this.post_category_id = post_category_id;
        this.list_order = list_order;
        this.category_id = category_id;
        this.user_login = user_login;
        this.user_nickname = user_nickname;
        this.user_email = user_email;
    }

    @Override
    public boolean equals(Object obj) {
        PageNews t = (PageNews) obj;
        return this.id == t.id;
    }
}
