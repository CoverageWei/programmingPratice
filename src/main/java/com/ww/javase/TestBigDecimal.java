package com.ww.javase;

import java.math.BigDecimal;

/**
 * Created by weiwei on 2017/4/27.
 */
public class TestBigDecimal {

    public static void main(String[] args) {
//        testBase();
//        testMulti();
        System.out.println(caculateLearnProgress(130, 129));
        System.out.println(caculateLearnProgress(129, 129));
        System.out.println(caculateLearnProgress(128, 129));        // 0.99224
        System.out.println(caculateLearnProgress(127, 129));        // 0.9844
        System.out.println(caculateLearnProgress(98, 99));          // 0.9898
        System.out.println(caculateLearnProgress(99, 100));
        System.out.println(caculateLearnProgress(198, 200));
        System.out.println(caculateLearnProgress(0, 1000));
        System.out.println(caculateLearnProgress(1, 1000));
        System.out.println(caculateLearnProgress(9, 1000));
        System.out.println(caculateLearnProgress(10, 1000));
        System.out.println(caculateLearnProgress(12, 1000));
        System.out.println(caculateLearnProgress(15, 1000));
//        System.out.println((128 * 100) / 129);
//        System.out.println((127 * 100) / 129);
//        System.out.println((126 * 100) / 129);

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

    public static void test3(String[] args) {
        BigDecimal b = new BigDecimal(new BigDecimal("0.5251").doubleValue() * 100);
        StringBuilder sb = new StringBuilder();
        sb.append(b.setScale(0,BigDecimal.ROUND_HALF_UP)).append('%');
        System.out.println(sb.toString());

        System.out.println(new BigDecimal("40.00").setScale(0, BigDecimal.ROUND_HALF_UP));

        System.out.println(new BigDecimal("0.3").compareTo(new BigDecimal("0.2")));

        System.out.println(new BigDecimal(1.0 * new Long(3600000) / 1000)
                .setScale(1, BigDecimal.ROUND_HALF_UP));


        System.out.println(new BigDecimal(new BigDecimal("1.0").toString().replaceAll("\\.00", "").replaceAll("\\.0", "")));
        System.out.println(new BigDecimal(new BigDecimal("1.00").toString().replaceAll("\\.00", "").replaceAll("\\.0", "")));
        System.out.println(new BigDecimal(new BigDecimal("1.01").toString().replaceAll("\\.00", "").replaceAll("\\.0", "")));
        System.out.println(new BigDecimal(new BigDecimal("0.00").toString().replaceAll("\\.00", "").replaceAll("\\.0", "")));


        System.out.println(new BigDecimal(new BigDecimal("0.0").toString().replaceAll("\\.0", "")));
        System.out.println(new BigDecimal(new BigDecimal("1.4").toString().replaceAll("\\.0", "")));
        System.out.println(new BigDecimal(new BigDecimal("0.1").toString().replaceAll("\\.0", "")));

        System.out.println(new BigDecimal("61.10").stripTrailingZeros().toString().replace("E+1", "0"));
        System.out.println(new BigDecimal("61.00").stripTrailingZeros().toString().replace("E+1", "0"));
        System.out.println(new BigDecimal("61.12").stripTrailingZeros().toString().replace("E+1", "0"));
        System.out.println(new BigDecimal("60.12").stripTrailingZeros().toString().replace("E+1", "0"));
        System.out.println(new BigDecimal("0.00").stripTrailingZeros().toString().replace("E+1", "0"));        // 对0.00 和 0.0 不起效
        System.out.println(new BigDecimal("0.0").stripTrailingZeros().toString().replace("E+1", "0"));
        System.out.println(new BigDecimal("1.0").stripTrailingZeros().toString().replace("E+1", "0"));

        System.out.println(new BigDecimal("40.00").stripTrailingZeros().toString());
        System.out.println(new BigDecimal("40.00").stripTrailingZeros().toPlainString());
        System.out.println(new BigDecimal("43.00").stripTrailingZeros().toPlainString());
        System.out.println(new BigDecimal("40.00").stripTrailingZeros().toString().replace("E+1", "0"));


        System.out.println(BigDecimal.ZERO.compareTo(new BigDecimal("0.0")));
        Boolean stat = null;
        if(stat) {                      // 空指针
            System.out.println(1);
        } else {
            System.out.println(2);
        }

    }



    private static BigDecimal caculateLearnProgress(Integer finishedCount, Integer totalCount){
        if(finishedCount == null || totalCount == null
                || finishedCount.compareTo(0) <= 0
                || totalCount.compareTo(0) <= 0) {
            return BigDecimal.ZERO;
        }
        if(finishedCount >= totalCount) {
            return new BigDecimal(1).setScale(2, BigDecimal.ROUND_FLOOR);
        }

        BigDecimal resultProgress = BigDecimal.ZERO;
        Integer progress = (finishedCount * 100)/ totalCount;
        Integer modValue = (finishedCount * 100) % totalCount;

        if(progress <= 0) {
            resultProgress = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_DOWN);
        } else if(progress < 99) {
            // 小于 99%的进度，会四舍五入
            resultProgress = new BigDecimal(1.0 * finishedCount / totalCount)
                    .setScale(4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if(modValue == 0){
            // 等于99%, 取99% 【注意 BigDecimal 的0.99 实际取值是小于 0.99 的近似值】
            resultProgress = new BigDecimal(0.990).setScale(2, BigDecimal.ROUND_UP);
        } else {
            // 大于等于99% 但是 小于100%， 取99%
            resultProgress = new BigDecimal(1.0 * finishedCount / totalCount).setScale(2, BigDecimal.ROUND_FLOOR);
        }
        return resultProgress;
    }

}
