package com.google.guaua;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.sun.tools.corba.se.idl.constExpr.Equal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weiwei on 2017/4/28.
 */
public class BaseTest {

    private static final Integer TYPE_ZERO = 0;
    private static final Integer TYPE_ONE = 1;

    /**
     * 好像没有 apache.commons.long 下的工具类全面
     * @param input
     */
    public static void testNullStringJudge(String input){
        System.out.println(Strings.isNullOrEmpty(input));
    }

    public static void doTestNullString(){
        testNullStringJudge("");
        testNullStringJudge("  ");
        testNullStringJudge("11");
    }

    public static void main(String[] args) {
//        doTestNullString();

//            doTestNullEquals();

//        System.out.println(new BigDecimal(1.0 * 2 / 5));
//        System.out.println(new BigDecimal(1.0 * 2 / 5).setScale(3, BigDecimal.ROUND_HALF_UP));
//        System.out.println(new BigDecimal(1.0 * 2 / 5).setScale(3, BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_UP));

//        Map<Long, Long> map = new HashMap<Long, Long>();
//        System.out.println(map.get(null));

        System.out.println(new BigDecimal(1).compareTo(new BigDecimal(1)) <= 0);
        System.out.println(new BigDecimal(1).compareTo(new BigDecimal(1.0)) <= 0);
    }

    public static void doTestNullEquals(){
        testNullEquals(0);
        testNullEquals(1);
        testNullEquals(null);
    }

    public static void testNullEquals(Integer type) {
        System.out.println("null == null: " + null == null);
        System.out.println(type);
        System.out.println(Objects.equal(type, TYPE_ZERO));
        System.out.println(Objects.equal(TYPE_ZERO, type));
        System.out.println("TYPE_ZERO.equals(type) = " + TYPE_ZERO.equals(type));
        System.out.println("(TYPE_ZERO == type) = " + (TYPE_ZERO == type));
        System.out.println("(type == TYPE_ZERO) = " + (type == TYPE_ZERO));
        System.out.println("type.equals(TYPE_ZERO) = " + type.equals(TYPE_ZERO));

    }
}
