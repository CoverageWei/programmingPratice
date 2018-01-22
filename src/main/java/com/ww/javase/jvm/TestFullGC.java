package com.ww.javase.jvm;

import java.util.HashMap;

/**
 * Created by weiwei on 2018/1/21.
 */
public class TestFullGC {


    public static void main(String[] args) {

        new printThread().start();

        new CostMemoryThread().start();
    }

    public static class printThread extends Thread {
        public static final long startTime = System.currentTimeMillis();

        @Override
        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println("time: " + t);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }

    public static class CostMemoryThread extends Thread {
        HashMap<Long, byte[]> map = new HashMap<Long, byte[]>();

        @Override
        public void run() {

            try {
                while (true) {
                    if(map.size() * 102400000 / 1024/ 1024 >= 450) {
                        System.out.println("————————————开始清理——————————： " + map.size());
                        map.clear();
                    }

                    for(int i=0; i < 1024; i++) {
                        map.put(System.currentTimeMillis(), new byte[100 * 1024 * 000]);
                    }
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
