package tech.com.commoncore.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作    者：ChenPengBo
 * 版    本：1.0
 * 创建日期：2016/10/26
 * 描    述：日期格式转换，处理工具
 * 修订历史
 */
public class DateUtil {

    public static final String FORMAT_1 = "yyyy";
    public static final String FORMAT_2 = "yyyy-MM";
    public static final String FORMAT_3 = "yyyy-MM-dd";
    public static final String FORMAT_4 = "yyyy-MM-dd HH";
    public static final String FORMAT_5 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_6 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_9 = "yy-MM-dd HH:mm";
    public static final String FORMAT_7 = "MM/yyyy";
    public static final String FORMAT_8 = "MM-dd";
    public static final String FORMAT_11 = "yyyy年";
    public static final String FORMAT_22 = "yyyy年MM月";
    public static final String FORMAT_33 = "yyyy年MM月dd日";
    public static final String FORMAT_44 = "yyyy年MM月dd日 HH时";
    public static final String FORMAT_55 = "yyyy年MM月dd日 HH时mm分";
    public static final String FORMAT_66 = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String FORMAT_77 = "MM月dd日 HH:mm";
    public static final String FORMAT_88 = "MM月dd日";
    public static final String FORMAT_99 = "mm:ss:SS";
    public static final String FORMAT_100 = "HH:mm";
    public static final String FORMAT_102 = "MM-dd HH:mm";
    public static final String FORMAT_991 = "mm分ss秒";
    public static final String FORMAT_101 = "yyyy-MM-dd HH:mm:ss.SS";
    public static final String FORMAT_103 = "yyyyMMdd_HHmmss";
    public static final String FORMAT_MONTH_DAY = "MM/dd";
    public static final String FORMAT_YEAR_MONTH = "yyyy.M";


    private static final String TAG = DateUtil.class.getSimpleName();

