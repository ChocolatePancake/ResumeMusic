package tech.com.commoncore.newsModel;

import java.io.Serializable;

public class CalendarNews implements Serializable {
    public int economicCalId;
    public String economicDate;
    public String previous;
    public String unit;
    public int importance;
    public String publishDt;
    public String country;
    public String predicttime;
    public String forecast;
    public String reality;
    public String title;
    public String effect;
    public String createDt;
    public String outId;
    public String kuaixunOutId;

    public CalendarNews() {

    }

    public CalendarNews(
            int economicCalId,
            String economicDate,
            String previous,
            String unit,
            int importance,
            String publishDt,
            String country,
            String predicttime,
            String forecast,
            String reality,
            String title,
            String effect,
            String createDt,
            String outId,
            String kuaixunOutId
    ) {
        this.economicCalId = economicCalId;
        this.economicDate = economicDate;
        this.previous = previous;
        this.unit = unit;
        this.importance = importance;
        this.publishDt = publishDt;
        this.country = country;
        this.predicttime = predicttime;
        this.forecast = forecast;
        this.reality = reality;
        this.title = title;
        this.effect = effect;
        this.createDt = createDt;
        this.outId = outId;
        this.kuaixunOutId = kuaixunOutId;
    }
}
