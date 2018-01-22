package com.ww.javase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by weiwei on 2017/4/27.
 */
public class TestBigDecimal {

    public static void main(String[] args) {
        testBase();
//        testMulti();
    }

    private static void testBase() {
//        Long a =290L;
//        Long b = 300L;
//        BigDecimal result = new BigDecimal(1.0 * a/b).setScale(3, BigDecimal.ROUND_FLOOR);
//        System.out.println(result);
//        System.out.println(result.compareTo(new BigDecimal(0.01)) >= 0);
//        System.out.println(result.setScale(2,BigDecimal.ROUND_FLOOR));
//
//        BigDecimal c = new BigDecimal("99.94");
//        BigDecimal d = new BigDecimal("99.97");
////        System.out.println(c.setScale(1, BigDecimal.ROUND_CEILING));
//        System.out.println(c.setScale(1, BigDecimal.ROUND_HALF_UP));
//        System.out.println(d.setScale(1, BigDecimal.ROUND_FLOOR));
//
//
//        List<Long> ids = new ArrayList<Long>();
//        ids.add(1L);
//        System.out.println(ids);
//        System.out.println(Arrays.asList(11));

        BigDecimal userScore = new BigDecimal(85);
        BigDecimal totalScore = new BigDecimal(100);
        BigDecimal qualifiedPercent = new BigDecimal(64.45);
        BigDecimal excellentPercent = new BigDecimal(80);
        BigDecimal userCompareScore = userScore.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal qualifiedScore = totalScore.multiply(qualifiedPercent).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal excellentScore = totalScore.multiply(excellentPercent).setScale(2, BigDecimal.ROUND_HALF_UP);

        System.out.println(userCompareScore);
        System.out.println(qualifiedScore);
        System.out.println(excellentScore);

        if(userCompareScore.compareTo(qualifiedScore) < 0) {
            System.out.println(1);
        } else if(userCompareScore.compareTo(excellentScore) < 0) {
            System.out.println(2);
        } else {
            System.out.println(3);
        }


    }

    private static void testMulti(){
        BigDecimal total = new BigDecimal(0);
        Long a = 20L;
        Long b = 20L;

        BigDecimal loaded1 = new BigDecimal(1.0 * a / b).setScale(2, BigDecimal.ROUND_DOWN);
        System.out.println(new BigDecimal(1.0 * a / b));
        total = total.add(loaded1);
        System.out.println(total);

        BigDecimal loaded2 = new BigDecimal(0.537).setScale(2, BigDecimal.ROUND_DOWN);
        total = total.add(loaded2);
        System.out.println(total);

        BigDecimal loaded3 = new BigDecimal(0.993).setScale(2, BigDecimal.ROUND_DOWN);
        total = total.add(loaded3);
        System.out.println(total);

        Long totalLong = Long.valueOf(100);
        BigDecimal aveSchdule = total.divide(new BigDecimal(3), BigDecimal.ROUND_DOWN);
        System.out.println("acverage : " + aveSchdule);
        System.out.println(aveSchdule.multiply(new BigDecimal(totalLong)));
        Long loadLong = Long.valueOf(aveSchdule.multiply(new BigDecimal(totalLong)).intValue());

        System.out.println(loadLong);
        System.out.println(totalLong);


    }

}
