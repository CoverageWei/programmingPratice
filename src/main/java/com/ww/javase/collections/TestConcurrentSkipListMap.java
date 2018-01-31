package com.ww.javase.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by weiwei on 2018/1/31.
 */
public class TestConcurrentSkipListMap {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
        int i = 0;
        for(int j = 30; j > 0; j--) {
            map.put(j, i);
            i++;
        }

        // 保持 key的顺序
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key: " + entry.getKey() + " , value :" + entry.getValue());
        }
    }
}
