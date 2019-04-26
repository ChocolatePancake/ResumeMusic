package cn.control.c.com.ccontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerView extends TextView implements View.OnClickListener {
    private static final int TIME_TICK = 1;
    private static final int MILLISECOND = 1000;
    private int TIME = 0;
    private int realTime = 0;
    private boolean clickable = true;
    private String startText = "";
    private String endText = "";

    private OnTimeClick onTimeClick;

    private Timer timer;
    private TimerTask timerTask;

    public TimerView(Context context) {
        super(context);
        into(context, null);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        into(context, attrs);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        into(context, attrs);
    }

    private void into(Context context, AttributeSet attrs) {
        setOnClickListener(this);
        startText = getText().toString();
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
            TIME = ta.getInt(R.styleable.TimerView_time, 0) * MILLISECOND;
            String et = ta.getString(R.styleable.TimerView_endText);
            if (et == null || et.isEmpty()) {
                endText = startText;
            } else {
                endText = et;
            }
        }
    }

    @Override
    public void onClick(View v) {
        handlerOnClick();
    }

    private void handlerOnClick() {
        if (onTimeClick != null && clickable) {
            if (!onTimeClick.onTimeClick())
                return;
        }
        if (!clickable) {
            return;
        }
        clickable = false;
        handlerTimer();
    }


    private synchronized void handlerTimer() {
        realTime = TIME;
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handlerInstantTask();
                }
            };
            timer.schedule(timerTask, 0, MILLISECOND);
        }
    }

    private void handlerInstantTask() {
        if (realTime <= 0) {
            clickable = true;
            setEndText();
            cancelTimer();
            return;
        }
        realTime = realTime - TIME_TICK * 1000;
        setTime();
    }

    private void cancelTimer() {
        timerTask.cancel();
        timer.cancel();
        timerTask = null;
        timer = null;
    }


    private void setTime() {
        setTimeText(realTime / MILLISECOND + "s");
    }

    private void setEndText() {
        setTimeText(endText);
    }

    public void setTimeText(CharSequence text) {
        setText(text, BufferType.NORMAL);
    }

    public void setOnTimeClick(OnTimeClick click) {
        this.onTimeClick = click;
    }

    public abstract static class OnTimeClick {
        public abstract boolean onTimeClick();
    }
}
