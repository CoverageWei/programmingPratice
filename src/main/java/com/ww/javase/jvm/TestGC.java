package com.ww.javase.jvm;

import java.util.Vector;

/**
 * Created by weiwei on 2018/1/14.
 */
public class TestGC {

    public static void main(String[] args) {
//        byte[] b=null;
//        for(int i=0;i<10;i++) {
//            b=new byte[1*1024*1024];
//        }

        Vector v = new Vector();
        for(int i=0; i < 25; i++) {
            v.add(new byte[1 * 1024 * 1024]);
        }

    }


    /**
     *
     * -Xmx20m -Xms20m -Xmn7m -XX:+PrintGCDetails
     *
     * [GC [PSYoungGen: 5205K->480K(6656K)] 5205K->1504K(19968K), 0.0145300 secs] [Times: user=0.00 sys=0.00, real=0.02 secs]
     [GC [PSYoungGen: 5797K->448K(6656K)] 6821K->2496K(19968K), 0.0122320 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     Heap
     PSYoungGen      total 6656K, used 1616K [0x00000007ff900000, 0x0000000800000000, 0x0000000800000000)
     eden space 6144K, 19% used [0x00000007ff900000,0x00000007ffa241b8,0x00000007fff00000)
     from space 512K, 87% used [0x00000007fff80000,0x00000007ffff0000,0x0000000800000000)
     to   space 512K, 0% used [0x00000007fff00000,0x00000007fff00000,0x00000007fff80000)
     ParOldGen       total 13312K, used 2048K [0x00000007fec00000, 0x00000007ff900000, 0x00000007ff900000)
     object space 13312K, 15% used [0x00000007fec00000,0x00000007fee00020,0x00000007ff900000)
     PSPermGen       total 21504K, used 2980K [0x00000007f9a00000, 0x00000007faf00000, 0x00000007fec00000)
     object space 21504K, 13% used [0x00000007f9a00000,0x00000007f9ce9198,0x00000007faf00000)

     Process finished with exit code 0
     *
     *
     *
     *
     *
     *
     */


}



