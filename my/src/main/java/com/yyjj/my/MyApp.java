package com.yyjj.my;

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
public class MyApp extends BaseApplication {
    private String TAG = "BaseApp";

    @Override
    public void init() {
        //初始化
        AVOSCloud.initialize(this,"EYnhqrUOKpEexzLmu7qiATzS-gzGzoHsz","3OOU8Nd47nVGmKkfHJCGnrn5");
        AVOSCloud.setDebugLogEnabled(true);

        ViseHttp.init(this);
        Utils.init(this);
        //最简单UI配置模式-必须进行初始化
        FastManager.init(this);
        //以下为更丰富自定义方法
        //全局UI配置参数-按需求设置

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
        ServiceFactory.getInstance().setPerconalService(new MyService());
    }


    @Override
    public void initModuleData(Application application) {
        //当前登录模块 暂无开放的fragment.

    }

}
