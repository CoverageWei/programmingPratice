package com.ww.javase.classloader;

/**
 * Created by weiwei on 2018/3/9.
 */
public class TestLoad {


    /**
     * -XX:+TraceClassLoading
     *
     * ...
     * [Loaded com.ww.javase.classloader.TestLoad from file:/Users/weiwei/work/my_github/programmingPratice/target/classes/]
     * ...
     * [Loaded com.ww.javase.classloader.Parent from file:/Users/weiwei/work/my_github/programmingPratice/target/classes/]
       [Loaded com.ww.javase.classloader.Child from file:/Users/weiwei/work/my_github/programmingPratice/target/classes/]
       ...
       parent init;
       100
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Child.v);
    }

}

class Parent {
    static {
        System.out.println("parent init;");
    }

    public static int v = 100;
//    public static final int v = 100;         // final 不会引起类初始化
}

class Child extends Parent{
    static {
        System.out.println("child init");
    }
}
