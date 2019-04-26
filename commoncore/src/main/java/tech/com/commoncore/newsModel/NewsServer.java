package tech.com.commoncore.newsModel;

import android.util.Log;

import com.vise.xsnow.event.BusManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tech.com.commoncore.constant.ApiConstant;

public class NewsServer {
    private static NewsServer newsServer;

    public static NewsServer getInstance() {
        synchronized (NewsServer.class) {
            if (newsServer == null)
                return new NewsServer();
            return newsServer;
        }
    }

    /**
     * =============================================================================================
     * =================================public function=============================================
     * =============================================================================================
     */

    /**
     * 请求分页新闻资讯
     *
     * @param type
     * @param page
     */
    public void requestPageNews(int type, int page) {
        requestPageNews(String.valueOf(type), String.valueOf(page));
    }

    /**
     * 请求财经日历
     *
     * @param year
     * @param monthday
     */
    public void requestCalendarNews(String year, String monthday) {
        requestCalendarNewspr(year, monthday);
    }

    /**
     * =============================================================================================
     * =================================private function============================================
     * =============================================================================================
     */
    private void requestCalendarNewspr(String year, String monthday) {
        ViseHttp.GET(ApiConstant.CJRL).baseUrl(ApiConstant.BASE_URL_FH7H)
                .addParam("year", year)
                .addParam("monthday", monthday)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonData = new JSONObject(data);
                            if (jsonData.getInt("code") != 0) {
                                JSONArray ja = jsonData.getJSONArray("data");
                                analysisDataBuildCalendarNews(ja);
                                BusManager.getBus().post(new CalendarNewsEvent(1, analysisDataBuildCalendarNews(ja)));
                            }
                            BusManager.getBus().post(new CalendarNewsEvent("数据异常"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        BusManager.getBus().post(new CalendarNewsEvent(errMsg));
                    }
                });
    }

    private ArrayList<CalendarNews> analysisDataBuildCalendarNews(JSONArray jsonArray) throws JSONException {
        ArrayList<CalendarNews> calendarNews = new ArrayList<>();
        int index = jsonArray.length();
        for (int i = 0; i < index; i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            CalendarNews calendar = new CalendarNews(
                    object.getInt("economicCalId"),
                    object.getString("economicDate"),
                    object.getString("previous"),
                    object.getString("unit"),
                    object.getInt("importance"),
                    object.getString("publishDt"),
                    object.getString("country"),
                    object.getString("predicttime"),
                    object.getString("forecast"),
                    object.getString("reality"),
                    object.getString("title"),
                    object.getString("effect"),
                    object.getString("createDt"),
                    object.getString("outId"),
                    object.getString("kuaixunOutId")
            );
            calendarNews.add(calendar);
        }
        return calendarNews;
    }

    private void requestPageNews(String requestType, String requestPage) {
        ViseHttp.GET(ApiConstant.ARTICLE_LIST_PAGE).baseUrl(ApiConstant.BASE_URL_FH7H)
                .addParam("category", requestType)
                .addParam("page", requestPage)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            if (code == 1) {
                                BusManager.getBus().post(new PageNewsEvent(1, analysisDataBuildPageNews(jsonObject)));
                            } else {
                                BusManager.getBus().post(new PageNewsEvent("数据异常"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        BusManager.getBus().post(new PageNewsEvent(errMsg));
                    }
                });
    }


    private ArrayList<PageNews> analysisDataBuildPageNews(JSONObject json) throws JSONException {
        JSONObject dataJson = json.getJSONObject("data");
        JSONArray dataArray = dataJson.getJSONArray("data");
        ArrayList<PageNews> pageNews = new ArrayList<>();
        int jbSize = dataArray.length();
        for (int i = 0; i < jbSize; i++) {
            JSONObject jsonObject = dataArray.getJSONObject(i);
            JSONObject moreJb = jsonObject.getJSONObject("more");
            PageNews news = new PageNews(
                    jsonObject.getInt("id"),
                    jsonObject.getInt("parent_id"),
                    jsonObject.getInt("post_type"),
                    jsonObject.getInt("post_format"),
                    jsonObject.getInt("user_id"),
                    jsonObject.getInt("post_status"),
                    jsonObject.getInt("comment_status"),
                    jsonObject.getInt("is_top"),
                    jsonObject.getInt("recommended"),
                    jsonObject.getInt("post_hits"),
                    jsonObject.getInt("post_like"),
                    jsonObject.getInt("comment_count"),
                    jsonObject.getInt("create_time"),
                    jsonObject.getInt("update_time"),
                    jsonObject.getString("published_time"),
                    jsonObject.getInt("delete_time"),
                    jsonObject.getString("post_title"),
                    jsonObject.getString("post_keywords"),
                    jsonObject.getString("post_excerpt"),
                    jsonObject.getString("post_source"),
                    jsonObject.getString("post_content"),
                    jsonObject.getString("post_content_filtered"),
                    moreJb.getString("thumbnail"),
                    moreJb.getString("template"),
                    jsonObject.getString("article_flag"),
                    jsonObject.getInt("aid"),
                    jsonObject.getInt("post_category_id"),
                    jsonObject.getInt("list_order"),
                    jsonObject.getInt("category_id"),
                    jsonObject.getString("user_login"),
                    jsonObject.getString("user_nickname"),
                    jsonObject.getString("user_email")
            );
            pageNews.add(news);
        }
        return pageNews;
    }
}
