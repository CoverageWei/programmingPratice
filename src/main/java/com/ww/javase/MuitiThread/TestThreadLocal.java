package com.ww.javase.MuitiThread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiwei on 2018/2/3.
 */
public class TestThreadLocal {

    static CountDownLatch countDownLatch = new CountDownLatch(10000);

    static volatile ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(System.currentTimeMillis() + " " + this.toString() + " is gc");
        }
    };

    public static class ParseDate implements Runnable {

        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if(threadLocal.get() == null) {
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(System.currentTimeMillis() + " " + this.toString() + " is gc");
                        }
                    });
                    System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getId() + " thread : create SimpleDateFormat");
                }
                threadLocal.get().parse("2017-02-03 19:38:" + (i % 60));
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() + ": mission complete!!");

        threadLocal = null;
        System.gc();
        System.out.println(System.currentTimeMillis() + ": first GC complete!!");

        threadLocal = new ThreadLocal<SimpleDateFormat>();
        countDownLatch = new CountDownLatch(10000);
        for(int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() + ": mission complete!!");
        Thread.sleep(1000);
        System.gc();
        System.out.println(System.currentTimeMillis() + ": second GC complete!!");
        es.shutdown();
    }

}
