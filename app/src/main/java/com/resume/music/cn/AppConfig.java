package com.resume.music.cn;

/**
 * Anthor:NiceWind
 * Time:2019/3/22
 * Desc:The ladder is real, only the climb is all.
 * <p>
 * 在这里配置所有的模块. module层的application.
 */
public class AppConfig {

    //---定义所有的 module 对外开放的application ---
    private static final String LoginApp = "com.leancloud.login.LoginApp";
    private static final String HomeApp = "com.leancloud.home.HomeApp";
    private static final String HangqingApp = "com.yyjj.hangqing.HangqingApp";
    private static final String CircleApp = "com.leancloud.circle.CircleApp";
    private static final String MyApp = "com.yyjj.my.MyApp";
    private static final String ZixunApp = "com.yyjj.zixun.ZiXunApp";
    private static final String FuturesApp = "cn.xyt.com.futures.FuturesApp";

    public static String[] moduleApps = {
            LoginApp, HomeApp, HangqingApp, CircleApp, MyApp, ZixunApp, FuturesApp
    };

}
