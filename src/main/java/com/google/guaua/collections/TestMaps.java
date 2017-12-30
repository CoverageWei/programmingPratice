package com.google.guaua.collections;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import com.sun.jdi.DoubleType;

import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

/**
 * Created by weiwei on 2017/5/2.
 */
public class TestMaps {

    public static void main(String[] args) {
        testIndex();

        invertFrom();

        testForMap();
    }


    /**
     * Multimaps.index(Iterable, Function)
     */
    public static void testIndex() {
        ImmutableSet digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        };

        ImmutableListMultimap<Integer, String> digitsByLength= Multimaps.index(digits, lengthFunction);
        System.out.println(digitsByLength);
        // {4=[zero, four, five, nine], 3=[one, two, six], 5=[three, seven, eight]}

        /*
        *  digitsByLength maps:
        *  3 => {"one", "two", "six"}
        *  4 => {"zero", "four", "five", "nine"}
        *  5 => {"three", "seven", "eight"}
        */
    }

    public static void invertFrom(){
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));
        System.out.println(multimap);
        System.out.println(multimap.get("a"));

        TreeMultimap<Integer, String> inverse = TreeMultimap.create();
        inverse = Multimaps.invertFrom(multimap, inverse);
        System.out.println(inverse);
        Set<String> oneSet = inverse.get(1);
        System.out.println(oneSet);

        /*
        注意我们选择的实现，因为选了TreeMultimap，得到的反转结果是有序的

        * inverse maps:
        *  1 => {"a"}
        *  2 => {"a", "b", "c"}
        *  3 => {"c"}
        *  4 => {"a", "b"}
        *  5 => {"c"}
        *  6 => {"b"}
        */
    }

    public static void testForMap(){
        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        System.out.println(map);
        SetMultimap<String, Integer> multimap = Multimaps.forMap(map);
        System.out.println(multimap);
        /* multimap：["a" => {1}, "b" => {1}, "c" => {2}] */

        Multimap<Integer, String> inverse = HashMultimap.create();
        inverse = Multimaps.invertFrom(multimap, inverse);
        System.out.println(inverse);
        // inverse：[1 => {"a","b"}, 2 => {"c"}]
    }
}
