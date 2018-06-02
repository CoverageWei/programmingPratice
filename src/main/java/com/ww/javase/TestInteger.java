package com.ww.javase;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by weiwei on 2018/4/1.
 */
public class TestInteger {
    public static void main(String[] args) {
        Integer i1 = new Integer(1);
        Integer i11 = new Integer(1);
        Integer i111 = new Integer(1);
        System.out.println((i1==i11));

        System.out.println(new Integer(1).hashCode());
        System.out.println(new Integer(1).hashCode());


        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();

        i1++;
        System.out.println(i1.hashCode());

        System.out.println("**********");
        Integer i3 = new Integer(126);
        System.out.println(i3.hashCode());
        i3++;
        System.out.println((i3).hashCode());
        i3++;
        System.out.println((i3).hashCode());
        i3++;
        System.out.println((i3).hashCode());
        i3++;
        System.out.println((i3).hashCode());


        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {

            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        List<Long> longs = Collections.synchronizedList(new ArrayList<Long>());

        Collections.synchronizedSet(new HashSet<Long>());

    }
}
