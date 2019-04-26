package com.resume.music.cn.channel;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

public class ChannelManager {
    public static final String TABLE_SWITCH = "CheckVersion";
    public static final String SWITCH_NEED_SWITCH = "versionCode";   // 0/空：正常界⾯面，1： 跳转到H5的界⾯面；
    public static final String SWITCH_CHANNEL = "channel";

    public static void requestTableSwitch(final ICallback callback) {
        AVQuery avQuery = new AVQuery(TABLE_SWITCH);
        avQuery.whereEqualTo(SWITCH_CHANNEL, "appSwitch");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        AVObject channelObject = list.get(0);
                        int versionCode = (int) channelObject.get(SWITCH_NEED_SWITCH);
                        if (versionCode == 1) {
                            callback.showExpired();
                        } else {
                            callback.goApp();
                        }
                    } else {
                        //2.2 创建开关数据库.单位创建表 创建表字段
                        initCheckVersion();
                        callback.goApp();
                    }
                } else {
                    if (e.getCode() == 101) {
                        //2.2 未创建开关数据库.  创建数据库
                        initCheckVersion();
                        callback.goApp();
                    } else {
                        //2.3
                        callback.showNetWorkError(e);
                    }
                }
            }
        });
    }


    private static void initCheckVersion() {
        AVObject t_check_version = new AVObject(TABLE_SWITCH);
        t_check_version.put(SWITCH_CHANNEL, "appSwitch");
        t_check_version.put(SWITCH_NEED_SWITCH, 0);
        t_check_version.saveInBackground();
    }

    public interface ICallback {
        void showNetWorkError(Exception e);

        void goApp();

        void showExpired();
    }
}
