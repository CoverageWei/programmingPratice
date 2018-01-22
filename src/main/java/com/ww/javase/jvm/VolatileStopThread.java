package com.ww.javase.jvm;

/**
 * Created by weiwei on 2018/1/14.
 */
public class VolatileStopThread extends Thread{
    private volatile boolean stop = false;
//    private boolean stop = false;
    public void stopMe(){
        stop=true;
    }

    @Override
    public void run(){
        int i=0;
        while(!stop){
            System.out.println(i++);
        }
        System.out.println("Stop thread");
    }

    public static void main(String args[]) throws InterruptedException{
        VolatileStopThread t=new VolatileStopThread();
        t.start();
        Thread.sleep(1000);
        t.stopMe();
        Thread.sleep(1000);
    }
}

