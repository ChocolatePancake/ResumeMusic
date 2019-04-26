package tech.com.commoncore.newsModel;

import com.vise.xsnow.event.IEvent;

import java.util.ArrayList;

public class CalendarNewsEvent implements IEvent {
    public int code = 0;
    public ArrayList<CalendarNews> calendarNews;
    public String errMsg;

    public CalendarNewsEvent(int code, ArrayList<CalendarNews> calendarNews) {
        this.code = code;
        this.calendarNews = calendarNews;
    }

    public CalendarNewsEvent(String errMsg) {
        this.errMsg = errMsg;
    }
}
