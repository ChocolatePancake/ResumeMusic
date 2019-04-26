package com.leancloud.login.cache;

import android.content.Context;

import tech.com.commoncore.constant.BussConstant;
import tech.com.commoncore.utils.SPHelper;
import tech.com.commoncore.utils.SignUtil;


/**
 * Desc: 内容缓存类
 */
public class CacheData {
    public static final String AES_KEY = "bdf17047b5b01f5c5040ca43b9f3eae9";

    public static void initLoginAccount(Context context, String phone,String passwrod) {
        setLoginAccount(context,phone);
        setLoginPassword(context,passwrod);
    }
    //设置登录账号
    public static void setLoginAccount(Context context, String phone) {
        String signPhone = SignUtil.getAESEncodeString(phone, AES_KEY);
        SPHelper.setStringSF(context, BussConstant.LOGIN_ACCOUNT, signPhone);
    }

    //获取登录账号
    public static String getLoginAccount(Context context) {
        String signPhone = SPHelper.getStringSF(context, BussConstant.LOGIN_ACCOUNT);
        return SignUtil.getAESDecodeString(signPhone, AES_KEY);
    }

    //设置登录密码
    public static void setLoginPassword(Context context, String password) {
        String signPassword = SignUtil.getAESEncodeString(password,AES_KEY);
        SPHelper.setStringSF(context, BussConstant.LOGIN_PASSWORD, signPassword);
    }



}
