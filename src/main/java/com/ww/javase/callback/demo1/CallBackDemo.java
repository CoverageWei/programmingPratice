package com.ww.javase.callback.demo1;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by weiwei on 2017/12/20.
 */
public class CallBackDemo {

//    public static void main(String[] args) {
//        Android android = new Android();
//
////        syncSend("hello, word!", android);
//
////        asyncSend("hello, word!", android);
//
//        appSendMessageByAnonymous("hello, word!", android);
//    }

    public static void syncSend(String msg, Android android){
        SyncAppSender appSender = new SyncAppSender();

        appSender.appSendMessage(msg, android);
    }

    public static void asyncSend(String msg, Android android){
        ASyncAppSender appSender = new ASyncAppSender();

        appSender.appSendMessage(msg, android);
    }

    public static void appSendMessageByAnonymous(String msg, Android android) {
        android.sysSendMessage(msg, new IMessage() {
            @Override
            public void sendMessage(String msg, Boolean isOk) {
                System.out.println("##### hi, I'm anonymous upper sender...");
                System.out.println(String.format("##### send %s, message: %s", isOk, msg));
            }
        });
    }

    private static void doFilterProviders(List<Long> sourceProviderIds) {
        List<Long> excludeproviderIds = Arrays.asList(1018990757L, 1021161559L, 11656787L, 6541803L, 1025413937L, 2836390L,
                1025199977L, 1021706599L, 7921320L, 1016343134L, 1014986943L, 1027576692L, 1028573773L, 9076979L,
                1022910025L, 1015312752L, 400000000187046L, 1018657074L, 1028618355L, 400000000344014L, 1019398910L,
                1021216589L, 1021538656L, 400000000333037L, 400000000270002L, 1028414392L, 1017773905L, 400000000270004L,
                400000000270005L, 1028573773L, 1098689L, 400000000331010L, 1023964853L, 1026875058L, 1027917096L, 1021242196L,
                1026590798L, 400000000274006L, 1023439908L, 1014351408L, 9088860L, 1219156L, 400000000184037L, 1026033338L);
        if(CollectionUtils.isEmpty(sourceProviderIds)) {
            return;
        }

        Iterator<Long> iterator = sourceProviderIds.iterator();
        while (iterator.hasNext()) {
            Long providerId = iterator.next();
            if(excludeproviderIds.contains(providerId)) {
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Long> excludeproviderIds = Lists.newArrayList(1018990757L, 1021161559L, 116567872L, 1L, 2L, 3L);
        doFilterProviders(excludeproviderIds);
        System.out.println(excludeproviderIds);

    }

}