    /**
     * 按照指定的格式，将日期类型对象转换成字符串，例如：yyyy-MM-dd,yyyy/MM/dd,yyyy/MM/dd hh:mm:ss 如果传入的日期为null,则返回空值
     *
     * @param date   日期类型对象
     * @param format 需转换的格式
     * @return 日期格式字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    /**
     * 将日期类型对象转换成yyyy-MM-dd类型字符串 如果传入的日期为null,则返回空值
     *
     * @param date 日期类型对象
     * @return 日期格式字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(date);
    }

    /**
     * 将日期类型对象转换成yyyy-MM-dd HH:mm:ss类型字符串  如果传入的日期为null,则返回空值
     *
     * @param date 日期类型对象
     * @return 日期格式字符串
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(date);
    }

    /**
     * 按照指定的格式，将字符串解析成日期类型对象，例如：yyyy-MM-dd,yyyy/MM/dd,yyyy/MM/dd hh:mm:ss
     *
     * @param dateStr 日期格式的字符串
     * @param format  字符串的格式
     * @return 日期类型对象
     */
    public static Date parseDate(String dateStr, String format) {
        if (DataUtils.isNullString(dateStr)) {
            return null;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串（yyyy-MM-dd）解析成日期
     *
     * @param dateStr 日期格式的字符串
     * @return 日期类型对象
     */
    public static Date parseDate(String dateStr) {
        if (dateStr.indexOf("/") != -1) {
            dateStr = dateStr.replaceAll("/", "-");
        }
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    public static Date parseTime(String dateStr) {
        if (DataUtils.isNullString(dateStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat(FORMAT_6).parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串解析成对应日期格式的日期
     *
     * @param value 日期格式字符串
     * @return 日期类型对象
     */
    public static Date parse(String value) {
        if (DataUtils.isNullString(value)) {
            return null;
        }
        value = value.trim().replaceAll("/", "-");
        if (value.length() == FORMAT_1.length()) {
            return parseDate(value, FORMAT_1);
        } else if (value.length() == FORMAT_2.length()) {
            return parseDate(value, FORMAT_2);
        } else if (value.length() == FORMAT_3.length()) {
            return parseDate(value, FORMAT_3);
        } else if (value.length() == FORMAT_4.length()) {
            return parseDate(value, FORMAT_4);
        } else if (value.length() == FORMAT_5.length()) {
            return parseDate(value, FORMAT_5);
        } else if (value.length() == FORMAT_6.length()) {
            return parseDate(value, FORMAT_6);
        } else {
            throw new RuntimeException("解析日期格式出错，与指定格式不匹配.");
        }
    }

    /**
     * @param startTimeStr 00:00
     * @param endTimeStr   24:00
     * @return
     * @Title: isBetoweenTime
     * @Description:是否在对应时间范围内
     */
    public static boolean isBetweenTime(String startTimeStr, String endTimeStr) {
        boolean result = false;
        if (!"".equals(startTimeStr.trim()) && !"".equals(endTimeStr.trim())) {
            try {
                Double startTime = Double.valueOf(startTimeStr.replace(":", "."));
                Double endTime = Double.valueOf(endTimeStr.replace(":", "."));
                Date nowDate = new Date();
                Double nowTime = Double.valueOf(nowDate.getHours() + "." + nowDate.getMinutes());
                result = nowTime > startTime && nowTime < endTime;
                Log.i("isBetoweenTime", "startTime: " + startTime + "  \nendTime: " +
                        endTime + " \nowDate: " + nowDate + " \nnowTime: " + nowTime + " \nresult:"
                        + result);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return result;
    }

    /**
     * @param englishWeekName
     * @return
     */
    public static String getChineseWeekNumber(String englishWeekName) {
        if ("monday".equalsIgnoreCase(englishWeekName)) {
            return "一";
        }

        if ("tuesday".equalsIgnoreCase(englishWeekName)) {
            return "二";
        }

        if ("wednesday".equalsIgnoreCase(englishWeekName)) {
            return "三";
        }

        if ("thursday".equalsIgnoreCase(englishWeekName)) {
            return "四";
        }

        if ("friday".equalsIgnoreCase(englishWeekName)) {
            return "五";
        }

        if ("saturday".equalsIgnoreCase(englishWeekName)) {
            return "六";
        }

        if ("sunday".equalsIgnoreCase(englishWeekName)) {
            return "日";
        }

        return null;
    }

    public static String getChineseWeekNumber(int englishWeekName) {
        englishWeekName = englishWeekName - 1;
        if (englishWeekName == 0) {
            englishWeekName = 7;
        }
        if (1 == englishWeekName) {
            return "一";
        }

        if (2 == englishWeekName) {
            return "二";
        }

        if (3 == englishWeekName) {
            return "三";
        }

        if (4 == englishWeekName) {
            return "四";
        }

        if (5 == englishWeekName) {
            return "五";
        }

        if (6 == englishWeekName) {
            return "六";
        }

        if (7 == englishWeekName) {
            return "日";
        }

        return null;
    }

    /**
     * 一個月最后一天的日期(获取某年某月有多少天)
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 2010年
        c.set(Calendar.MONTH, month); // 6 月
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getTimeStampNowTime(long time) {
        time = time * 1000;
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTime();
    }

    /**
     * 比较两日期对象中的小时和分钟部分的大小.
     *
     * @param date        日期对象1, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @param anotherDate 日期对象2, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @return 如果日期对象1大于日期对象2, 则返回大于0的数; 反之返回小于0的数; 如果两日期对象相等, 则返回0.
     */
    public static int compareHourAndMinute(Date date, Date anotherDate) {
        if (date == null) {
            date = new Date();
        }

        if (anotherDate == null) {
            anotherDate = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hourOfDay1 = cal.get(Calendar.HOUR_OF_DAY);
        int minute1 = cal.get(Calendar.MINUTE);

        cal.setTime(anotherDate);
        int hourOfDay2 = cal.get(Calendar.HOUR_OF_DAY);
        int minute2 = cal.get(Calendar.MINUTE);

        if (hourOfDay1 > hourOfDay2) {
            return 1;
        } else if (hourOfDay1 == hourOfDay2) {
            // 小时相等就比较分钟
            return minute1 > minute2 ? 1 : (minute1 == minute2 ? 0 : -1);
        } else {
            return -1;
        }
    }

    /**
     * 比较两日期对象的大小, 忽略秒, 只精确到分钟.
     *
     * @param date        日期对象1, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @param anotherDate 日期对象2, 如果为 <code>null</code> 会以当前时间的日期对象代替
     * @return 如果日期对象1大于日期对象2, 则返回大于0的数; 反之返回小于0的数; 如果两日期对象相等, 则返回0.
     */
    public static int compareIgnoreSecond(Date date, Date anotherDate) {
        if (date == null) {
            date = new Date();
        }

        if (anotherDate == null) {
            anotherDate = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        cal.setTime(anotherDate);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        anotherDate = cal.getTime();

        return date.compareTo(anotherDate);
    }

    /**
     * 计算两个时间差，返回的是的秒s
     *
     * @param dete1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String dete1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = parseDate(dete1, FORMAT_66);
            d2 = parseDate(date2, FORMAT_66);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff / 1000;
    }

    /**
     * 计算两个时间差，返回的是的秒s
     *
     * @param ms1
     * @param ms2
     * @return
     */
    public static long calDateDifferent(long ms1, long ms2) {
        return (ms2 - ms1) / 1000;
    }

    public static int daysBetween(Date smdate, Date bdate) {
        return (int) ((smdate.getTime() / 86400000L) - (bdate.getTime() / 86400000L));
    }

    /**
     * 获取当前时间毫秒数
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }


    /**
     * 获取时间描述(前天、昨天、今天、明天、后天,否则返回几月几日)
     *
     * @param date
     * @return
     */
    public static String getDayDes(Date date) {
        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (date.getTime() + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天

        String str;
        if (intervalTime == -2) {
            str = "前天";
        } else if (intervalTime == -1) {
            str = "昨天";
        } else if (intervalTime == 0) {
            str = "今天";
        } else if (intervalTime == 1) {
            str = "明天";
        } else if (intervalTime == 2) {
            str = "后天";
        } else {
            str = formatDate(date, FORMAT_88);
        }
        return str;
    }

    private String switchWeek(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
            default:
                return "";
        }
    }

    public static String getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
            default:
                return "";
        }
    }


    /**
     * 根据毫秒时间戳来格式化字符串
     * 今天显示今天、昨天显示昨天、前天显示前天.
     * 早于前天的显示具体年-月-日，如2017-06-12；
     *
     * @param timeStamp 毫秒值
     * @return 今天 昨天 前天 或者 yyyy-MM-dd HH:mm:ss类型字符串
     */
    public static String format(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if (timeStamp >= todayStartMillis) {
            return "今天";
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if (timeStamp >= yesterdayStartMilis) {
            return "昨天";
        }
        long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
        if (timeStamp >= yesterdayBeforeStartMilis) {
            return "前天";
        }
        //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timeStamp));
    }


    /**
     * 根据时间戳来判断当前的时间是几天前,几分钟,刚刚
     *
     * @param long_time
     * @return
     */
    public static String getTimeStateNew(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //    System.out.println("传递过来的时间:"+format.format(time));
        //    System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
        //    System.out.println("天数:"+time_conver/day_conver);
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                return temp_conver + "天前";
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }


    public static final long ONE_DAY = 1000 * 60 * 60 * 24;


    /**
     * 获取两个时间相差的天数
     *
     * @param time1 time1
     * @param time2 time2
     * @return time1 - time2相差的天数
     */
    public static int getDayOffset(long time1, long time2) {
        // 将小的时间置为当天的0点
        long offsetTime;
        if (time1 > time2) {
            offsetTime = time1 - getDayStartTime(getCalendar(time2)).getTimeInMillis();
        } else {
            offsetTime = getDayStartTime(getCalendar(time1)).getTimeInMillis() - time2;
        }
        return (int) (offsetTime / ONE_DAY);
    }

    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static Calendar getDayStartTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

}


