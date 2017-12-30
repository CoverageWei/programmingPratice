package com.google.guaua.String;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by weiwei on 2017/5/8.
 */
public class SplitterTest {
    public static void main(String[] args) {
//        testJDKSplit();
        testSplitter();
    }


    public static void testJDKSplit() {
        String sourceStr = ",a,,b,";
        String[] result = sourceStr.split(",");     // ["", "a", "", "b"]
        printStirngs(result);   // 4  ###a####b#

    }

    public static void testSplitter(){
        String sourceStr = ",a,,b,";
        Iterable<String> result = Splitter.on(",").trimResults().omitEmptyStrings().split(sourceStr);
        System.out.println(result);     // [a, b]
        System.out.println(Lists.newArrayList(result));     // [a, b]
    }



    public static void printStirngs(String[] source) {
        System.out.println(source.length);
        for(String str : source) {
            System.out.print("#" + str + "#");
        }
    }
}
