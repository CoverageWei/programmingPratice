package com.google.guaua.rateLimiter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiwei on 2018/3/30.
 *
 *
 * 限流算法
 * 令牌桶(Token Bucket)、漏桶(leaky bucket)和计数器算法是最常用的三种限流的算法。
 *
 * 使用Semaphore信号量来控制并发执行的次数，如果超过域值信号量，则进入阻塞队列中排队等待获取信号量进行执行。
 * 如果阻塞队列中排队的请求过多超出系统处理能力，则可以在拒绝请求。
 *
 * 相对Atomic优点：如果是瞬时的高并发，可以使请求在阻塞队列中排队，而不是马上拒绝请求，从而达到一个流量削峰的目的。
 *
 */
public class SemaphoreRateLimiterDemo {
    private static Semaphore semaphore = new Semaphore(10);

    public static void exec(){
        if(semaphore.getQueueLength() > 100) {
            System.out.println("当前等待排队的任务数大于100，请稍候再试...");
        }

        try {
//            semaphore.tryAcquire();
            semaphore.tryAcquire(3000, TimeUnit.MILLISECONDS);
//            semaphore.tryAcquire(2);
//            semaphore.tryAcquire(2, 3000, TimeUnit.MILLISECONDS);

            Thread.sleep(1000);
            System.out.println("--" + System.currentTimeMillis() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}


