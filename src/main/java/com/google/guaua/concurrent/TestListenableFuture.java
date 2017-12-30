package com.google.guaua.concurrent;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by weiwei on 2017/5/2.
 */
public class TestListenableFuture {

    static ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(5));

    public static void main(String[] args) {
//        simpleTest();

        testAllAsList();

//        testFutureCancel();
    }

    public static void simpleTest() {

        ListenableFuture<Integer> listenableFuture = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println("call execute..");
                TimeUnit.SECONDS.sleep(1);
                return 7;
            }
        });

        System.out.println("****** no wait result return print");
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }



    public static void testAllAsList() {
        ListenableFuture<Integer> listenableFuture1 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("call execute 1...");
                return 1;
            }
        });

        ListenableFuture<Integer> listenableFuture2 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("call execute 2...");
                return 2;
            }
        });

        ListenableFuture<Integer> listenableFuture3 = listeningExecutorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("call execute 3...");
//                throw new RuntimeException();
                return 3;
            }
        });

        System.out.println("****** no wait result return print");

        List<ListenableFuture<Integer>> allFutures = Lists.newArrayList(listenableFuture1, listenableFuture2, listenableFuture3);
        ListenableFuture<List<Integer>> resultFuture = Futures.allAsList(allFutures);

        Futures.addCallback(resultFuture, new FutureCallback<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> result) {
                System.out.println(result);
                int sum = 0;
                for(Integer value : result) {
                    sum += value;
                }
                System.out.println("result future:  " + sum);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public static void testFutureCancel(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int i = 0;
        int sum = 0;
        List<Future<Integer>> futures = Lists.newArrayList();
        while (i < 10) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("blocking..." + Thread.currentThread().getName());
                    Thread.currentThread().sleep(5000);
                    return 1;
                }
            });
            futures.add(future);
            i++;
        }

        for(Future<Integer> future: futures) {
            try {
                sum += future.get(1, TimeUnit.SECONDS);
                System.out.println("return...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                System.out.println("timeout...");
                future.cancel(true);
            }
        }
        System.out.println("sum= " + sum);

        int j = 0;
        while (j < 5) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
//                    Thread.currentThread().sleep(5000);
                    System.out.println("no blocking ..." + Thread.currentThread().getName());
                    return 1;
                }
            });
//            futures.add(future);
            j++;
        }
    }
}
