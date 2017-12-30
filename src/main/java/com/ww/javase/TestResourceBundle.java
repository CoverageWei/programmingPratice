package com.ww.javase;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by weiwei on 2017/1/8.
 */
public class TestResourceBundle {

    public static void main(String[] args) {
        Locale locale1 = new Locale("zh", "CN");
        ResourceBundle resb1 = ResourceBundle.getBundle("myres", locale1);
        System.out.println(resb1.getString("aaa"));

        Locale aDefault = Locale.getDefault();
        System.out.println("Locale.getDefault() = " + aDefault);
        ResourceBundle resb2 = ResourceBundle.getBundle("myres", Locale.getDefault());
        System.out.println(resb1.getString("aaa"));

        Locale locale3 = new Locale("en", "US");
        ResourceBundle resb3 = ResourceBundle.getBundle("myres", locale3);
        System.out.println(resb3.getString("aaa"));
    }

    /**
     * java.util.Locale.getDefault() 方法获取默认语言环境的当前值的Java虚拟机实例。
     * 如果使用默认的Locale，那么在英文操作系统上，会选择myres_en_US.properties或myres.properties资源文件。
     * */

}
