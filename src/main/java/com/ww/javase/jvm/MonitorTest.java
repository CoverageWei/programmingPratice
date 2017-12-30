package com.ww.javase.jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiwei on 2017/4/23.
 */
public class MonitorTest {
    static class OOMObject {
        private byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) {
//        Thread.sleep();
        List<OOMObject> list = new ArrayList<OOMObject>();
        for(int i = 0; i < num; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void createBusyThread(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    ;
                }
            }
        }, "testBusyThread");
        thread.start();
    }

    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static int add(int a, int b) {
        int sum = a + b;
        System.out.println(sum);
        return sum;
    }

    public static void main(String[] args) throws Exception{
//        System.out.println(Long.MAX_VALUE);       // 9223372036854775807
//        fillHeap(1000);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
//        createBusyThread();
//        br.readLine();
//        Object obj = new Object();
//        createLockThread(obj);
        add(1, 3);

    }
}
