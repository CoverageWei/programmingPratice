package com.google.guaua.rateLimiter;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 之前的限流方式都不能很好地应对突发请求，即瞬间请求可能都被允许从而导致一些问题；
 * 因此在一些场景中需要对突发请求进行整形，整形为平均速率请求处理（比如5r/s，则每隔200毫秒处理一个请求，平滑了速率）。
 * 这个时候有两种算法满足我们的场景：令牌桶和漏桶算法。Guava框架提供了令牌桶算法实现，可直接拿来使用。
 *
 * Guava RateLimiter提供了令牌桶算法实现：平滑突发限流(SmoothBursty)和平滑预热限流(SmoothWarmingUp)实现。
 * 本类是 SmoothBursty 实现
 * Created by weiwei on 2017/4/3.
 */
public class SmoothBurstyDemo {
    public static void main(String[] args) {
//        rateLimiterTest1();
//        rateLimiterTest1_1();
//        rateLimiterTest2();
//        rateLimiterTest3();
//        rateLimiterTest4();
        rateLimiterTest5();
    }

    public static void acquire(RateLimiter limiter, Integer permits) {
        if(permits == null)
            limiter.acquire();
        else
            limiter.acquire(permits);
    }

    /**
     * 将突发请求速率平均为了固定请求速率
     *
     ##### 0 cost: 0
     ##### 1 cost: 203
     ##### 2 cost: 201
     ##### 3 cost: 201
     ##### 4 cost: 200
     ##### 5 cost: 196
     */
    public static void rateLimiterTest1(){
        RateLimiter limiter = RateLimiter.create(5);

        Long start = System.currentTimeMillis();
        Long end = null;
        for(int i = 0; i <= 5; i++){
            acquire(limiter, null);
            end = System.currentTimeMillis();
            System.out.println(String.format("##### %s cost: %s", i, (end - start)));
            start = end;
        }
    }

    /**
     *
     ##### 0 cost: 0
     ##### 1 cost: 206
     ##### 2 cost: 197
     ##### 3 cost: 201
     ##### 4 cost: 200
     ##### 5 cost: 199
     ##### 5 cost: 0        // 消费之前2s积攒的令牌
     ##### 6 cost: 0
     ##### 7 cost: 0
     ##### 8 cost: 0
     ##### 9 cost: 0        // 消费完最后一个 积攒的令牌
     ##### 10 cost: 0       // 开始正常计算
     ##### 11 cost: 205     // 平滑请求
     ##### 12 cost: 198
     */
    public static void rateLimiterTest1_1(){
        RateLimiter limiter = RateLimiter.create(5);

        Long start = System.currentTimeMillis();
        Long end = null;
        for(int i = 0; i <= 5; i++){
            acquire(limiter, null);
            end = System.currentTimeMillis();
            System.out.println(String.format("##### %s cost: %s", i, (end - start)));
            start = end;
        }

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        start = System.currentTimeMillis();
        for(int i = 0; i <= 7; i++){
            acquire(limiter, null);
            end = System.currentTimeMillis();
            System.out.println(String.format("##### %s cost: %s", i + 5, (end - start)));
            start = end;
        }
    }


    /**
     * RateLimiter.create(5) 表示桶的容量为5且每秒新增5个令牌;
     * limiter.acquire(5)表示突发了5个请求，令牌桶算法允许一定程度的突发，所以可以一次性消费5个令牌[未来的令牌]，
     * 但接下来的limiter.acquire(1)将等待差不多1秒桶中才能有令牌，且接下来的请求也整形为固定速率了.
     ##### 1 cost: 1
     ##### 2 cost: 1002
     ##### 3 cost: 201
     */
    public static void rateLimiterTest2(){
        RateLimiter limiter = RateLimiter.create(5);

        Long start = System.currentTimeMillis();
        acquire(limiter, 5);
        Long end1 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 1, (end1 - start)));

        acquire(limiter, 1);
        Long end2 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 2, (end2 - end1)));

        acquire(limiter, 1);
        Long end3 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 3, (end3 - end2)));
    }

    /**
     * 第一秒突发了10个请求，令牌桶算法也允许了这种突发（允许消费未来的令牌），
     * 但接下来的limiter.acquire(1)将等待差不多2秒桶中才能有令牌，且接下来的请求也整形为固定速率了。
     *
     ##### 1 cost: 0
     ##### 2 cost: 2005
     ##### 3 cost: 197
     */
    public static void rateLimiterTest3(){
        RateLimiter limiter = RateLimiter.create(5);

        Long start = System.currentTimeMillis();
        acquire(limiter, 10);
        Long end1 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 1, (end1 - start)));

        acquire(limiter, 1);
        Long end2 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 2, (end2 - end1)));

        acquire(limiter, 1);
        Long end3 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 3, (end3 - end2)));
    }

    /**
     ##### 1 cost: 0
     ##### 2 cost: 2068     //消费之前2s积攒的令牌
     ##### 3 cost: 0        //消费之前2s积攒的令牌
     ##### 4 cost: 0        //第四个开始就是正常计算的，如同创建完 RateLimiter后就开始 acquire() 可以获取；
     ##### 5 cost: 505      //阻塞500+毫秒等待令牌
     ##### 6 cost: 499
     */
    public static void rateLimiterTest4(){
        RateLimiter limiter = RateLimiter.create(2);

        Long start = System.currentTimeMillis();
        acquire(limiter, null);
        Long end1 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 1, (end1 - start)));

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        acquire(limiter, null);
        Long end2 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 2, (end2 - end1)));

        acquire(limiter, null);
        Long end3 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 3, (end3 - end2)));

        acquire(limiter, null);
        Long end4 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 4, (end4 - end3)));

        acquire(limiter, null);
        Long end5 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 5, (end5 - end4)));

        acquire(limiter, null);
        Long end6 = System.currentTimeMillis();
        System.out.println(String.format("##### %s cost: %s", 6, (end6 - end5)));
    }


    /**
     *   1526173949939
         0.0                    // limiter.acquire(20)
         1526173949940
         ##### 1 cost: 1
         3.95833                // 等待4s
         0.197879

     Process finished with exit code 0
     */


    /**
     * 1526174035200
     0.0                        // limiter.acquire(100)
     1526174035201
     ##### 1 cost: 1
     19.959365
     0.194271
     */


    /**
     * 1526174153642
     0.0
     1526174153643
     ##### 1 cost: 1
     3.973415
     0.194186
     3.996075
     */
    public static void rateLimiterTest5(){

        RateLimiter limiter = RateLimiter.create(5);

        Long start = System.currentTimeMillis();
        System.out.println(start);
        System.out.println(limiter.acquire(20));
        Long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println(String.format("##### %s cost: %s", 1, (end - start)));

        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(20));
        System.out.println(limiter.acquire(1));
    }

}
