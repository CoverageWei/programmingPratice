package com.ww.javase;

import java.io.*;

/**
 * Created by weiwei on 2018/5/6.
 */
public class TestSerializable {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test1(){
        Person person = new Person();
        person.setAge(26);
        System.out.println("原始对象： " + person);
        System.out.println("原始对象： " + person.hashCode());

        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("person.ser"));
            outputStream.writeObject(person);

            inputStream = new ObjectInputStream(new FileInputStream("person.ser"));
            Person person1 = (Person) inputStream.readObject();
            System.out.println("序列化之后： " + person1);
            System.out.println("序列化之后： " + person1.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 一、没有显式声明 序列化id 的时候：
     * 1、如果类没有变更，则可以 序列化成功：
     * 序列化之后： Person{age=26}
       序列化之后： 258795999
     *
     * 2、如果类发生变更：则会异常，提示：流文件中的 序列化id 与 本地类中的不匹配，不能序列化！
     * java.io.InvalidClassException: com.ww.javase.Person; local class incompatible:
     * stream classdesc serialVersionUID = 8814744911735698145,
     * local class serialVersionUID = 7067461304966423028
     *
     * 二、显式声明 序列化id，值保持不变：
     * 无论类是否发生了改变，都可以成功序列化：【注意：新增的属性 不会有】
     * 序列化之后： Person{age=26}
       序列化之后： 2012561903
     *
     *
     *
     * 总结：
     * 1、序列化id 的原理：
     * java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
     * 在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地实体类中的serialVersionUID进行比较，
     * 如果相同则认为是一致的，便可以进行反序列化，否则就会报序列化版本不一致的异常。
     *
     *
     * 没有显示的定义一个名为“serialVersionUID”、类型为long的变量时，
     * Java序列化机制会根据编译时的class自动生成一个serialVersionUID作为序列化版本比较；
     * 【如果类没有发生变更，则运行时生成的序列化id 是一致的】
     *
     */
    private static void test2(){
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("person.ser"));
            Person person1 = (Person) inputStream.readObject();
            System.out.println("序列化之后： " + person1);
            System.out.println("序列化之后： " + person1.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


class Person implements Serializable {
    private static final long serialVersionUID = 3507374975851089032L;

    private int age;
    private String name;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                '}';
    }
}


enum SingletonEnum {
    INSTANCE;

    private int value;
    private String desc;


}



