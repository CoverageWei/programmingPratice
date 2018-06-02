package com.ww.javase.classloader;

/**
 * Created by weiwei on 2018/2/27.
 */
public class Test7 {


    public static void main(String[] args) throws Exception{
        // 获得系统类加载器
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        // 仅仅是加载阶段，不是对类的主动使用，不会导致 类初始化
        Class<?> clazz = classLoader.loadClass("com.ww.javase.classloader.Inner");

        System.out.println("---------------------");
        // 类主动使用，初始化
        clazz = Class.forName("com.ww.javase.classloader.Inner");
    }
    //---------------------
    //Inner static block


}


class Inner {
    static {
        System.out.println("Inner static block");
    }
}

