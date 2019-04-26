package com.leancloud.home;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.avos.avoscloud.AVOSCloud;
import com.vise.xsnow.http.ViseHttp;

import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.base.BaseApplication;
import tech.com.commoncore.basecomponent.ServiceFactory;
import tech.com.commoncore.utils.ApplicationUtil;
import tech.com.commoncore.utils.Utils;


/**
 * Function:基础配置Application
 * Description:  股票个股期权宝
 */
public class HomeApp extends BaseApplication {
    private String TAG = "BaseApp";

    @Override
    public void init() {
        //初始化
        AVOSCloud.initialize(this, "nSSNRmju4GImUHRrr942AdJ2-gzGzoHsz", "OXzxVFSvyq85YaAc531UMEVy");
        AVOSCloud.setDebugLogEnabled(true);

        //最简单初始化
        Utils.init(this);
        FastManager.init(this);
        ViseHttp.init(this);

        // 初始化 ARouter
        if (ApplicationUtil.isApkInDebug(this)) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    //初始化当前模块对外开放的接口fragment.
    @Override
    public void initModuleApp(Application application) {
        //当前登录模块 暂无开放的fragment.
        ServiceFactory.getInstance().setHomeService(new FragmentService());
    }


    @Override
    public void initModuleData(Application application) {
        //当前登录模块 暂无开放的fragment.
    }
}
