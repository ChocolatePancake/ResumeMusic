package tech.com.commoncore.utils;


import tech.com.commoncore.R;

/**
 * Anthor:HeChuan
 * Time:2018/11/22
 * Desc:
 */
public class FlagSwitchUtil {

    public static int getFlagId(String flagName){

        if(flagName==null || flagName.equals("")){
            return R.mipmap.img_flag_default;
        }else if(flagName.contains("美国")){
            return R.mipmap.img_flag_america;
        }else if(flagName.contains("日本")){
            return R.mipmap.img_flag_japan;
        }else if(flagName.contains("英国")){
            return R.mipmap.img_flag_england;
        }else if(flagName.contains("瑞士")){
            return R.mipmap.img_flag_swiss;
        }else if(flagName.contains("法国")){
            return R.mipmap.img_flag_french;
        }else if(flagName.contains("韩国")){
            return R.mipmap.img_flag_korea;
        }else if(flagName.contains("加拿大")){
            return R.mipmap.img_flag_canada;
        }else  if(flagName.contains("澳")){
            return R.mipmap.img_flag_australia;
        }else if(flagName.contains("巴西")){
            return R.mipmap.img_flag_brazil;
        }else if(flagName.contains("德国")){
            return R.mipmap.img_flag_germany;
        }else if(flagName.contains("中国")){
            return R.mipmap.img_flag_chain;
        }
        return R.mipmap.img_flag_default;


    }
}
