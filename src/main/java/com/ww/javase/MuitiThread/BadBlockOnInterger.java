package com.ww.javase.MuitiThread;

/**
 * Created by weiwei on 2018/1/25.
 */
public class BadBlockOnInterger implements Runnable {
    public static Integer i = 0;

    static BadBlockOnInterger instance = new BadBlockOnInterger();

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++){
//            synchronized (i) {
            synchronized (instance) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);

    }
}
