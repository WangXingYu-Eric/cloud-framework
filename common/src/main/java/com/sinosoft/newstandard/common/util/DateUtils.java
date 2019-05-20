package com.sinosoft.newstandard.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private final static String DATE_FORMAT = "yyyy-MM-dd";

    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期.
     */
    public static String getCurrentDate() {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        dateStr = df.format(new Date());
        return dateStr;
    }

    /**
     * 获取当前日期时间.
     */
    public static String getCurrentDateTime() {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        dateStr = df.format(new Date());
        return dateStr;
    }

    /**
     * 获取当前日期时间.
     */
    public static String getCurrentDateTime(String dateFormat) {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        dateStr = df.format(new Date());
        return dateStr;
    }

    /**
     * 将字符串日期转换为日期格式[yyyy-MM-dd].
     */
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, DateUtils.DATE_FORMAT);
    }

    /**
     * 将字符串日期转换为自定义日期格式.
     */
    public static Date stringToDate(String dateStr, String dateFormat) {

        Date date;

        if (dateStr == null || dateStr.equals("")) {
            return null;
        }

        String pattern = "^\\d{4}(-|/|.)\\d{1,2}\\1\\d{1,2}$";
        boolean isMatch = Pattern.matches(pattern, dateStr);
        if (isMatch) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                date = df.parse(dateStr);
            } catch (ParseException e) {
                logger.error(e.getMessage());
                date = null;
            }
        } else {
            logger.warn("日期格式错误:{}", dateStr);
            date = null;
        }

        return date;
    }


    /**
     * 将日期格式日期转换为字符串格式.
     */
    public static String dateToString(Date date) {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 将日期格式日期转换为自定义字符串格式.
     */
    public static String dateToString(Date date, String dateFormat) {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 获取日期的DAY值.
     *
     * @param date 输入日期
     */
    public static int getDayOfDate(Date date) {
        int day;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取日期的MONTH值.
     *
     * @param date 输入日期
     */
    public static int getMonthOfDate(Date date) {
        int month;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取日期的YEAR值.
     *
     * @param date 输入日期
     */
    public static int getYearOfDate(Date date) {
        int year;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取星期几.
     *
     * @param date 输入日期
     */
    public static int getWeekOfDate(Date date) {
        int whatDay;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        whatDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return whatDay;
    }

    /**
     * 获取输入日期的当月第一天.
     *
     * @param date 输入日期
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获得输入日期的当月最后一天.
     */
    public static Date getLastDayOfMonth(Date date) {
        return DateUtils.addDay(DateUtils.getFirstDayOfMonth(DateUtils.addMonth(date, 1)), -1);
    }

    /**
     * 判断是否是闰年.
     *
     * @param date 输入日期
     * @return 是true 否false
     */
    public static boolean isLeapYear(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 根据整型数表示的年月日,生成日期类型格式.
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        //noinspection MagicConstant
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * 计算fromDate到toDate相差多少年.
     *
     * @return 年数
     */
    public static int getCountForDifferentYear(Date fromDate, Date toDate) {
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTime(fromDate);
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(toDate);
        return calendarTo.get(Calendar.YEAR) - calendarFrom.get(Calendar.YEAR);
    }

    /**
     * 计算fromDate到toDate相差多少个月.
     *
     * @return 月数
     */
    public static int getCountForDifferentMonth(Date fromDate, Date toDate) {
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTime(fromDate);
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(toDate);
        return (calendarTo.get(Calendar.YEAR) * 12 + calendarTo.get(Calendar.MONTH)) -
                (calendarFrom.get(Calendar.YEAR) * 12 + calendarFrom.get(Calendar.MONTH));
    }

    /**
     * 计算fromDate到toDate相差多少天.
     *
     * @return 天数
     */
    public static long getCountForDifferentDate(Object fromDate, Object toDate) {
        Date from = DateUtils.objectToDate(fromDate);
        Date to = DateUtils.objectToDate(toDate);
        return (to.getTime() - from.getTime()) / (24L * 60L * 60L * 1000L);
    }

    /**
     * 计算fromDate到toDate相差多分钟.
     *
     * @return 分钟数
     */
    public static long getCountForDifferentMinute(Object fromDate, Object toDate) {
        Date from = DateUtils.objectToDate(fromDate);
        Date to = DateUtils.objectToDate(toDate);
        return (to.getTime() - from.getTime()) / (60L * 1000L);
    }

    /**
     * 计算fromDate到toDate相差多少秒.
     *
     * @return 秒数
     */
    public static long getCountForDifferentSecond(Object fromDate, Object toDate) {
        Date from = DateUtils.objectToDate(fromDate);
        Date to = DateUtils.objectToDate(toDate);
        return (to.getTime() - from.getTime()) / 1000L;
    }

    /**
     * 计算年龄.
     *
     * @param birthday 生日日期
     * @param calcDate 要计算的日期点
     */
    public static int calcAge(Date birthday, Date calcDate) {

        int cYear = DateUtils.getYearOfDate(calcDate);
        int cMonth = DateUtils.getMonthOfDate(calcDate);
        int cDay = DateUtils.getDayOfDate(calcDate);
        int bYear = DateUtils.getYearOfDate(birthday);
        int bMonth = DateUtils.getMonthOfDate(birthday);
        int bDay = DateUtils.getDayOfDate(birthday);

        if (cMonth > bMonth || (cMonth == bMonth && cDay > bDay)) {
            return cYear - bYear;
        } else {
            return cYear - bYear - 1;
        }
    }

    /**
     * 从身份证中获取出生日期.
     *
     * @param IdNo 身份证号码
     */
    public static String getBirthDayFromIDCard(String IdNo) {
        Calendar calendar = Calendar.getInstance();
        if (IdNo.length() == 15) {
            calendar.set(Calendar.YEAR, Integer.parseInt("19" + IdNo.substring(6, 8)));
            calendar.set(Calendar.MONTH, Integer.parseInt(IdNo.substring(8, 10)) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(IdNo.substring(10, 12)));
        } else if (IdNo.length() == 18) {
            calendar.set(Calendar.YEAR, Integer.parseInt(IdNo.substring(6, 10)));
            calendar.set(Calendar.MONTH, Integer.parseInt(IdNo.substring(10, 12)) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(IdNo.substring(12, 14)));
        }
        return DateUtils.dateToString(calendar.getTime());
    }

    /**
     * 在输入日期上增加(+)或减去(-)天数.
     *
     * @param date 输入日期
     * @param days 要增加或减少的天数
     */
    public static Date addDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 在输入日期上增加(+)或减去(-)月份.
     *
     * @param date   输入日期
     * @param months 要增加或减少的月分数
     */
    public static Date addMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 在输入日期上增加(+)或减去(-)年份.
     *
     * @param date  输入日期
     * @param years 要增加或减少的年数
     */
    public static Date addYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 将Object转换为Date.
     */
    public static Date objectToDate(Object date) {
        if (date instanceof Date) {
            return (Date) date;
        }
        if (date instanceof String) {
            return DateUtils.stringToDate((String) date);
        }
        return null;
    }

    /**
     * 比较两个日期的先后.
     *
     * @param dateA dateA
     * @param dateB dateB
     * @return 0: dateA = dateB;<br/>
     * 1: dateA > dateB;<br/>
     * -1: dateA < dateB;
     */
    public static int compareDate(String dateA, String dateB) {
        try {
            long datetime1 = DateUtils.objectToDate(dateA).getTime();
            long datetime2 = DateUtils.objectToDate(dateB).getTime();
            return Long.compare(datetime1, datetime2);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

}
