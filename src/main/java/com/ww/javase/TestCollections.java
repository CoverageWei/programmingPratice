package com.ww.javase;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by weiwei on 2018/1/20.
 */
public class TestCollections {

    public static void main(String[] args) {
        testList();
    }

    public static void testList(){
        List<Integer> list = Lists.newArrayList(1, 1, 2, 2, 3, 3, 4);
        System.out.println(list);

        list = new ArrayList<Integer>(new HashSet<Integer>(list));
        System.out.println(list);
    }
}
