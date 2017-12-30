package com.ww.javase.jvm;

/**
 * Created by weiwei on 2017/4/22.
 */

/**
 * VM options:  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 */

public class TestPretenureSizeThreshold {
    private static final int _1MB = 1024 * 1024;

    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        TestPretenureSizeThreshold.testPretenureSizeThreshold();
    }
}

/**
 *
 *
 Heap
 PSYoungGen      total 9216K, used 5423K [0x00000007ff600000, 0x0000000800000000, 0x0000000800000000)
 eden space 8192K, 66% used [0x00000007ff600000,0x00000007ffb4be88,0x00000007ffe00000)
 from space 1024K, 0% used [0x00000007fff00000,0x00000007fff00000,0x0000000800000000)
 to   space 1024K, 0% used [0x00000007ffe00000,0x00000007ffe00000,0x00000007fff00000)
 ParOldGen       total 10240K, used 0K [0x00000007fec00000, 0x00000007ff600000, 0x00000007ff600000)
 object space 10240K, 0% used [0x00000007fec00000,0x00000007fec00000,0x00000007ff600000)
 PSPermGen       total 21504K, used 2939K [0x00000007f9a00000, 0x00000007faf00000, 0x00000007fec00000)
 object space 21504K, 13% used [0x00000007f9a00000,0x00000007f9cdef70,0x00000007faf00000)

 Process finished with exit code 0
 *
 *
 */
