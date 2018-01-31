package com.ww.javase.collections;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by weiwei on 2018/1/31.
 */
public class TestConcurrentLinkedQueue {


    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("1");
        q.poll();
    }
}
