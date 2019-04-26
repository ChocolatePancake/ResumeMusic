package tech.com.commoncore.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gyf.barlibrary.ImmersionBar;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.com.commoncore.R;
import tech.com.commoncore.utils.brower.SonicJavaScriptInterface;
import tech.com.commoncore.utils.brower.SonicRuntimeImpl;
import tech.com.commoncore.utils.brower.SonicSessionClientImpl;

/**
 * Author:ChenPengBo
 * Date:2018/11/2
 * Desc:Brower
 * Version:1.0
 */
public class BrowerActivity extends BaseActivity {
    public static final String CONETNT = "content";
    public static final String TITLE_COLOR = "titleBarColor";
    public static final String NAVIGATION_COLOR = "navigationBarColor";  //无用字段
    WebView webView;
    ProgressBar progressWeb;
    private String url;
    private String titleColor = "#ffffff";
    private String navigationColor = "#ffffff";

    private SonicSession sonicSession;

    private ImmersionBar mImmersionBar;

    @Override
    public int getContentLayout() {
        return R.layout.activity_brower;
    }


    @Override
    public void beforeSetContentView() {
        super.beforeSetContentView();
        url = getIntent().getStringExtra(CONETNT);
        titleColor = getIntent().getStringExtra(TITLE_COLOR);
        navigationColor = getIntent().getStringExtra(NAVIGATION_COLOR);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        webView = findViewById(R.id.webview);
        progressWeb = findViewById(R.id.progress_web);
        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        SonicSessionClientImpl sonicSessionClient = null;

        // if it's sonic mode , startup sonic session at first time
        // sonic mode
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);

        // if it's offline pkg mode, we need to intercept the session connection


        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            // throw new UnknownError("create session fail!");
            //            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
        Intent intent = getIntent();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                ViewUtil.hideHtmlContent(view);  //过滤关键字
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (!TextUtils.isEmpty(url) && url.endsWith(".apk")) {
                    downloadByBrowser(url);
                }
                return !(url.startsWith("http") || url.startsWith("https"));
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {


                if (newProgress == 100) {
                    progressWeb.setVisibility(View.GONE); // 加载完毕进度条消失
                } else {
                    progressWeb.setProgress(newProgress); // 更新进度
                }
                super.onProgressChanged(view, newProgress);
            }

        });

        WebSettings webSettings = webView.getSettings();

        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }

        //解决图片不显示
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void loadData() {
        super.loadData();

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarView(R.id.top_view)
                .fullScreen(true);
        if (titleColor != null && titleColor.toLowerCase().contains("0x")) {  // 如果INT类型,16进制
            try {
                String lowCase = titleColor.toLowerCase();
                String colorValue = lowCase.substring(2, titleColor.length());
                mImmersionBar.statusBarColor("#" + colorValue);
                if (colorValue.startsWith("f")) {
                    mImmersionBar.statusBarDarkFont(true);
                } else {
                    mImmersionBar.statusBarDarkFont(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titleColor != null && titleColor.toLowerCase().contains("#")) {  //如果 # 16进制
            try {
                mImmersionBar.statusBarColor(titleColor);

                String lowCase = titleColor.toLowerCase();
                String colorValue = lowCase.substring(1, titleColor.length());
                if (colorValue.startsWith("f")) {
                    mImmersionBar.statusBarDarkFont(true);
                } else {
                    mImmersionBar.statusBarDarkFont(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //不做处理,默认值.
        }

        mImmersionBar.init();
    }

    public static Intent actionToActivity(Context context, String url, String titleColor) {
        Intent intent = new Intent(context, BrowerActivity.class);
        intent.putExtra(CONETNT, url);
        intent.putExtra(TITLE_COLOR, titleColor);
        return intent;
    }

    public static Intent actionToActivity(Context context, String url, String titleColor, String navigationColor) {
        Intent intent = new Intent(context, BrowerActivity.class);
        intent.putExtra(CONETNT, url);
        intent.putExtra(TITLE_COLOR, titleColor);
        intent.putExtra(NAVIGATION_COLOR, navigationColor);
        return intent;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        super.onDestroy();
    }


    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }

}
