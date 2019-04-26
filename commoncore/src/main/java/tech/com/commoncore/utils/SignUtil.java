package tech.com.commoncore.utils;


/**
 * 作者: ChenPengBo
 * 时间: 2018-01-29
 * 描述:
 */

public class SignUtil {

    private static String TAG = SignUtil.class.getSimpleName();

    public static String getAESDecodeString(String str,String aesKey) {
        try {
            str = AES256Cipher.AES_Decode(str, aesKey);
        } catch (Exception e) {
        }

        return str;
    }

    public static String getAESEncodeString(String str,String aesKey) {
        try {
            str = AES256Cipher.AES_Encode(str,aesKey);
        } catch (Exception e) {
        }

        return str;
    }
}
