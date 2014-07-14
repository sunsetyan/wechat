package com.sunrise22.galaxy.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class DateUtils {
    /**
     * 构造函数
     */
    private DateUtils() {
        // Utility class
    }

    /**
     * 日志处理类
     */
    private static final Log LOG = LogFactory.getLog(DateUtils.class.getName());

    /**
     * 日期时间24小时制格式("yyyy-MM-dd HH:mm:ss")
     */
    public static final String DATE_TIME_24_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期时间24小时制格式("yyyy-MM-dd HH:mm")
     */
    public static final String DATE_TIME_24_FORMAT2 = "yyyy-MM-dd HH:mm";
    /**
     * 时间24小时制格式("HH:mm")
     */
    public static final String TIME_24_FORMAT = "HH:mm";

    /**
     * 日期格式("yyyy-MM-dd")
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 日期格式("yyyy/MM/dd")
     */
    public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";

    /**
     * 短日期格式
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    /**
     * 日期时间24小时制格式("yyyyMMddHHmmss")
     */
    public static final String DATE_TIME_24_FORMAT_SHORT = "yyyyMMddHHmmss";

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyy-MM-dd HH:mm:ss 格式时间串
     * @return 时间对象
     */
    public static Date converToDateTime(String dateTime) {
        if (null == dateTime || "".equals(dateTime)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_24_FORMAT);
        try {
            return df.parse(dateTime);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }
            return new Date();
        }
    }

    /**
     * 转换为日期
     * 
     * @param date 短格式日期
     * @return 日期对象
     */
    public static Date convertToDateShort(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_SHORT);
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return new Date();
        }
    }

    /**
     * 转换为日期
     * 
     * @param date 短格式日期
     * @return 日期对象
     */
    public static Date convertToDateShort2(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        // 设置当日期非法时，直接返回null
        df.setLenient(false);
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return null;
        }
    }

    /**
     * 转换为日期
     * 
     * @param date 短格式日期
     * @return 日期对象
     */
    public static Date convertToDateShort3(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_24_FORMAT);
        // 设置当日期非法时，直接返回null
        df.setLenient(false);
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return null;
        }
    }

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyyMMddHHmmss 格式时间串
     * @return 时间对象
     */
    public static Date converToDateTimeShort(Object dateTime) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_24_FORMAT_SHORT);
        try {
            return df.parse(StrUtils.c2str(dateTime));
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return new Date();
        }
    }

    /**
     * 日期字符串转化为日期
     * 
     * @param dateTime yyyy-MM-dd HH:mm:ss 格式时间串
     * @param format 格式
     * @return 时间对象
     */
    public static Date converToDateTime(String dateTime, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);

        try {
            return df.parse(dateTime);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return new Date();
        }
    }

    /**
     * 使用自定义格式格式化当前日期
     * 
     * @param dateFormat 输出显示的时间格式
     * @return 格式化结果
     */
    public static String formatCurrentDate(String dateFormat) {
        return formatDate(new Date(), dateFormat);
    }

    /**
     * 使用默认格式(yyyy-MM-dd)格式化当前日期
     * 
     * @return 格式化结果
     */
    public static String formatCurrentDate() {
        return formatCurrentDate(DATE_FORMAT);
    }

    /**
     * 使用默认格式(HH:mm)格式化当前日期
     * 
     * @return 格式化结果
     */
    public static String formatCurrentTime() {
        return formatCurrentDate(TIME_24_FORMAT);
    }

    /**
     * 格式化日期，自定义格式
     * 
     * @param date 日期
     * @param dateFormat 日期格式
     * @return 格式化结果
     */
    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(date);
    }

    /**
     * 使用默认格式(yyyy-MM-dd)格式化日期
     * 
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDate(Date date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * 使用自定义格式格式化当前时间
     * 
     * @param dateTime 日期时间
     * @param datetimeFormat 日期时间格式
     * @return 格式化结果
     */
    public static String formatDateTime(Date dateTime, String datetimeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(datetimeFormat);

        return sdf.format(dateTime);
    }

    /**
     * 使用默认格式(yyyy-MM-dd HH:mm:ss)格式化日期时间,24小时制
     * 
     * @param dateTime 日期时间
     * @return 格式化结果
     */
    public static String formatDateTime(Date dateTime) {
        return formatDateTime(dateTime, DATE_TIME_24_FORMAT);
    }

    /**
     * 使用格式(yyyy-MM-dd HH:mm:ss)格式化当前日期
     * 
     * @return 格式化结果
     */
    public static String formatCurrentDateTime() {
        return formatDateTime(new Date(), DATE_TIME_24_FORMAT);
    }

    /**
     * 获取日历0点
     * 
     * @param date 日期
     * @return 日历0点
     */
    public static Calendar getZeroCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 获取日历昨天0点
     * 
     * @param date 今天日期
     * @return 日历昨天0点
     */
    public static Calendar getPreZeroCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 获取日历今天23点30分
     * 
     * @param date 今天日期
     * @return 日历今天23点30分
     */
    public static Calendar getLateCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * 20090904170000.000字符串转化为时间
     * 
     * @param timeStr 时间串
     * @return 时间字符串
     */
    public static String toTimeStampString(String timeStr) {
        String result = "";
        // TimeZone tz = TimeZone.getDefault();
        // GregorianCalendar c = new GregorianCalendar(tz);
        result += timeStr.substring(0, 4);
        result += "-";
        result += timeStr.substring(4, 6);
        result += "-";
        result += timeStr.substring(6, 8);

        if (timeStr.length() > 8) {
            result += " " + timeStr.substring(8, 10);
            result += ":";
            result += timeStr.substring(10, 12);
            result += ":";
            result += timeStr.substring(12, 14);
        }
        return result;
    }

    /**************************************************************
     * 根据当前时间获取下周一日期
     * 
     * @param sDate 比较时间
     * @param count 下几周
     * @author dujj
     * @return Date
     */
    public static Date getNextMonday(Date sDate, int count) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);

        if (week > 1) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 1) + 7 * count);
        } else {
            // cal.add(Calendar.DAY_OF_MONTH, 1 - week + 7 * count);
        }

        cal.add(Calendar.DATE, 1);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * 根据当前时间获取下月一号日期
     * 
     * @param sDate 比较时间
     * @param count 下几月
     * @author dujj
     * @return Date
     */
    public static Date getNextMonth(Date sDate, int count) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        cal.add(Calendar.MONTH, count);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * 获取当前月最后一天
     * 
     * @return last day
     */
    public static int getCurrMonthLastDay() {

        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("d");

        return NumberUtils.c2int(sdf.format(cal.getTime()));
    }

    /**
     * @param year int 年份
     * @param month int 月份
     * 
     * @return int 某年某月的最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 根据当前时间获取下一天日期
     * 
     * @param sDate 比较时间
     * @param count 下几天
     * @author dujj
     * @return Date
     */
    public static Date getNextDay(Date sDate, int count) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(sDate);
        cal.add(Calendar.DAY_OF_YEAR, count);

        return formatDate2(cal.getTime(), DATE_FORMAT_SLASH);
    }

    /**
     * 时间格式化
     * 
     * @param sdate sdate
     * @param formats formats
     * @return Date
     */
    public static Date formatDate2(Date sdate, String formats) {

        SimpleDateFormat df = new SimpleDateFormat(formats);
        String date = df.format(sdate);

        try {
            return df.parse(date);
        } catch (ParseException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("parse date string error, return current date");
            }

            return null;
        }
    }
}
