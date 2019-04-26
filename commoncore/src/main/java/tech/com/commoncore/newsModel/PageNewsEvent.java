package tech.com.commoncore.newsModel;

import com.vise.xsnow.event.IEvent;

import java.util.ArrayList;

public class PageNewsEvent implements IEvent {
    public int code = 0;
    public ArrayList<PageNews> pageNews;
    public String errMgs;

    public PageNewsEvent(int code, ArrayList<PageNews> pageNews) {
        this.code = code;
        this.pageNews = pageNews;
    }

    public PageNewsEvent(String msg) {
        this.errMgs = msg;
    }
}
