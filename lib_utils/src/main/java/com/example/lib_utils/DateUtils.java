package com.example.lib_utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.tool
 * email：893855882@qq.com
 * data：2017/5/25
 */

public class DateUtils {
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat msFormat = new SimpleDateFormat("mm:ss");

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParse(long duration) {
        String time = "";
        if (duration > 1000) {
            time = timeParseMinute(duration);
        } else {
            long minute = duration / 60000;
            long seconds = duration % 60000;
            long second = Math.round((float) seconds / 1000);
            if (minute < 10) {
                time += "0";
            }
            time += minute + ":";
            if (second < 10) {
                time += "0";
            }
            time += second;
        }
        return time;
    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParseMinute(long duration) {
        try {
            return msFormat.format(duration);
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     * 判断两个时间戳相差多少秒
     *
     * @param d
     * @return
     */
    public static int dateDiffer(long d) {
        try {
            long l1 = Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10));
            long interval = l1 - d;
            return (int) Math.abs(interval);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 毫秒级时间戳 换转成 mm：ss
     *
     * @param millisecond
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromLong(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    /**
     * 获取minute分钟后的时间
     *
     * @param millisecond 时间戳
     * @param minute      几分钟后
     * @param timeFormat  返回格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromDelayMinute(Long millisecond, int minute, String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Date now = new Date(millisecond);
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, minute);
        return sdf.format(millisecond);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromLong(Long millisecond, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }


    /**
     * 将秒数转换成  分秒
     *
     * @param time
     * @return
     */
    public static String second2Time(long time) {
        long minute = 0;
        long second = 0;
        time = time / 1000;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            second = time % 60;
            return unitFormat(minute) + ":" + unitFormat(second);

        }
    }


    /**
     * 将秒数转换成 时分秒
     *
     * @param time
     * @return
     */
    public static String second2Date(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
//                if (hour > 99) {
//                    return "99:59:59";
//                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }


    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Long.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }


    public static String timeStamp2Date(long occupy_time, String format) {
        if (String.valueOf(occupy_time).length() == 10) {
            occupy_time = occupy_time * 1000;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(occupy_time));
    }


    public static String secToTimeSub(long time) {
        if (time <= 1000) {
            return "000";
        } else {
            time = time % 1000;
            if (time == 0) {
                return "000";
            } else {
                String s = String.valueOf(time);
                if (s.length() == 1) {
                    return "00" + s;
                } else if (s.length() == 2) {
                    return "0" + s;
                } else {
                    return s;
                }
            }
        }
    }

    /**
     * 时间戳与日期转化 start——————————————————————————————————————————————————
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * @param time
     * @return
     */
    public static String secToTime(long time) {
        String timeStr = null;
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                day = hour / 24;
                hour = hour % 24;
                timeStr = day + "天 " + unitFormat(hour) + ":"
                        + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }


    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }


    /**
     * second to HH:MM:ss
     *
     * @param seconds
     * @return
     */
    public static String convertSecondsToTime(long seconds) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (seconds <= 0)
            return "00:00";
        else {
            minute = (int) seconds / 60;
            if (minute < 60) {
                second = (int) seconds % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = (int) (seconds - hour * 3600 - minute * 60);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String convertSecondsToFormat(long seconds, String format) {

        if (TextUtils.isEmpty(format))
            return "";

        Date date = new Date(seconds);
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeToDate(long seconds, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(seconds));
    }


    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */

    public static String getSecondTimestampTwo(Date date) {
        if (null == date) {
            return "";
        }
        return String.valueOf(date.getTime() / 1000);

    }


    /**
     * 20 * 字符串转换成日期
     * 21 * @param str
     * 22 * @return date
     * 23
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
            default:
                return 30;
            case 2:
                if (year % 4 == 0) {
                    return 29;
                } else {
                    return 28;
                }
        }
    }

    /**
     * 获取时间 yyyy-MM-dd HH:mm
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentSpecialDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date now = new Date();
        return sdf.format(now);
    }


    /**
     * 获取时间 yyyy-MM-dd HH:mm
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentClickDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date now = new Date();
        return sdf.format(now);
    }


    public static final String STR_TODAY = "（今天）";

    @SuppressLint("SimpleDateFormat")
    public static String getVehicleDisplay(Date date, boolean isShowToday) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            String dateStr = sdf.format(date);
            String todayStr = sdf.format(today);
            if (TextUtils.equals(todayStr, dateStr) && isShowToday) {
                return todayStr.concat(STR_TODAY);
            } else {
                return dateStr;
            }
        }
        return "";
    }

    /**
     * 获取指定日期后一天
     *
     * @param gmtEnd 指定日期
     * @return 后一天
     */
    public static String getDayAfterDate(String gmtEnd) {
        String endDate = "";
        if (!TextUtils.isEmpty(gmtEnd)) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date parse = sdf1.parse(gmtEnd);
                if (parse != null) {
                    Calendar gmtEndData = new GregorianCalendar();
                    String[] gmtEndSp = gmtEnd.split("-");
                    gmtEndData.set(Integer.parseInt(gmtEndSp[0]), Integer.parseInt(gmtEndSp[1]) - 1, Integer.parseInt(gmtEndSp[2]) + 1);
                    Date time = gmtEndData.getTime();
                    endDate = sdf1.format(time);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return endDate;
    }

    public static String getTodayFlag(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.get(Calendar.HOUR_OF_DAY);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 5) {
            return "凌晨";
        } else if (hour < 12) {
            return "上午";
        } else if (hour < 13) {
            return "中午";
        } else if (hour < 18) {
            return "下午";
        } else if (hour < 24) {
            return "晚上";
        }
        return "";
    }
}
