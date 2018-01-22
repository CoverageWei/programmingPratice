package com.ww.javase.jvm;

/**
 * Created by weiwei on 2018/1/13.
 */
public class PrintTest {
    public static void main(String[] args) {
        printNum(-6);

    }

    private static void printNum(int number){
        for(int i = 0; i < 32; i++) {
            int t = (number & 0x80000000 >>> i) >>> (31 - i);
            System.out.println(t);
        }
    }
}
