package com.ww.javase;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weiwei on 2018/1/3.
 */
public class TestPattern {

    public static void main(String[] args) {
        System.out.println(new BigDecimal(0).compareTo(new BigDecimal(0)) >= 0);
        System.out.println(new BigDecimal(0).compareTo(new BigDecimal(0.00)) >= 0);
        System.out.println(new BigDecimal(0).compareTo(new BigDecimal(0.01)) >= 0);
        System.out.println(new BigDecimal(0).compareTo(new BigDecimal(0.10)) >= 0);

        System.out.println("*****");

        System.out.println(new BigDecimal(100).compareTo(new BigDecimal(100.00)) < 0);
        System.out.println(new BigDecimal(100).compareTo(new BigDecimal(100.01)) < 0);
        System.out.println(new BigDecimal(100).compareTo(new BigDecimal(100.10)) < 0);
        System.out.println(new BigDecimal(100).compareTo(new BigDecimal(99.99)) < 0);

//        System.out.println("**********************");
//        BigDecimal b = new BigDecimal("12.10");
//        System.out.println(b.toString());
//        System.out.println(isNumber(b.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b2 = new BigDecimal("12.12");
//        System.out.println(b2.toString());
//        System.out.println(isNumber(b2.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b3 = new BigDecimal("12.120");
//        System.out.println(b3.toString());
//        System.out.println(isNumber(b3.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b4 = new BigDecimal("12.125");
//        System.out.println(b4.toString());
//        System.out.println(isNumber(b4.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b5 = new BigDecimal("12.1");
//        System.out.println(b5.toString());
//        System.out.println(isNumber(b5.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b6 = new BigDecimal("12.0");
//        System.out.println(b6.toString());
//        System.out.println(isNumber(b6.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b7 = new BigDecimal("12");
//        System.out.println(b7.toString());
//        System.out.println(isNumber(b7.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b8 = new BigDecimal("0.1");
//        System.out.println(b8.toString());
//        System.out.println(isNumber(b8.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b9 = new BigDecimal("0.01");
//        System.out.println(b9.toString());
//        System.out.println(isNumber(b9.toString()));
//
//        System.out.println("**********************");
//        BigDecimal b10 = new BigDecimal("0.010");
//        System.out.println(b10.toString());
//        System.out.println(isNumber(b10.toString()));

//        System.out.println(isNumber("12.10"));
//        System.out.println(isNumber("12.12"));
//        System.out.println(isNumber("12.120"));
//        System.out.println(isNumber("12.125"));
//        System.out.println(isNumber("12.1"));
//        System.out.println(isNumber("12.0"));
//        System.out.println(isNumber("12"));
//        System.out.println(isNumber("1"));
    }

    private static boolean isNumber(String str){
        // 判断小数点后2位的数字的正则表达式
        final Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }
}
