package com.ww.javase;

import java.util.Calendar;

/**
 * Created by weiwei on 2018/2/2.
 */
public class GetDateTimeStrapByMonth {


    //获得本月第一天0点时间
    public static long getTimesMonthmorning(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);

        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND,0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }

    //获得本月最后一天0点时间
    public static long getTimesMonthLastTime(int year, int month){
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();

        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month);

        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        ca.set(Calendar.MINUTE, 0);
        //将秒至0
        ca.set(Calendar.SECOND,0);
        //将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        // 获取本月最后一天的时间戳
        return (ca.getTimeInMillis()+86400000);
    }

    public static void main(String [] args){
        for(int i = 0; i < 12; i++) {
//            System.out.println((i+1) + "月： " + getTimesMonthmorning(2017, i) + " + " + getTimesMonthLastTime(2017,i));

            System.out.println(String.format("select count(*) from s2_provider_team where gmt_create >= %s and gmt_create <= %s;", getTimesMonthmorning(2017, i), getTimesMonthLastTime(2017,i)));
        }

        for(int i = 0; i < 12; i++) {
//            System.out.println((i+1) + "月： " + getTimesMonthmorning(2017, i) + " + " + getTimesMonthLastTime(2017,i));

            System.out.println(String.format("select count(*) from s2_provider_staff_invite where status=2 and gmt_modified >= %s and gmt_modified <= %s;", getTimesMonthmorning(2017, i), getTimesMonthLastTime(2017,i)));
        }
    }





}
