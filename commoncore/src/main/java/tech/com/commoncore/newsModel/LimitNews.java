package tech.com.commoncore.newsModel;

import java.io.Serializable;

public class LimitNews implements Serializable {
    public int id;
    public int post_id;
    public int category_id;
    public int list_order;
    public int status;
    public String post_title;
    public String post_content;
    public String published_time;
    public String more;
    public int post_like;
    public int comment_count;
    public String post_source;
    public String post_excerpt;
    public int post_hits;
    public String post_content_filtered;

    public LimitNews(
            int id,
            int post_id,
            int category_id,
            int list_order,
            int status,
            String post_title,
            String post_content,
            String published_time,
            String more,
            int post_like,
            int comment_count,
            String post_source,
            String post_excerpt,
            int post_hits,
            String post_content_filtered
    ) {
        this.id = id;
        this.post_id = post_id;
        this.category_id = category_id;
        this.list_order = list_order;
        this.status = status;
        this.post_title = post_title;
        this.post_content = post_content;
        this.published_time = published_time;
        this.more = more;
        this.post_like = post_like;
        this.comment_count = comment_count;
        this.post_source = post_source;
        this.post_excerpt = post_excerpt;
        this.post_hits = post_hits;
        this.post_content_filtered = post_content_filtered;
    }

    @Override
    public boolean equals(Object obj) {
        PageNews t = (PageNews) obj;
        return this.id == t.id;
    }
}
