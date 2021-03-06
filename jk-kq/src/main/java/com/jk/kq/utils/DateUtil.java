package com.jk.kq.utils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    /**
     * 日期相等 不比时间
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2) {
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    /**
     * 自定义时间相加
     * @return
     */
    public static Date dateAdd(String type,int amount, Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if("minute".equals(type)){
            calendar.add(Calendar.MINUTE, amount);
        }
        if("hour".equals(type)){
            calendar.add(Calendar.HOUR, amount);
        }
        if("day".equals(type)){
            calendar.add(Calendar.DATE, amount);
        }
        if("month".equals(type)){
            calendar.add(Calendar.MONTH, amount);
        }
        if("year".equals(type)){
            calendar.add(Calendar.YEAR, amount);
        }
        return calendar.getTime();


    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //不同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //同一年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 获取30分
     * @param number
     * @return
     */
    public static Date getDay(int hour,int min) {
        Date date = new Date();
        date.setDate(date.getDate());
        date.setHours(hour);
        date.setMinutes(min);
        date.setSeconds(0);
        return date;
    }

    /**
     * 获取30分
     * @param number
     * @return
     */
    public static Date getDateDay(Date date,int hour,int min) {
        date.setDate(date.getDate());
        date.setHours(hour);
        date.setMinutes(min);
        date.setSeconds(0);
        return date;
    }

    /**
     * LocalDate转Date
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());

    }

    public static LocalDate date2LocalDate(Date date) {
        if(null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    /**
     * 返回第一天
     */
    public static LocalDateTime firstday(LocalDateTime date){
        LocalDateTime firstday = LocalDateTime.of(date.getYear(),date.getMonth(),1,0,0,0);
        return firstday;
    }

    public static LocalDateTime firstday(LocalDate date){
        LocalDateTime firstday = LocalDateTime.of(date.getYear(),date.getMonth(),1,0,0,0);
        return firstday;
    }

    public static LocalDateTime firstday(Date input){
        Instant instant = input.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime firstday = LocalDateTime.of(date.getYear(),date.getMonth(),1,0,0,0);
        return firstday;
    }


    public static LocalDateTime    lastDay(LocalDateTime date){
        LocalDateTime lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay;
    }

    public static LocalDate    lastDay(LocalDate date){
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay;
    }

    public static LocalDate    lastDay(Date input){
        Instant instant = input.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = date.toLocalDate();
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay;
    }

    public static void main(String[] args) {
        int i = differentDays(new Date(), new Date());
        System.out.println(i);
        Date day = dateAdd("day", 1, new Date());
        System.out.println(day);
    }
}
