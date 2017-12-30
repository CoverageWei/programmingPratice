package com.ww.javase.callback.demo1;

import org.apache.commons.lang.StringUtils;

/**
 * Created by weiwei on 2017/12/20.
 */
public class Android {
    public void sysSendMessage(String msg, IMessage sender) {
        if(StringUtils.isBlank(msg)) {
            System.out.println("***** send failed, message is null");
        }

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("***** hi, i'm just call upper sender to deal...");
        sender.sendMessage(msg, msg.length() <= 140 ? true : false);
    }
}
