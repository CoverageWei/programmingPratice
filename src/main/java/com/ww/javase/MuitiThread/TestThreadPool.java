package com.ww.javase.MuitiThread;

import java.util.Currency;
import java.util.concurrent.*;

/**
 * Created by weiwei on 2018/1/30.
 */
public class TestThreadPool {
    public static class MyTask implements Runnable {

        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(String.format("running ... [ Thread ID: %s, taskName = %s ]",
                    Thread.currentThread().getId(), name));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(30, 50, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行： " + ((MyTask) r).name + ", startTime = " + System.currentTimeMillis());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成： " + ((MyTask) r).name + ", endTime = " + System.currentTimeMillis());
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        for(int i = 0; i < 5000; i++) {
            MyTask myTask = new MyTask("task-" + i);
            es.execute(myTask);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("shutDown time: " + System.currentTimeMillis());
        es.shutdown();
    }
}