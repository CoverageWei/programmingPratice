package com.ww.javase.MuitiThread;

import java.util.concurrent.*;

/**
 * Created by weiwei on 2018/4/1.
 */
public class TestPolicy {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L,  TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        for(int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("11");
                }
            });
        }
        System.out.println("finished...");      // 前面抛异常，不会执行

//        ExecutorService executorService1 = Executors.newFixedThreadPool(10);

    }
}
