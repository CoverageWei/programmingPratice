package com.ww.javase;

/**
 * Created by weiwei on 2018/1/31.
 */
public class BaseTest {


    public static void main(String[] args) {
//        Integer base = 1;
//        for(Integer i = base, j = i, q;;) {
//            System.out.println(i);
//            System.out.println(j);
////            System.out.println(q);
//        }

//        testBoolean();


        int index = 10;
        for (int i = index; i >= 0; i--) {
            if(i == 5) {
                continue;
            }
            System.out.println(i);
        }
    }

    private static void testBoolean(){
        Boolean creditView = null;
        System.out.println(Boolean.TRUE.equals(creditView));
        creditView = true;
        System.out.println(Boolean.TRUE.equals(creditView));
    }

}
