package com.ww.javase;

import org.apache.commons.collections.set.SynchronizedSet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiwei on 2018/5/9.
 */
public class TestConcurrentHashMap {

    private static ConcurrentHashMap<Long, ConcurrentHashMap<Long, Boolean>> test = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        test.putIfAbsent(1L, null);
    }

}
