package com.ww.javase.classloader;

/**
 * Created by weiwei on 2018/2/27.
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());

        Class clazz2 = Class.forName("com.ww.javase.classloader.C");
        System.out.println(clazz2.getClassLoader());
    }
    //null
    //sun.misc.Launcher$AppClassLoader@20e90906
}

class C {

}
