package com.google.guaua.rateLimiter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weiwei on 2018/3/30.
 *
 * 计数器限流算法也是比较常用的，主要用来限制总并发数，比如数据库连接池大小、线程池大小、程序访问并发数等都是使用计数器算法。
 *
 * 使用AomicInteger来进行统计当前正在并发执行的次数，如果超过域值就简单粗暴的直接响应给用户，说明系统繁忙，请稍后再试或其它跟业务相关的信息。
 * 弊端：使用 AomicInteger 简单粗暴超过域值就拒绝请求，可能只是瞬时的请求量高，也会拒绝请求。
 */
public class CountRateLimiterDemo {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void exec(){
        if(count.get() >= 10) {
            System.out.println("请求用户过多，请稍后在试！"+System.currentTimeMillis()/1000);
        } else {
            count.incrementAndGet();
            try {
                // 处理核心逻辑
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                count.decrementAndGet();
            }
        }
    }



}
