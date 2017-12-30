package com.ww.javase;

import java.io.Serializable;

/**
 * Created by weiwei on 2016/12/25.
 */
public class GetClassNameDemo implements Serializable{
    private static int secret = 99;
    private int allKnowInt;
    private String allKnow;

    static {
        System.out.println("static process...");
    }


    private static final long serialVersionUID = -5840550594037583050L;


    public static class SubGetClassName {

    }

    public static void main(String[] args) {
        System.out.println(SubGetClassName.class.getName());        // com.ww.javaSE.GetClassNameDemo$SubGetClassName
        System.out.println(SubGetClassName.class.getSimpleName());  // SubGetClassName
        System.out.println(SubGetClassName.class.getCanonicalName());   // com.ww.javaSE.GetClassNameDemo.SubGetClassName


        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(SubGetClassName.class.getClassLoader().getSystemClassLoader());


        System.out.println(GetClassNameDemo.class.getClassLoader().getResource("").toString()); // 当前 classpath 路径
        // file:/Users/weiwei/work/my_idea_projects/target/classes/

        System.out.println(secret);
        System.out.println(new GetClassNameDemo().allKnowInt);
        System.out.println(new GetClassNameDemo().allKnow);


    }

}

/*
        其实getName、getCanonicalNam这两个方法没有什么不同的，对于大部分class来说，但是对于array或内部类等就显示出来了。
        getName返回的是[[Ljava.lang.String之类的表现形式，而getCanonicalName返回的就是跟我们声明类似的形式。
        BTW，在load class的时候需要的名字也是getName这种的名字

        在根据类名字创建文件的时候最好使用getCanonicalName()
*/