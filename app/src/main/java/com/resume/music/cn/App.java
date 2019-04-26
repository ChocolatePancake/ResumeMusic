package com.resume.music.cn;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVOSCloud;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;

import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.base.BaseApplication;
import tech.com.commoncore.constant.ApiConstant;
import tech.com.commoncore.utils.ApplicationUtil;
import tech.com.commoncore.utils.Utils;


/**
 * Function:基础配置Application
 * Description:  币圈社区 ,174735  账号
 */
public class App extends BaseApplication {
    private String TAG = "BaseApp";

    @Override
    public void init() {
        AVOSCloud.initialize(this, "Ecf48M8pXP7XwlNFC7Rpfwjo-gzGzoHsz", "mSdVPi1WmWYNyQuMjbK5C2RN");
        AVOSCloud.setDebugLogEnabled(true);

        Utils.init(this);
        //最简单UI配置模式-必须进行初始化
        FastManager.init(this);
        //以下为更丰富自定义方法
        //全局UI配置参数-按需求设置
        AppImplDefault impl = new AppImplDefault(getInstance());
        FastManager.getInstance()
                //设置Adapter加载更多视图--默认设置了FastLoadMoreView
                .setLoadMoreFoot(impl)
                //设置RecyclerView加载过程多布局属性
                .setMultiStatusView(impl)
                //设置全局网络请求等待Loading提示框如登录等待loading--观察者必须为FastLoadingObserver及其子类
                .setLoadingDialog(impl)
                //设置SmartRefreshLayout刷新头-自定加载使用BaseRecyclerViewAdapterHelper
                .setDefaultRefreshHeader(impl)
                //设置全局TitleBarView相关配置
                .setTitleBarViewControl(impl)
                //设置Activity滑动返回控制-默认开启滑动返回功能不需要设置透明主题
                .setSwipeBackControl(impl)
                //设置Activity/Fragment相关配置(横竖屏+背景+虚拟导航栏+状态栏+生命周期)
                .setActivityFragmentControl(impl)
                //设置BasisActivity 子类按键监听
                .setActivityKeyEventControl(impl)
                //设置http请求结果全局控制
                .setHttpRequestControl(impl)
                //设置主页返回键控制-默认效果为2000 毫秒时延退出程序
                .setQuitAppControl(impl);


        // 初始化 ARouter
        if (ApplicationUtil.isApkInDebug(this)) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initLogHelper() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    @Override
    public void initHttpClient() {
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(ApiConstant.BASE_URL)
                .connectTimeout(15)
                .setCookie(true)
                .retryCount(3)
                //配置日志拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY));
    }

    @Override
    public void initNotifyHelper() {

    }

    @Override
    public void initModuleApp(Application application) {
        //加载各个模块提供出来的使用接口
        for (String moduleApp : AppConfig.moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                Log.e("clazz ", clazz.getSimpleName());
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.initModuleApp(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initModuleData(Application application) {
        //加载各个模块提供出来的使用接口
        for (String moduleApp : AppConfig.moduleApps) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.initModuleData(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
