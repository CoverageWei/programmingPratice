package com.ww.javase.MuitiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiwei on 2018/3/12.
 */
public class TestThreadExcption {

    private static ExecutorService indexThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        for(int i = 1; i < 1000; i++) {
            Runnable runnable = null;
            if(i % 30 == 0) {
                runnable = new ExceptionThread(i);
            } else {
                runnable = new SuccessThread(i);
            }

            indexThreadPool.execute(runnable);
            System.out.println("submitTask..." + i);
        }
    }

}
    class SuccessThread implements Runnable {

        private int index;

        public SuccessThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[ " + index + " ]run success..." + Thread.currentThread().getId());
        }
    }

    class ExceptionThread implements Runnable {

        private int index;

        public ExceptionThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("[ " + index + " ] exception... " + Thread.currentThread().getId());
            throw new RuntimeException("run exception..." + Thread.currentThread().getId());
        }
    }


