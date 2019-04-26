package tech.com.commoncore.base;

import android.app.Application;


/**
 * Created by Arison on 2017/5/15.
 */
public abstract class BaseApplication extends Application {
    private static Application mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
        init();
        //初始化网络库
        initHttpClient();
        //初始化图片库
        initImageClient();
        //初始化DBHelper
        initDBHelper();
        //初始化消息提醒库
        initNotifyHelper();
        //初始化日志打印
        initLogHelper();

    }

    public void initLogHelper() {

    }


    public abstract void init();

    public void initHttpClient() {

    }

    public void initImageClient() {

    }

    public void initDBHelper() {

    }

    public void initNotifyHelper() {

    }

    public static Application getInstance() {
        return mApplication;
    }


    //模块化回调,用于被主APP 反射加载.

    /**
     * Application 初始化
     */
    public abstract void initModuleApp(Application application);

    /**
     * 所有 Application 初始化后的自定义操作
     */
    public abstract void initModuleData(Application application);
}
