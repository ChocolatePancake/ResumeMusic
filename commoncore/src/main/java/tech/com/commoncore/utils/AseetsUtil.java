package tech.com.commoncore.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Anthor:HeChuan
 * Time:2019/1/5
 * Desc:
 */
public class AseetsUtil {


    public static String getJson(String fileName, Context context) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "解析错误";
    }

}
