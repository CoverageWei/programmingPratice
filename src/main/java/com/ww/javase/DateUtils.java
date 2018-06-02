package com.ww.javase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiwei on 2018/4/25.
 */
public class DateUtils {

    private DateUtils(){}

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式化的类是非同步的，建议为每一个线程创建独立的格式化实例。
     * 如果多个线程并发访问同一个格式化实例，就必须在外部添加同步机制。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000000; i++) {
                        try {
                            DATE_FORMAT.parse("2014-01-01 00:00:00");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(3000000);
    }

}