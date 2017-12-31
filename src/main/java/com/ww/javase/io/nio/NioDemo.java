package com.ww.javase.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by weiwei on 2017/12/31.
 */
public class NioDemo {

    public static void main(String[] args) {

        try {
            nioCopyFile("/Users/weiwei/mac_setting/linux 下文件夹区别.png", "/Users/weiwei/mac_setting/linux 下文件夹区别2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     * @param source
     * @param destination
     * @throws IOException
     */
    public static void nioCopyFile(String source, String destination) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(destination);
        FileChannel readChannel = fis.getChannel();
        FileChannel writeChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            int len = readChannel.read(byteBuffer);
            if(len == -1) {
                break;
            }
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
        }

        readChannel.close();
        writeChannel.close();
    }
}
