package com.ww.javase;

/**
 * Created by weiwei on 2018/3/10.
 */
public class TestConference {
    public static void main(String[] args) {
        A a = new A(null, "", "", "");
        System.out.println(a);
        function(a);
        System.out.println(a);
    }

    private static void function(A a) {
        A a2 = new A("a", "b", "c", "d");
        a = a2;//标注1
//        a.setA(a2.getA());//标注2
    }
}

/**
 * 【总结】
 * Java中传参都是值传递，如果是基本类型，就是对值的拷贝，如果是对象，就是对引用地址的拷贝。
 */


class A {
    String a;
    String b;
    String c;
    String d;

    public A(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "A{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}
