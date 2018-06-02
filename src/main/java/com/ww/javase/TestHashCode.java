package com.ww.javase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weiwei on 2018/4/2.
 */
public class TestHashCode {
    String name;

    public TestHashCode(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Map<TestHashCode, String> map = new HashMap<TestHashCode, String>();
        map.put(new TestHashCode("ww"), "ww");
        String name = map.get(new TestHashCode("ww"));
        System.out.println(name);

    }
}
