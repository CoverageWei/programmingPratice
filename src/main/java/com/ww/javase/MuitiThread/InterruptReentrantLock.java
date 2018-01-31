package com.ww.javase.MuitiThread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by weiwei on 2018/1/25.
 */
public class InterruptReentrantLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int index;

    public InterruptReentrantLock(int i) {
        index = i;
    }


    @Override
    public void run() {
        try {
            if(index == 1) {
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(index + "interrupt...");
        } finally {
            if(lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }

            System.out.println(index + ": 线程退出");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        InterruptReentrantLock reentrantLock1 = new InterruptReentrantLock(1);
        InterruptReentrantLock reentrantLock2 = new InterruptReentrantLock(2);

        Thread thread1 = new Thread(reentrantLock1);
        Thread thread2 = new Thread(reentrantLock2);

        thread1.start();
        thread2.start();

        Thread.sleep(2000);

        thread2.interrupt();
    }
}
