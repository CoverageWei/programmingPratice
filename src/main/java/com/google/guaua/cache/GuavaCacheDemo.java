package com.google.guaua.cache;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiwei on 2016/12/19.
 */
public class GuavaCacheDemo {

    private static Cache<String, String> cacheFormCallable = null;

    public static <K,V> Cache<K , V> callableCached() throws Exception {
        Cache<K, V> cache = CacheBuilder
                .newBuilder()
                .maximumSize(3)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        return cache;
    }

    private static String getCallableCache(final String userName) {
        try {
            //Callable只有在缓存值不存在时，才会调用
            return cacheFormCallable.get(userName, new Callable<String>() {
//                @Override
                public String call() throws Exception {
                    System.out.println(userName+" from db");
                    return "hello "+userName+"!";
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        final String u1name = "peida";
        final String u2name = "jerry";
        final String u3name = "lisa";
        final String u4name = "weiwei";
        cacheFormCallable=callableCached();
        System.out.println("peida:"+ getCallableCache(u1name));
        System.out.println("jerry:"+ getCallableCache(u2name));
        System.out.println("lisa:"+ getCallableCache(u3name));
        Thread.currentThread().sleep(10000);
        System.out.println("peida:"+ getCallableCache(u1name));
        System.out.println("weiwei:"+ getCallableCache(u4name));

        Thread.currentThread().sleep(10000);
        System.out.println(cacheFormCallable.asMap().keySet());

        System.out.println(Strings.isNullOrEmpty(""));
    }


}
