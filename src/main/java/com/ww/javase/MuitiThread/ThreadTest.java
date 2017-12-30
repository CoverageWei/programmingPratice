package com.ww.javase.MuitiThread;

/**
 * Created by weiwei on 2017/12/30.
 */
public class ThreadTest {

//    //主线程 interrupt 无效，不会停止thread1
//    public static void main(String[] args) {
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println("I'm running...");
//                    Thread.yield();
//                }
//            }
//        });
//        thread1.start();
//        System.out.println("main thread running...");
//        thread1.interrupt();
//    }
    


    /**
     * sleep 期间被中断后，线程被其他线程中断，则会 catch 中断异常：InterruptedException
     * 在 中断异常处理中，中断状态会被清除；
     * 需要重新设置 中断状态，上层中断判断 才能生效；
     */
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(Thread.currentThread().isInterrupted()) {
                        System.out.println("I'm interrupted and terminated...");
                        break;
                    }
                    System.out.println("I'm running...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(String.format("catch InterruptedException... [interruptStatus = %s]",
                                Thread.currentThread().isInterrupted()));
                        Thread.currentThread().interrupt();
                        System.out.println(String.format("catch InterruptedException... [interruptStatus = %s]",
                                Thread.currentThread().isInterrupted()));
                    }
                }
            }
        });
        thread1.start();

        System.out.println("main thread running...");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
    }







}
