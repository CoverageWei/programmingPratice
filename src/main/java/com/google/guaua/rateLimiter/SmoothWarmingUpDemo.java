package com.google.guaua.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 *
 * 因为SmoothBursty允许一定程度的突发，会有人担心如果允许这种突发，假设突然间来了很大的流量，那么系统很可能扛不住这种突发。
 * 因此需要一种平滑速率的限流工具，从而系统冷启动后慢慢的趋于平均固定速率（即刚开始速率小一些，然后慢慢趋于我们设置的固定速率）。
 * Guava也提供了SmoothWarmingUp来实现这种需求，其可以认为是漏桶算法，但是在某些特殊场景又不太一样。
 *
 * 应用级限流
 * 假设将应用部署到多台机器，应用级限流方式只是单应用内的请求限流，不能进行全局限流。因此我们需要分布式限流和接入层限流来解决这个问题。
 *
 * Created by weiwei on 2017/4/3.
 */
public class SmoothWarmingUpDemo {

    public static void main(String[] args) {
        rateLimiterTest1();
    }

    public static void acquire(RateLimiter limiter, Integer permits) {
        if(permits == null)
            limiter.acquire();
        else
            limiter.acquire(permits);
    }

    /**
     *速率是梯形上升速率的，也就是说冷启动时会以一个比较大的速率慢慢到平均速率；然后趋于平均速率（梯形下降到平均速率）。
     * 可以通过调节warmupPeriod参数实现一开始就是平滑固定速率。
     *
     ##### 0 cost: 0
     ##### 1 cost: 526
     ##### 2 cost: 357
     ##### 3 cost: 218
     ##### 4 cost: 203
     ##### 5 cost: 198
     ##### after sleep, 5 cost: 0
     ##### after sleep, 6 cost: 369
     ##### after sleep, 7 cost: 221
     ##### after sleep, 8 cost: 199
     ##### after sleep, 9 cost: 203
     ##### after sleep, 10 cost: 198
     ##### after sleep, 11 cost: 199
     ##### after sleep, 12 cost: 201
     */
    public static void rateLimiterTest1(){
        RateLimiter limiter = RateLimiter.create(5, 1000L, TimeUnit.MILLISECONDS);

        Long start = System.currentTimeMillis();
        Long end = null;
        for(int i = 0; i <= 5; i++){
            acquire(limiter, null);
            end = System.currentTimeMillis();
            System.out.println(String.format("##### %s cost: %s", i, (end - start)));
            start = end;
        }

        try {
            Thread.sleep(1000L);
            acquire(limiter, null);
            end = System.currentTimeMillis();
            System.out.println(String.format("##### after sleep, %s cost: %s", 6, (end - start)));
            start = end;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
