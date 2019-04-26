package com.yyjj.zixun.fragment;

import android.icu.text.DateTimePatternGenerator;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.event.Subscribe;
import com.vise.xsnow.event.inner.ThreadMode;
import com.yyjj.zixun.R;
import com.yyjj.zixun.adapter.NewsCalendarAdapter;

import java.util.ArrayList;

import tech.com.commoncore.base.BaseFragment;
import tech.com.commoncore.newsModel.CalendarNews;
import tech.com.commoncore.newsModel.CalendarNewsEvent;
import tech.com.commoncore.newsModel.NewsServer;

public class NewsCalendarFragment extends BaseFragment {
    private SmartRefreshLayout calendarSmartRefresh;
    private RecyclerView calendarRecyclerView;

    private CalendarView calendarView;
    private TextView moneyText;

    private NewsCalendarAdapter newsCalendarAdapter;

    private ArrayList<CalendarNews> calendarNews = new ArrayList<>();

    private Calendar mCalendar;

    public static NewsCalendarFragment newInstance() {
        NewsCalendarFragment fragment = new NewsCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_calendar;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        calendarRecyclerView = mContentView.findViewById(R.id.news_calendar_recycler_view);
        calendarSmartRefresh = mContentView.findViewById(R.id.news_calendar_smart_refresh);
        calendarView = mContentView.findViewById(R.id.calendar_news_calendar_view);
        moneyText = mContentView.findViewById(R.id.calendar_news_money_text);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.zixun_news_recycler_divider));
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        calendarRecyclerView.addItemDecoration(dividerItemDecoration);
        newsCalendarAdapter = new NewsCalendarAdapter(R.layout.zixun_layout_item_calendar);
        calendarRecyclerView.setAdapter(newsCalendarAdapter);

        calendarSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mCalendar != null)
                    requestCalendar(mCalendar.getYear(), mCalendar.getMonth(), mCalendar.getDay());
            }
        });

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                moneyText.setText(String.valueOf(month) + "月");
            }
        });
        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                mCalendar = calendar;
                calendarSmartRefresh.autoRefresh();
            }
        });
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD)
    public void CalendarNewsEvent(CalendarNewsEvent event) {
        calendarSmartRefresh.finishRefresh();
        if (event.code != 0) {
            calendarNews.clear();
            calendarNews.addAll(event.calendarNews);
            newsCalendarAdapter.replaceData(calendarNews);
            newsCalendarAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {

    }

    private void requestCalendar(int year, int money, int day) {
        NewsServer.getInstance().requestCalendarNews(String.valueOf(year), String.valueOf(money) + String.valueOf(day));
    }
}
