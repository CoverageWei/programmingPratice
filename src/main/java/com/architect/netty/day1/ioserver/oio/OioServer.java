package com.architect.netty.day1.ioserver.oio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiwei on 2017/12/31.
 */
public class OioServer {

    /**
     * 多连接 对应 单线程处理，同时只能处理1个连接，会阻塞
     * @param args
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException {
//        ServerSocket server = new ServerSocket(10011);
//        System.out.println("服务器启动...");
//
//        while (true) {
//            final Socket socket = server.accept();
//            System.out.println("接受一个新客户端！");
//            handle(socket);
//        }
//    }

    /**
     * 1连接对应一个线程处理，消耗性能
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(10011);
        System.out.println("服务器启动...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true) {
            final Socket socket = server.accept();
            System.out.println("接受一个新客户端！");

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handle(socket);
                }
            });
        }
    }

    private static void handle(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();

            while (true) {
                int read = inputStream.read(bytes);
                if(read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("socket 关闭");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
