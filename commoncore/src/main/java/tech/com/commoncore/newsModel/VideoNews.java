package tech.com.commoncore.newsModel;

import java.io.Serializable;

public class VideoNews implements Serializable {
    public String url;          //视频地址
    public String date;         //时间
    public String title;        //标题
    public String volume;       //播放次数

    public VideoNews(
            String url,
            String date,
            String title,
            String volume
    ) {
        this.url = url;
        this.date = date;
        this.title = title;
        this.volume = volume;
    }

    @Override
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof VideoNews) {
            VideoNews t = (VideoNews) obj;
            return this.url.equals(t.url);
        }
        return false;
    }

    public void setVideo(VideoNews videoNews) {
        this.date = videoNews.date;
        this.title = videoNews.title;
        this.volume = videoNews.volume;
    }
}
