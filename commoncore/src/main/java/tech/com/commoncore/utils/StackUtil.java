package tech.com.commoncore.utils;

import android.app.Activity;


import java.util.Stack;

import tech.com.commoncore.manager.LoggerManager;

/**
 * @Author: AriesHoo on 2018/7/23 14:32
 * @E-Mail: AriesHoo@126.com
 * Function:Activity堆栈管理工具类
 * Description:
 * 1、2018-6-21 09:49:11 新增根据class获取Activity方法
 * 2、2018-7-30 10:00:45 修改方法返回值
 */
public class StackUtil {
    private final String TAG = this.getClass().getSimpleName();
    private static Stack<Activity> mActivityStack;
    private static volatile StackUtil sInstance;

    private StackUtil() {
    }

    public static StackUtil getInstance() {
        if (sInstance == null) {
            synchronized (StackUtil.class) {
                if (sInstance == null) {
                    sInstance = new StackUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取Stack
     *
     * @return
     */
    public Stack<Activity> getStack() {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        return mActivityStack;
    }

    /**
     * 获取最后一个入栈Activity理论上是应用当前停留Activity
     * (前提是所有Activity均在onCreate调用 push onDestroy调用pop)
     *
     * @return
     */
    public Activity getCurrent() {
        if (mActivityStack != null && mActivityStack.size() != 0) {
            Activity activity = mActivityStack.lastElement();
            LoggerManager.i(TAG, "get current activity:" + activity.getClass().getSimpleName());
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 获取前一个Activity
     *
     * @return
     */
    public Activity getPrevious() {
        if (mActivityStack != null && mActivityStack.size() >= 2) {
            Activity activity = mActivityStack.get(mActivityStack.size() - 2);
            LoggerManager.i(TAG, "get Previous Activity:" + activity.getClass().getSimpleName());
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 根据Class 获取Activity
     *
     * @param cls
     * @return
     */
    public Activity getActivity(Class cls) {
        if (mActivityStack == null || mActivityStack.size() == 0 || cls == null) {
            return null;
        }
        for (Activity activity : mActivityStack) {
            if (activity != null && activity.getClass() == cls) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 入栈
     *
     * @param activity
     */
    public StackUtil push(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        mActivityStack.add(activity);
        LoggerManager.i(TAG, "push stack activity:" + activity.getClass().getSimpleName());
        return sInstance;
    }

    /**
     * 出栈
     *
     * @param activity Activity对象
     */
    public StackUtil pop(Activity activity) {
        if (activity != null) {
            LoggerManager.i(TAG, "remove current activity:" + activity.getClass().getSimpleName() + ";isFinishing" + activity.isFinishing());
            //只需在activity不在正在关闭状态下进行finish即可
            if (!activity.isFinishing()) {
                activity.finish();
            }
            if (mActivityStack != null && mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
                LoggerManager.i(TAG, "remove current activity:" + activity.getClass().getSimpleName() + ";size:" + mActivityStack.size());
            }
        }
        return sInstance;
    }

    /**
     * 将栈里的Activity全部清空
     */
    public StackUtil popAll() {
        if (mActivityStack != null) {
            while (mActivityStack.size() > 0) {
                Activity activity = this.getCurrent();
                if (activity == null) {
                    break;
                }
                pop(activity);
            }
        }
        return sInstance;
    }

    /**
     * 将堆栈里退回某个Activity为止
     *
     * @param cls
     */
    public StackUtil popAllExceptCurrent(Class cls) {
        while (true) {
            Activity activity = this.getCurrent();
            if (activity == null || activity.getClass().equals(cls)) {
                return sInstance;
            }
            pop(activity);
        }
    }

    /**
     * 只留下栈顶一个Activity
     */
    public StackUtil popAllExceptCurrent() {
        while (true) {
            Activity activity = this.getPrevious();
            if (activity == null) {
                return sInstance;
            }
            pop(activity);
        }
    }

    /**
     * 应用程序退出
     */
    public StackUtil exit() {
        try {
            popAll();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            System.exit(-1);
            LoggerManager.e(TAG, "exit():" + e.getMessage());
        }
        return sInstance;
    }
}
