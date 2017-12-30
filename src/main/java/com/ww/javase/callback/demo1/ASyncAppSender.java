package com.ww.javase.callback.demo1;

/**
 * Created by weiwei on 2017/12/20.
 */
public class ASyncAppSender implements IMessage {

    @Override
    public void sendMessage(String msg, Boolean isOk) {
        System.out.println("##### hi, I'm upper sender...");
        System.out.println(String.format("##### send %s, message: %s", isOk, msg));
    }

    public void appSendMessage(final String msg, final Android android) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                android.sysSendMessage(msg, ASyncAppSender.this);
            }
        }).start();
        System.out.println("##### send finish...");
    }
}
