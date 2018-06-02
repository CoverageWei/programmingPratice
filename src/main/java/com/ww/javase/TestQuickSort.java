package com.ww.javase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiwei on 2018/5/14.
 */
public class TestQuickSort {
    public static void main(String[] args) {
        int[] source = new int[] {1, 2, 3, 5, 2, 3};
        quickSort(source);

        for(int i : source) {
            System.out.println(i);
        }

        System.out.println("*****");

        List<Integer> array = Arrays.asList(1, 2, 3, 5, 2, 3);
        quikSort(array);
        for(int i : array) {
            System.out.println(i);
        }


    }

    public static List<Integer> quikSort(List<Integer> source) {
        if(source == null || source.size() <= 0) {
            return source;
        }

        quickSort(source, 0, source.size() - 1);

        return source;
    }

    public static void quickSort(List<Integer> source, int start, int end) {
        if(start >= end) {
            return ;
        }

        int middle = partition(source, start, end);

        quickSort(source, start, middle - 1);
        quickSort(source, middle, end);
    }

    private static int partition(List<Integer> array, int start, int end) {
        int middleValue = array.get((start + end) / 2);

        while (start <= end) {
            while (array.get(start) < middleValue) {
                start++;
            }

            while (array.get(end) > middleValue) {
                end--;
            }

            if(start <= end) {
                swap(array, start, end);
                start++;
                end--;
            }
        }
        return start;
    }

    public static void swap(List<Integer> array, int p1, int p2) {
        array.set(p2, array.set(p1, array.get(p2)));
    }





    //------------------------- 数组形式

    public static int[] quickSort(int[] source) {
        if(source == null || source.length <= 0) {
            return source;
        }

        quickSort(source, 0, source.length - 1);
        return source;
    }

    public static void quickSort(int[] array, int start, int end) {
        if(start >= end) {
            return;
        }

        int middle = partition(array, start, end);

        quickSort(array, start, middle - 1);
        quickSort(array, middle, end);
    }

    private static int partition(int[] array, int start, int end) {
        int middleValue = array[(start + end) / 2];
        while (start <= end) {
            while (array[start] < middleValue) {
                start++;
            }

            while (array[end] > middleValue) {
                end--;
            }

            if(start <= end) {
                // 交换2个元素
                swap(array, start, end);
                start++;
                end--;
            }
        }
        //start是大于等于分界值的第一个元素，下一次就在以此分界点形成的2个子数组中进行递归分界
        return start;
    }

    private static void swap(int[] array, int p1, int p2) {
        int tmp = array[p1];
        array[p1] = array[p2];
        array[p2] = tmp;
    }


}
