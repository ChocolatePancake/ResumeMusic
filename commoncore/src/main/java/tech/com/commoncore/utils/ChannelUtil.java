package tech.com.commoncore.utils;

import android.content.Context;

/**
 * 用于LeanCloud区分渠道
 */
public class ChannelUtil {

    private static final String GROUP_ID = "jf";//组名
    private static final String APP_ID = "gppz";//app简称

    public static String getChannel(Context context) {
        return GROUP_ID + APP_ID +ApplicationUtil.getMetaValueFromApp(context,"UMENG_CHANNEL");
    }
}
