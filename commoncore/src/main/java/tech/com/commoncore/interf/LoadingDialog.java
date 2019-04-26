package tech.com.commoncore.interf;

import android.app.Activity;
import android.support.annotation.Nullable;

import tech.com.commoncore.widget.LoadDialog;


/**
 * @Author: AriesHoo on 2018/7/23 10:39
 * @E-Mail: AriesHoo@126.com
 * Function: 用于全局配置网络请求登录Loading提示框
 * Description:
 */
public interface LoadingDialog {

    /**
     * @param activity
     * @return
     */
    @Nullable
    LoadDialog createLoadingDialog(@Nullable Activity activity, String text);
}
