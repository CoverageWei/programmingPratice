package com.ww.javase.jvm;

/**
 * Created by weiwei on 2017/4/22.
 */
public class PSAllocTest {

    private static final int _1KB = 1024;
    private static final int _1MB = _1KB * 1024;

    /**
     * eden区的初始大小为20M，用MaxNewSize限定了新生代的初始大小为40M。
     *
     * -Xmx100M -XX:+PrintGCDetails -XX:MaxNewSize=40M -XX:+PrintTenuringDistribution
     *
     * -Xmx100M -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+PrintTLAB -XX:MaxNewSize=40M -XX:+PrintTenuringDistribution
     */
    public static void testAllocation() {
        byte[] byte1 = new byte[_1MB*5];
        byte[] byte2 = new byte[_1MB*10];
        byte1 = null;
        byte2 = null;
        byte[] byte3 = new byte[_1MB*5];
        byte[] byte4 = new byte[_1MB*10];
        byte3 = null;
        byte4 = null;
        byte[] byte5 = new byte[_1MB*15];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}

/**
 *
 *http://hllvm.group.iteye.com/group/topic/38293
 * 总结一下过程：

 第1个数组：
 大小5MB，慢速路径上在young gen分配直接成功；

 第2个数组：
 大小10MB，慢速路径上在young gen分配直接成功；

 第3个数组：
 大小5MB，慢速路径上在young gen分配失败，
 （进入循环）再尝试在young gen分配，还是失败，
 没达到直接去old gen分配的大小限制（试图分配的大小大于eden的capacity的一半）），
 于是真的触发一次minor GC，eden清空，
 再尝试在young gen分配，成功；

 第4个数组：
 大小10MB，慢速路径上在young gen分配直接成功；

 第5个数组：
 大小15MB，慢速路径上在young gen分配失败，
 （进入循环）再尝试在young gen分配，还是失败，
 达到了去old gen直接分配的大小限制，在old gen上分配成功。没有触发GC。
 *
 *
 * eden区会在GC之后分配内存，但是注意： 如果分配内存大于eden区capacity一半就会直接在old gen分配的规则。
 */



/**
 * -Xmx100M -XX:+PrintGCDetails -XX:MaxNewSize=40M -XX:+PrintTenuringDistribution
 *
 * 新生代: 40M, eden: 32, from : 4 , to: 4
 * [GC
 Desired survivor size 4194304 bytes, new threshold 7 (max 15)
 [PSYoungGen: 22569K->432K(30208K)] 22569K->5552K(91648K), 0.0049020 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
 [GC
 Desired survivor size 4194304 bytes, new threshold 7 (max 15)
 [PSYoungGen: 26544K->384K(36864K)] 31664K->5504K(98304K), 0.0020750 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
 Heap
 PSYoungGen      total 36864K, used 932K [0x00000007fd800000, 0x0000000800000000, 0x0000000800000000)
 eden space 32768K, 1% used [0x00000007fd800000,0x00000007fd889050,0x00000007ff800000)
 from space 4096K, 9% used [0x00000007ffc00000,0x00000007ffc60020,0x0000000800000000)
 to   space 4096K, 0% used [0x00000007ff800000,0x00000007ff800000,0x00000007ffc00000)
 ParOldGen       total 61440K, used 5120K [0x00000007f9c00000, 0x00000007fd800000, 0x00000007fd800000)
 object space 61440K, 8% used [0x00000007f9c00000,0x00000007fa100010,0x00000007fd800000)
 PSPermGen       total 21504K, used 3011K [0x00000007f4a00000, 0x00000007f5f00000, 0x00000007f9c00000)
 object space 21504K, 14% used [0x00000007f4a00000,0x00000007f4cf0c78,0x00000007f5f00000)
 *
 */