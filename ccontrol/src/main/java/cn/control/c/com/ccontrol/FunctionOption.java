package cn.control.c.com.ccontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FunctionOption extends LinearLayout {

    ImageView leftIcon, rightIcon;
    TextView messageTx, remarksTx;

    public FunctionOption(Context context) {
        super(context);
        info(context, null);
    }

    public FunctionOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        info(context, attrs);
    }

    public FunctionOption(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        info(context, attrs);
    }

    private void info(Context context, AttributeSet attrs) {
        binderView(context);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FunctionOption);
            setLeftIcon(ta.getResourceId(R.styleable.FunctionOption_leftIcon, 0));
            setRightIcon(ta.getResourceId(R.styleable.FunctionOption_rightIcon, 0));
            setMessageTx(ta.getString(R.styleable.FunctionOption_message));
            setLeftIconGone(ta.getBoolean(R.styleable.FunctionOption_gongLeftIcon, false));
            setRemarksTx(ta.getString(R.styleable.FunctionOption_remarks));
        }
    }

    private void binderView(Context context) {
        View orderView = LayoutInflater.from(context)
                .inflate(R.layout.layout_function_option, this, true);
        leftIcon = orderView.findViewById(R.id.left_icon);
        rightIcon = orderView.findViewById(R.id.right_icon);
        messageTx = orderView.findViewById(R.id.message_tx);
        remarksTx = orderView.findViewById(R.id.remarks_tx);
    }

    public void setLeftIcon(int icon) {
        leftIcon.setBackgroundResource(icon);
    }

    public void setRightIcon(int icon) {
        rightIcon.setBackgroundResource(icon);
    }

    public void setMessageTx(String s) {
        messageTx.setText(s);
    }


    public void setLeftIconGone(boolean gone) {
        if (gone) {
            leftIcon.setVisibility(GONE);
        } else {
            leftIcon.setVisibility(VISIBLE);
        }
    }

    public void setRemarksTx(String text) {
        remarksTx.setText(text);
    }

}
