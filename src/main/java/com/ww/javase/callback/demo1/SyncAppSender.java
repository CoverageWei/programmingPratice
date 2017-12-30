package com.ww.javase.callback.demo1;

/**
 * Created by weiwei on 2017/12/20.
 */
public class SyncAppSender implements IMessage {

    @Override
    public void sendMessage(String msg, Boolean isOk) {
        System.out.println("##### hi, I'm upper sender...");
        System.out.println(String.format("##### send %s, message: %s", isOk, msg));
    }

    public void appSendMessage(String msg, Android android) {
        android.sysSendMessage(msg, SyncAppSender.this);
        System.out.println("##### send finish...");
    }
}
