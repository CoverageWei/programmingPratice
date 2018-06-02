package com.ww.javase.singleton;

/**
 * Created by weiwei on 2018/4/6.
 *
 *
 * 创建对象过程，实例化一个对象要分为三个步骤：
    分配内存空间
    初始化对象
    将内存空间的地址赋值给对应的引用
   但是由于重排序的缘故，步骤2、3可能会发生重排序


 有两个解决办法：

 不允许初始化阶段步骤2 、3发生重排序。[volatile]
 允许初始化阶段步骤2 、3发生重排序，但是不允许其他线程“看到”这个重排序。 [类初始化]
 *
 */

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * volatile + double check
 * 当singleton声明为volatile后，步骤2、步骤3就不会被重排序了
 */
public class Singleton {
    private static volatile Singleton instance;

    private Singleton(){}

    public static Singleton getInstance(){
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for(int i=0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Singleton.getInstance().hashCode());
                }
            }).start();
        }
    }
}


/**
 * 基于类初始化的解决方案
 * 利用classloder的机制来保证初始化instance时只有一个线程。JVM在类初始化阶段会获取一个锁，这个锁可以同步多个线程对同一个类的初始化。
 */
class LazySingleton2 {
    private LazySingleton2(){
    }

    private static class SingletonHolder{
        private static LazySingleton2 instance = new LazySingleton2();
    }

    protected static LazySingleton2 getInstance(){
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        for(int i=0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(LazySingleton2.getInstance().hashCode());
                }
            }).start();
        }

    }
}


// ---------------------------------------------- 反射的影响

/**
 * 静态内部类方式，在反射的作用下，单例结构是会被破坏的
 *
 * Instance 1 hash:1379251688
   Instance 2 hash:1099149023

 根据哈希值可以看出，反射破坏了单例的特性
 */
class Singleton2Test {
    public static void main(String[] args) {
        LazySingleton2 instance1 = LazySingleton2.getInstance();

        LazySingleton2 instance2 = null;

        try {
            Class<LazySingleton2> clazz = LazySingleton2.class;
            Constructor<LazySingleton2> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance2 = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //检查两个实例的hash值
        System.out.println("Instance 1 hash:" + instance1.hashCode());
        System.out.println("Instance 2 hash:" + instance2.hashCode());
    }
}


/**
 * 保证了 反射无法破坏其单例特性
 */
class LazySingleton3 implements Serializable{

    private static boolean initialized = false;
    private LazySingleton3 (){
        synchronized (LazySingleton3.class) {
            if(!initialized) {
                initialized = !initialized;
            } else {
                throw new RuntimeException("单例已被生成");
            }
        }
    }
////    volatile 方式保障 标志的可见性，也是ok的
//    private static volatile boolean initialized = false;
//    private LazySingleton3 (){
//        if(!initialized) {
//            initialized = !initialized;
//        } else {
//            throw new RuntimeException("单例已被生成");
//        }
//    }

    private static class LazySingleton3Holder {
        private static LazySingleton3 instance = new LazySingleton3();
    }

    public static LazySingleton3 getInstance(){
        return LazySingleton3Holder.instance;
    }
}


class Singleton3Test {
    public static void main(String[] args) {
        LazySingleton3 instance1 = LazySingleton3.getInstance();

        LazySingleton3 instance2 = null;

        try {
            Class<LazySingleton3> clazz = LazySingleton3.class;
            Constructor<LazySingleton3> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance2 = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //检查两个实例的hash值
        System.out.println("Instance 1 hash:" + instance1.hashCode());
        System.out.println("Instance 2 hash:" + instance2.hashCode());
    }
}

/**
 * 多线程 反射测试
 */
class Singleton3Test2 {
    public static void main(String[] args) {
        LazySingleton3 instance1 = LazySingleton3.getInstance();

        for(int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LazySingleton3 instance2 = null;

                    try {
                        Class<LazySingleton3> clazz = LazySingleton3.class;
                        Constructor<LazySingleton3> constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        instance2 = constructor.newInstance();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println(instance2 != null ? instance2.hashCode() : null);
                }
            }).start();
        }

        //检查两个实例的hash值
        System.out.println("Instance 1 hash:" + instance1.hashCode());
//        System.out.println("Instance 2 hash:" + instance2.hashCode());
    }
}


