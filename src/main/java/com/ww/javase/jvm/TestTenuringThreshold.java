package com.ww.javase.jvm;

/**
 * Created by weiwei on 2017/4/22.
 */
public class TestTenuringThreshold {
    private static final int _1MB = 1024 * 1024;

    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;

        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;

        allocation3 = new byte[4 * _1MB];

    }

    public static void main(String[] args) {
        TestTenuringThreshold.testTenuringThreshold();
    }

}


/**
 *
 * VM options: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 *
 Heap
 PSYoungGen      total 9216K, used 5515K [0x00000007ff600000, 0x0000000800000000, 0x0000000800000000)
 eden space 8192K, 67% used [0x00000007ff600000,0x00000007ffb62f30,0x00000007ffe00000)
 from space 1024K, 0% used [0x00000007fff00000,0x00000007fff00000,0x0000000800000000)
 to   space 1024K, 0% used [0x00000007ffe00000,0x00000007ffe00000,0x00000007fff00000)
 ParOldGen       total 10240K, used 8192K [0x00000007fec00000, 0x00000007ff600000, 0x00000007ff600000)
 object space 10240K, 80% used [0x00000007fec00000,0x00000007ff400020,0x00000007ff600000)
 PSPermGen       total 21504K, used 2986K [0x00000007f9a00000, 0x00000007faf00000, 0x00000007fec00000)
 object space 21504K, 13% used [0x00000007f9a00000,0x00000007f9cea9e0,0x00000007faf00000)
 *
 */


/**
 * VM options: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
 *
 Heap
 PSYoungGen      total 9216K, used 5679K [0x00000007ff600000, 0x0000000800000000, 0x0000000800000000)
 eden space 8192K, 69% used [0x00000007ff600000,0x00000007ffb8be78,0x00000007ffe00000)
 from space 1024K, 0% used [0x00000007fff00000,0x00000007fff00000,0x0000000800000000)
 to   space 1024K, 0% used [0x00000007ffe00000,0x00000007ffe00000,0x00000007fff00000)
 ParOldGen       total 10240K, used 8192K [0x00000007fec00000, 0x00000007ff600000, 0x00000007ff600000)
 object space 10240K, 80% used [0x00000007fec00000,0x00000007ff400020,0x00000007ff600000)
 PSPermGen       total 21504K, used 2986K [0x00000007f9a00000, 0x00000007faf00000, 0x00000007fec00000)
 object space 21504K, 13% used [0x00000007f9a00000,0x00000007f9cea9e0,0x00000007faf00000)

 */
