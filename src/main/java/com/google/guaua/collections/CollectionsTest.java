package com.google.guaua.collections;

import com.google.common.collect.*;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by weiwei on 2017/4/30.
 */
public class CollectionsTest {

    public static void testSortedSet() {
        ImmutableSet<String> stringSortedSet = ImmutableSortedSet.of("a", "c", "f", "b", "e", "d", "a", "b");
        System.out.println(stringSortedSet);
        System.out.println(stringSortedSet.asList().get(1));
    }

    public static void main(String[] args) {
//        testSortedSet();

//        testMultimap();

//        doCheckCollectionJoin();

//        checkCollectionContain();

        testLists();

//        testMapsDifference();

//        testSets();
    }

    void putMyObject(String key, Object value) {
        Map<String, List<Object>> myClassListMap = new HashMap<String, List<Object>>();
        List<Object> myClassList = myClassListMap.get(key);
        if(myClassList == null) {
            myClassList = new ArrayList<Object>();
            myClassListMap.put(key,myClassList);
        }
        myClassList.add(value);
    }

    /**
     * https://my.oschina.net/leejun2005/blog/179645
     * 理解： Multimap 本质上不是map，而是 collection，可以说其内部实现 依赖了map
     */
    public static void testMultimap(){
        ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        // Adding some key/value
        listMultimap.put("Fruits", "Bannana");
        listMultimap.put("Fruits", "Apple");
        listMultimap.put("Fruits", "Pear");
        listMultimap.put("Fruits", "Pear");
        listMultimap.put("Vegetables", "Carrot");

        System.out.println(listMultimap.size());    // 5

        System.out.println("*********** Fruits ");
        List<String> fruits = listMultimap.get("Fruits");
        System.out.println(fruits.size());  // 4
        System.out.println(fruits); // [Bannana, Apple, Pear, Pear]
        System.out.println(ImmutableSet.copyOf(fruits));    // [Bannana, Apple, Pear]
        System.out.println(Sets.newHashSet(fruits));        // [Pear, Bannana, Apple]
        System.out.println(new HashSet<String>(fruits));    // [Pear, Bannana, Apple]

        System.out.println("*********** Vegetables ");
        Collection<String> vegetables = listMultimap.get("Vegetables");
        System.out.println(vegetables.size());  // 1
        System.out.println(vegetables);     // [Carrot]

        System.out.println("*********** iterator the ArrayListMultimap ");
        for(String string : listMultimap.values()) {    // size: 5
            System.out.println(string);
        }

        System.out.println("*********** ArrayListMultimap toString()");
        System.out.println(listMultimap.toString());    // {Fruits=[Bannana, Apple, Pear, Pear], Vegetables=[Carrot]}

        System.out.println("asMap() : " + listMultimap.asMap());    // asMap() : {Fruits=[Bannana, Apple, Pear, Pear], Vegetables=[Carrot]}
        System.out.println("keySet() : " + listMultimap.keySet());
        System.out.println("keys() : " + listMultimap.keys());
        System.out.println("keys().size() : " + listMultimap.keys().size());
        System.out.println("entries() : " + listMultimap.entries());


        System.out.println("*********** remove ArrayListMultimap ");
        listMultimap.remove("Fruits", "Pear");
        System.out.println(listMultimap.get("Fruits"));     // [Bannana, Apple, Pear]

        listMultimap.removeAll("Fruits");
        System.out.println(listMultimap.get("Fruits"));     // [],return a empty collection, not null
    }

    private static List<Integer> lista = Lists.newArrayList(1,2,3,4,5,6);
    private static List<Integer> listb = Lists.newArrayList(6,7,8,9,10);

    /**
     * 判断集合是否有交集
     * @return
     */
    public static boolean checkCollectionJoin1(){

        for(Integer a : lista) {
            for(Integer b : listb) {
                if(a.equals(b))
                    return true;
            }
        }
        return false;
    }

    /**
     * JDK 自带的集合方法：disjoint
     * @return
     */
    public static boolean checkCollectionJoin2(){
        return !Collections.disjoint(lista, listb);
    }

    public static boolean checkCollectionJoin3(){
        return CollectionUtils.containsAny(lista, listb);
    }

    public static void doCheckCollectionJoin(){
        System.out.println(checkCollectionJoin1());
        System.out.println(checkCollectionJoin2());
        System.out.println(checkCollectionJoin3());
    }

    public static void checkCollectionContain(){
        System.out.println(lista.containsAll(listb));
        System.out.println(lista.containsAll(Arrays.asList(1,2,3)));
    }


    public static void testLists(){
        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9);
        List<List<Integer>> partitions = Lists.partition(list, 5);
        System.out.println(partitions);     // [[1, 2, 3, 4, 5], [6, 7, 8, 9]]
        System.out.println(partitions.size());
    }

    public static void testMapsDifference(){
        Map<String, Integer> leftMap = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> rightMap = ImmutableMap.of("c", 10, "d", 4, "e", 5);
        System.out.println(leftMap);
        System.out.println(rightMap);
        MapDifference<String, Integer> difference = Maps.difference(leftMap, rightMap);
        System.out.println("entriesInCommon: " + difference.entriesInCommon()); // {}
        System.out.println("entriesDiffering: " + difference.entriesDiffering());   // {c=(3, 10)}
        System.out.println("entriesOnlyOnLeft: " + difference.entriesOnlyOnLeft()); // {b=2, a=1}
        System.out.println("entriesOnlyOnRight: " + difference.entriesOnlyOnRight());   // {d=4, e=5}
    }

    /**
     * 求交集
     */
    public static void testSets(){
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(wordsWithPrimeLength, primes);
        System.out.println("intersection: " + intersection.immutableCopy());   // [two, three, seven]

        // Sets.difference(setA, setB) 参数顺序有关系，以setA 作为基本比较对象
        Sets.SetView<String> difference = Sets.difference(wordsWithPrimeLength, primes);
        System.out.println("difference1: " + difference.immutableCopy());    // [one, six, eight]

        Sets.SetView<String> difference2 = Sets.difference(primes, wordsWithPrimeLength);
        System.out.println("difference2: " + difference2.immutableCopy());    // [five]


        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");
        // {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
        //  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}
        Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
        System.out.println(product);
        // {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}
        Set<Set<String>> animalSets = Sets.powerSet(animals);
        System.out.println(animalSets);

        // 源set 需要自己判断非null
//        Set<Integer> set1 = null;   // java.lang.NullPointerException: set1
        Set<Integer> set1 = Sets.newHashSet(1,2,3,9,10);
        Set<Integer> set2 = Sets.newHashSet(1,2,3,4,5,6,7,8);
        System.out.println("union: " + Sets.union(set1, set2));     // [9, 1, 10, 2, 3, 4, 5, 6, 7, 8]

    }
}
