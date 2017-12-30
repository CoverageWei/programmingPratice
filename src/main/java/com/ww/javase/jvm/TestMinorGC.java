package com.ww.javase.jvm;

/**
 * Created by weiwei on 2017/4/22.
 */
public class TestMinorGC {

    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
//        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
//        allocation5 = new byte[2 * _1MB];
//        allocation6 = new byte[2 * _1MB];
//        allocation7 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 出现一次Minor GC
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(1);
        testAllocation();
    }
}


/**
 *现象： 不触发 minorGC， 4M的数组在老年代分配
 *
 * 区别就是Client VM跟Server VM默认用的GC堆不同带来的。
 Client VM默认用Serial GC，Server VM默认用Parallel GC。后者有直接把大对象分配在old gen的逻辑，而前者没启动同类逻辑。
 *
 * VM options：-Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * Heap
 PSYoungGen      total 9216K, used 7471K [0x00000007ff600000, 0x0000000800000000, 0x0000000800000000)
 eden space 8192K, 91% used [0x00000007ff600000,0x00000007ffd4be30,0x00000007ffe00000)
 from space 1024K, 0% used [0x00000007fff00000,0x00000007fff00000,0x0000000800000000)
 to   space 1024K, 0% used [0x00000007ffe00000,0x00000007ffe00000,0x00000007fff00000)
 ParOldGen       total 10240K, used 4096K [0x00000007fec00000, 0x00000007ff600000, 0x00000007ff600000)
 object space 10240K, 40% used [0x00000007fec00000,0x00000007ff000010,0x00000007ff600000)
 PSPermGen       total 21504K, used 2983K [0x00000007f9a00000, 0x00000007faf00000, 0x00000007fec00000)
 object space 21504K, 13% used [0x00000007f9a00000,0x00000007f9ce9cb0,0x00000007faf00000)
 */