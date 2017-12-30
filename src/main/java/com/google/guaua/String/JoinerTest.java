package com.google.guaua.String;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by weiwei on 2017/5/8.
 */
public class JoinerTest {

    public static void main(String[] args) {
//        testJoinerWithNull();
//        testJoinerSkipNull();
        testJoinerUseNull();
    }

    public static void testJoinerWithNull(){
        Joiner joiner = Joiner.on("-");
        String result = joiner.join("Harry", null, "Jack", "Hamilton");   // java.lang.NullPointerException  Preconditions.checkNotNull
        System.out.println(result);
        String result2 = joiner.join(null, null);
        System.out.println(result2);
    }

    public static void testJoinerSkipNull(){
        Joiner joiner = Joiner.on("-").skipNulls();
        String result = joiner.join("Harry", null, "Jack", "Hamilton");     // Harry-Jack-Hamilton
        System.out.println(result);
        String result2 = joiner.join(null, null);   // ""
        System.out.println(result2);
    }

    public static void testJoinerUseNull(){
        Joiner joiner = Joiner.on("-").useForNull("null");
        String result = joiner.join("Harry", null, "Jack", "Hamilton");     // Harry-Jack-Hamilton
        System.out.println(visiableStr(result));
        String result2 = joiner.join(null, null);   // ""
        System.out.println(visiableStr(result2));
        System.out.println(result2.length());

        String result3 = joiner.join(Arrays.asList(1, 2, 3, 4, 5));     // 1-2-3-4-5
        System.out.println(visiableStr(result3));
        String result4 = joiner.join(new ArrayList<Object>());      // ""
        System.out.println(visiableStr(result4));
        System.out.println(result4.length());   // 0
    }

    public static String visiableStr(String str){
        return "*" + str + "*";
    }
}
