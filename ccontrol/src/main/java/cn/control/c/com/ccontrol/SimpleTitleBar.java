package cn.control.c.com.ccontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleTitleBar extends LinearLayout implements View.OnClickListener {

    ImageView rightIcon, leftIcon;
    TextView titleText, goText;

    public SimpleTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        binderView(context);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PTitleBar);
            setBackIcon(ta.getResourceId(R.styleable.PTitleBar_backIcon, R.drawable.ic_white_back));
            setTitleText(ta.getString(R.styleable.PTitleBar_titleText));
            setGo(ta.getResourceId(R.styleable.PTitleBar_goType, 0),
                    ta.getResourceId(R.styleable.PTitleBar_goIcon, 0),
                    ta.getString(R.styleable.PTitleBar_goText));
            setTitleColor(ta.getColor(R.styleable.PTitleBar_titleColor, Color.WHITE));
            setGoTextColor(ta.getColor(R.styleable.PTitleBar_goTextColor, Color.WHITE));
        }
    }

    private void binderView(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_ptitle_bar, this, true);
        leftIcon = view.findViewById(R.id.bar_back_img);
        rightIcon = view.findViewById(R.id.bar_go_img);
        titleText = view.findViewById(R.id.bar_title_tx);
        goText = view.findViewById(R.id.bar_go_tx);

        view.findViewById(R.id.simple_title_bar_back_view).setOnClickListener(this);
        view.findViewById(R.id.simple_title_bar_go_view).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.simple_title_bar_back_view) {
            if (clickBack != null)
                clickBack.click();

        } else if (i == R.id.simple_title_bar_go_view) {
            if (clickGo != null)
                clickGo.click();

        }
    }

    private OnClickBack clickBack;
    private OnClickGo clickGo;

    public void setClickBack(OnClickBack back) {
        this.clickBack = back;
    }

    public void setClickGo(OnClickGo go) {
        this.clickGo = go;
    }

    public abstract static class OnClickBack {
        public abstract void click();
    }

    public abstract static class OnClickGo {
        public abstract void click();
    }

    public void setBackIcon(int icon) {
        leftIcon.setBackgroundResource(icon);
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }

    public void setGo(int type, int icon, String text) {
        if (type == 0) {
            setGoText(text);
        } else if (type == 1) {
            setGoIcon(icon);
        }
    }

    public void setTitleColor(int color) {
        titleText.setTextColor(color);
    }

    public void setGoTextColor(int color) {
        goText.setTextColor(color);
    }

    private void setGoIcon(int icon) {
        rightIcon.setVisibility(VISIBLE);
        goText.setVisibility(GONE);
        rightIcon.setBackgroundResource(icon);
    }

    private void setGoText(String text) {
        goText.setVisibility(VISIBLE);
        rightIcon.setVisibility(GONE);
        goText.setText(text);
    }
}
