package com.ww.javase.MuitiThread;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by weiwei on 2017/1/15.
 */
public class ThreadPoolExecutorTest {

    private static int[] d;

//    public static void main(String[] args) {
//        d = new int[10];
//        for(int i = 0; i < 10; i++) {
//            d[i] = i;
//        }
//        ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 4, Long.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue());
//        Future[] f = new Future[4];
//        int size = d.length / 4;
//        for (int i = 0; i < 3; i++) {
//            f[i] = tpe.submit(new GenerateSnapshotTask(i * size, (i + 1) * size - 1, d));
//        }
//        f[3] = tpe.submit(new GenerateSnapshotTask(3 * size, d.length - 1, d));
//
//        int n = 0;
//        try {
//            for (int i = 0; i < 4; i++) {
//                n += (Integer) f[i].get();
//            }
//        } catch (InterruptedException e) {
//
//        } catch (ExecutionException e) {
//
//        }
//
//        System.out.println("sum = " + n);
//    }

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        System.out.println("start all...");
        List<Integer> counts = Arrays.asList(1,2,3,4,5);

        for(int i = 0; i < 10 ; i++) {
            countOnePage2(i, counts);
        }
        System.out.println("finish all...");
        System.out.println(executorService.isShutdown());
        if(!executorService.isShutdown()) {
            executorService.shutdown();
        }
        System.out.println(executorService.isShutdown());
    }

    public static void countOnePage2(final Integer page, List<Integer> counts) {
//        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println(String.format("##### %s start... ", page));

        List<Future<Integer>> resultFutures = Lists.newArrayList();
        for(final Integer i : counts) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {

                public Integer call() throws Exception {
                    return returnCount(page, i);
                }
            });
            resultFutures.add(future);
        }

        int sum = 0;
        for(Future<Integer> future : resultFutures) {
            try {
                sum += future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("##### %s finish...[sum = %s] ", page, sum));
    }


    public static void countOnePage(final Integer page, List<Integer> counts) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println(String.format("##### %s start... ", page));

        List<Future<Integer>> resultFutures = Lists.newArrayList();
        for(final Integer i : counts) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {

                public Integer call() throws Exception {
                    return returnCount(page, i);
                }
            });
            resultFutures.add(future);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int sum = 0;
        for(Future<Integer> future : resultFutures) {
            try {
                sum += future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("##### %s finish...[sum = %s] ", page, sum));
    }

    public static int returnCount(int page, int i){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(page + "-" + i);
        return 1;
    }
    private static class GenerateSnapshotTask implements Callable<Integer> {
        private int first;
        private int last;
        private int[] d;
        public GenerateSnapshotTask(int first, int last, int[] d) {
            this.first = first;
            this.last = last;
            this.d = d;
        }

        @Override
        public Integer call() throws Exception {
            int subCount = 0;
            for (int i = first; i <= last; i++) {
                subCount += d[i];
            }
            return subCount;
        }
    }
}

