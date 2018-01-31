package com.ww.javase.MuitiThread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by weiwei on 2018/1/25.
 */
public class ReenterLock implements Runnable {
    static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;


    @Override
    public void run() {
        for(int j = 0; j < 100000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock r1 = new ReenterLock();
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r1);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println(i);
    }
}
