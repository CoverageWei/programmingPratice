package com.ww.javase.jvm.jvmpractice;

/**
 * Created by weiwei on 2018/2/28.
 */
public class LocalVarGCTest {


    /**
     * [GC 8786K->6592K(251392K), 0.0146830 secs]
       [Full GC 6592K->6511K(251392K), 0.0377580 secs]
     */
    public void localvarGc1() {
        byte[] a = new byte[6 * 1024 * 1024];
        System.gc();
    }

    public void localvarGc2() {
        byte[] a = new byte[6 * 1024 * 1024];
        a = null;
        System.gc();
    }

    public void localvarGc3() {
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }
        System.gc();
    }

    public void localvarGc4() {
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }
        int c = 10;
        System.gc();
    }

    public void localvarGc5() {
        localvarGc1();
        System.gc();
    }

    /**
     * -XX:PrintGC
     * @param args
     */
    public static void main(String[] args) {
        LocalVarGCTest localVarGCTest = new LocalVarGCTest();
//        localVarGCTest.localvarGc1();
        localVarGCTest.localvarGc4();

    }

}