/**
 * 在分布式系统中，有些情况下你需要在单例类中实现 Serializable 接口。这样你可以在文件系统中存储它的状态并且在稍后的某一时间点取出。
 * 测试 这个懒汉式V3版在序列化和反序列化之后是否仍然保持单例。
 *
 * 修改：LazySingleton3 实现 Serializable 接口；
 *
 * 单例遭到破坏：
 * instance1 hashCode=1278835946
   instance2 hashCode=1893072126
 *
 */
class Singleton3Test3 {
    public static void main(String[] args) {
        LazySingleton3 instance1 = LazySingleton3.getInstance();

        try {
            LazySingleton3 instance2 = null;
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("singleton3.ser"));
            output.writeObject(instance1);
            output.close();

            ObjectInputStream input = new ObjectInputStream(new FileInputStream("singleton3.ser"));
            instance2 = (LazySingleton3) input.readObject();
            input.close();

            System.out.println("instance1 hashCode=" + instance1.hashCode());
            System.out.println("instance2 hashCode=" + instance2.hashCode());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}


// ---------------------------------------------- 序列化的影响

/**
 * 总结：
 * 实际上，我们在实际项目中一般从 懒汉式v2、懒汉式v3、懒汉式v4中，根据实际情况三选一即可，并不是非要选择懒汉式v4作为单例来实现。
 */


/**
 * 终极方案：解决了 双重检查的synchronized性能问题（静态内部类方式） + 反射问题 + 序列化问题；
 *
 * 为了避免此问题，我们需要提供 readResolve() 方法的实现。readResolve(）代替了从流中读取对象。
 * 这就确保了在序列化和反序列化的过程中没人可以创建新的实例。
 */
class LazySingleton4 implements Serializable{

    // 注意：readResolve()方式，必须确保 单例对象中 所有 对象引用类型的实例域 都必须是 transient，普通类型则不必
    private int age;
    private transient String name;
    private transient Object object;

    private static boolean initialized = false;
    private LazySingleton4 (){
        synchronized (LazySingleton3.class) {
            if(!initialized) {
                initialized = !initialized;
            } else {
                throw new RuntimeException("单例已被生成");
            }
        }
    }
////    volatile 方式保障 标志的可见性，也是ok的
//    private static volatile boolean initialized = false;
//    private LazySingleton4 (){
//        if(!initialized) {
//            initialized = !initialized;
//        } else {
//            throw new RuntimeException("单例已被生成");
//        }
//    }

    private static class LazySingleton4Holder {
        private static LazySingleton4 instance = new LazySingleton4();
    }

    public static LazySingleton4 getInstance(){
        return LazySingleton4Holder.instance;
    }

    /**
     * readResolve(）代替了从流中读取对象
     * readResolve 特性，在反序列化之后，新建对象上的 readResolve(）方法将被调用，然后该方法返回的对象引用将被返回，取代新建的对象；
     * 反序列化新建的对象 将被垃圾回收！！！！
     *
     * @return
     */
    private Object readResolve() {
        return getInstance();
    }
}

/**
 * 问题解决：
 * readResolve(）代替了从流中读取对象，这就确保了在序列化和反序列化的过程中没人可以创建新的实例。
 *
 * 【注意】
 *  1、readResolve()方式，必须确保 单例对象中 所有 对象引用类型的实例域 都必须是 transient，基本类型不需要是 transient；
 *
 *
 *
 * instance1 hashCode=1658853491
   instance2 hashCode=1658853491
 */
class Singleton4Tests {
    public static void main(String[] args) {
        LazySingleton4 instance1 = LazySingleton4.getInstance();

        try {
            LazySingleton4 instance2 = null;
            ObjectOutput output = new ObjectOutputStream(new FileOutputStream("singleton3.ser"));
            output.writeObject(instance1);
            output.close();

            ObjectInput input = new ObjectInputStream(new FileInputStream("singleton3.ser"));
            instance2 = (LazySingleton4) input.readObject();
            input.close();

            System.out.println("instance1 hashCode=" + instance1.hashCode());
            System.out.println("instance2 hashCode=" + instance2.hashCode());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}












