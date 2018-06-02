package com.ww.javase.MuitiThread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by weiwei on 2018/4/6.
 */
public class TestAtomicStampedReference {
    private static AtomicInteger atomicInteger = new AtomicInteger(100);
    private static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(100, 1);

    public static void main(String[] args) throws InterruptedException {
        Thread at1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInteger.compareAndSet(100, 110);
                atomicInteger.compareAndSet(110, 100);
            }
        });

        Thread at2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("AtomicInteger:" + atomicInteger.compareAndSet(100, 120));

            }
        });

        at1.start();
        at2.start();
        at1.join();
        at2.join();


        Thread atsr1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                atomicStampedReference.compareAndSet(100, 110, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                atomicStampedReference.compareAndSet(110, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            }
        });

        Thread atsr2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedReference.getStamp();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("AtomicStampedReference:" + atomicStampedReference.compareAndSet(100, 120, stamp, stamp + 1));

            }
        });

        atsr1.start();
        atsr2.start();
        atsr1.join();
        atsr2.join();
    }
}
