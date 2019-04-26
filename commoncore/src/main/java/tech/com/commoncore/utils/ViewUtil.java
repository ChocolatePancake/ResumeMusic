package tech.com.commoncore.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

/**
 * Created: AriesHoo on 2017-01-17 12:59
 * Function: 视图帮助类
 * Desc:
 */
public class ViewUtil {
    private static volatile ViewUtil instance;

    private ViewUtil() {
    }

    public static ViewUtil getInstance() {
        if (instance == null) {
            synchronized (ViewUtil.class) {
                if (instance == null) {
                    instance = new ViewUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取视图的宽度
     *
     * @param mView
     * @return
     */
    public int getWidth(View mView) {
        if (mView == null) {
            return 0;
        }
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mView.measure(w, h);
        int width = mView.getMeasuredWidth();
        return width;
    }

    /**
     * 获取视图高度
     *
     * @param mView
     * @return
     */
    public int getHeight(View mView) {
        if (mView == null) {
            return 0;
        }
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mView.measure(w, h);
        int height = mView.getMeasuredHeight();
        return height;
    }

    /**
     * 设置视图的宽高
     *
     * @param view
     * @param width
     * @param height
     */
    public void setViewWidthAndHeight(View view, int width, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public void setViewWidth(View view, int width) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }

    public void setViewHeight(View view, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置View的Margin
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param width
     * @param height
     */
    public void setViewMargin(View view, int left, int top, int right, int bottom, int width, int height) {
        if (view == null) {
            return;
        }
        ViewParent parent = view.getParent();
        if (parent == null) {
            return;
        }
        ViewGroup.MarginLayoutParams lp;
        if (parent instanceof LinearLayout) {
            lp = new LinearLayout.LayoutParams(width, height);
        } else if (parent instanceof RelativeLayout) {
            lp = new RelativeLayout.LayoutParams(width, height);
        } else if (parent instanceof FrameLayout) {
            lp = new FrameLayout.LayoutParams(width, height);
        } else {
            lp = new TableLayout.LayoutParams(width, height);
        }
        if (lp != null) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    public void setViewMargin(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            return;
        }
        setViewMargin(view, left, top, right, bottom, getWidth(view), getHeight(view));
    }

    /**
     * WebView 替换敏感字
     */
    public static void hideHtmlContent(WebView webView) {
        String js_replace_sensitive_words = "javascript:function modifyText() {" +
                "document.body.innerHTML = document.body.innerHTML.replace(/美元/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/欧元/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/货币/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/政府公债/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/国库券/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/公司债券/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/MT4外汇/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇期货/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇投资/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇软件/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇投资软件/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇开户软件/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇交易软件/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇交易/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/恒指汇/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇现货/g, '*');" +
                "document.body.innerHTML = document.body.innerHTML.replace(/外汇/g, '*');" +
                "}";
        webView.loadUrl(js_replace_sensitive_words);
        webView.loadUrl("javascript:modifyText();");
    }
}
