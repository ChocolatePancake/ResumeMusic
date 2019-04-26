package tech.com.commoncore.utils;

import android.webkit.WebView;

/**
 * Created: AriesHoo on 2017-01-17 12:59
 * Function: 视图帮助类
 * Desc:
 */
public class KeyWordUtil {
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
