package com.ww.javase.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by weiwei on 2018/1/10.
 */
public class AioClient {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        Future<Void> future = client.connect(new InetSocketAddress("127.0.0.1", 8888));
        future.get();

        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer, null, new CompletionHandler<Integer, Object>() {

            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("client received: " + new String(buffer.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(10000);

    }

}
