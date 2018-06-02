package com.ww.javase.singleton;

/**
 * Created by weiwei on 2018/5/10.
 */
public class TestPowerOfTwo {

    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(-1);

        System.out.println(isPowerOfTwo(2));
        System.out.println(isPowerOfTwo(3));
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

}
