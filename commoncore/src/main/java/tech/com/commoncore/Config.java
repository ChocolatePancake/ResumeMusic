package tech.com.commoncore;

import android.os.Environment;

import tech.com.commoncore.base.BaseApplication;

/**
 * Author: ChenPengBo
 * Date: 2018/10/18
 * Desc: 配置
 */
public class Config {

    public static final String SIMPLE_APP_NAME = "qihuoshuju";
    //图片路径
    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory() + "/"
            + BaseApplication.getInstance().getPackageName() + "/photos/";

    public static final String PHOTO_AUTHORITY = "com.yjdata.futuredata.provider";

    public static final String AES_KEY = "bdf17047b5b01f5c5040ca43b9f3eae9";

}
