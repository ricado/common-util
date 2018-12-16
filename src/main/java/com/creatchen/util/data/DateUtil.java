package com.creatchen.util.data;

import com.creatchen.util.constants.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final int BY_DATE = 0;//按照天
    public static final int BY_MONTH = 1;//按照月
    public static final int BY_HOUR = 2;//按照小时
    /** 一天时间的秒数 */
    public static final int SECOND_ONE_DAY = 86400;

    /**
     * 计算参数时间与当前时间的天数
     * @param time time
     * @return float
     * @throws ParseException
     */
    public static float timeIntervalOfDayFromNow(String time) throws ParseException{
        return timeIntervalOfHourFromNow(time)/24;
    }

    /**
     * 计算参数时间与当前时间的小时间隔
     * @param time time
     * @return float
     * @throws ParseException
     */
    public static float timeIntervalOfHourFromNow(String time) throws ParseException
    {
        if (time.length() < 10)
        {
            throw new IllegalArgumentException("createTime length should more than 10");
        }

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());

        Calendar createCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        if (time.length() >= 19)
        {
            try
            {
                dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD_HH_MM_SS.desc());
                time = time.substring(0, 19);
            }
            catch (Exception e)
            {
                dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD.desc());
                time = time.substring(0, 10);
            }
        }
        else
        {
            dateFormat = new SimpleDateFormat(DateFormat.YYYY_MM_DD.desc());
            time = time.substring(0, 10);
        }

        Date createDate = null;
        createDate = dateFormat.parse(time);
        createCalendar.setTime(createDate);
        float hourAfterApr = (float)(nowCalendar.getTimeInMillis() - createCalendar.getTimeInMillis()) / (1000 * 60 * 60);
        return hourAfterApr;
    }

    /**
     * 计算参数时间(毫秒数)与当前时间的小时间隔
     * @param milliSecond milliSecond
     * @return float
     * @throws ParseException
     */
    public static float timeIntervalHourFromNow(long milliSecond) {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        float hourAfterApr = (float)(nowCalendar.getTimeInMillis() - milliSecond) / (1000 * 60 * 60);
        return hourAfterApr;
    }

    public static Date getStr2Date(String strDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDate2String(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        return format.format(date);
    }

    /**
     * 获取距离当前日期diff的日期
     * @param type 0 天，1月
     * @param diff 负数表示前，正数表示后
     * @return
     */
    public static Date getDateFromNow(int type, int diff) {
        Calendar calendar = Calendar.getInstance();
        if(type == BY_DATE) {//天
            calendar.add(Calendar.DATE, diff);
        } else if(type == BY_MONTH) {//月
            calendar.add(Calendar.MONTH, diff);
        }
        return calendar.getTime();
    }

    /**
     * 获取距离某个具体日期diff的日期
     * @param type 0 天，1月
     * @param diff 负数表示前，正数表示后
     * @return
     */
    public static Date getDateFromTargetDate(Date targetDate, int type, int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);

        if(type == BY_DATE) {//天
            calendar.add(Calendar.DATE, diff);
        } else if(type == BY_MONTH) {//月
            calendar.add(Calendar.MONTH, diff);
        } else if(type == BY_HOUR) {//小时
            calendar.add(Calendar.HOUR, diff);
        }
        return calendar.getTime();
    }

    /**
     * 获取昨天0点开始时间 eg. 2013-07-23 00:00:00
     * @return
     */
    public static Date getYesterdayBeginTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

    /**
     * 获取昨天结束时间 eg.  2013-07-23 23:59:59
     * @return
     */
    public static Date getYesterdayEndTime(){
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);

        return calendar.getTime();
    }

    /**
     * 获取某天的开始时间 eg. 2013-07-23 00:00:00
     * @return
     */
    public static Date getBeginTimeOfDate(Date time){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

    /**
     * 获取当前月份的第一天开始时间
     * @param time
     * @return
     */
    public static Date getFirstDateOfCurMon(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

    /**
     * 计算2个日期间隔月份数
     * @param date1 日期字符串
     * @param date2 日期字符串
     * @param roundDown 默认向下取整
     * @return int
     */
    public static int getMonthBetween(Date date1, Date date2, boolean roundDown) {
        long diffTime=Math.abs(date1.getTime()-date2.getTime());
        double diffMonth = diffTime /(double)(3600 * 24 * 30 * 1000L);
        int result = (int)(roundDown ? Math.floor(diffMonth) : Math.ceil(diffMonth));
        return result < 0 ? 0 : result;
    }

    /**
     * 计算2个日期间隔天数
     * @param date1 date1
     * @param date2 date2
     * @param roundDown 默认向下取整
     * @return int
     */
    public static int getDaysBetween(Date date1, Date date2, boolean roundDown) {
        long diffTime=Math.abs(date1.getTime()-date2.getTime());
        double diffMonth = diffTime /(double)(3600 * 24 * 1000L);
        int result = (int)(roundDown ? Math.floor(diffMonth) : Math.ceil(diffMonth));
        return result < 0 ? 0 : result;
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTimeStr(){
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);//设置日期格式
        return (df.format(new Date()));//为获取当前系统时间
    }

}
