package com.google.guaua.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * Created by weiwei on 2018/3/30.
 */
public class RateLimiterDemo {
    public static void main(String[] args) {
//        testNoRateLimiter();
//        testWithRateLimiter();
        test();
    }

    public static void testNoRateLimiter() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

    public static void testWithRateLimiter() {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(10.0); // 每秒不超过10个任务被提交
        for (int i = 0; i < 100; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

    public static void test() {
        final RateLimiter rateLimiter = RateLimiter.create(1.0);
        for(int i=0; i < 100; i++) {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
//            rateLimiter.acquire();
                    rateLimiter.tryAcquire(1, 100, TimeUnit.MILLISECONDS);
                    System.out.println("success : " + Thread.currentThread().getId());
                }
            });
            thread.start();
        }
    }

}