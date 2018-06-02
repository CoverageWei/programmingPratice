package com.ww.javase.MuitiThread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by weiwei on 2018/3/23.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
//        testCyclicBarrier();
        try {
            count();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testCyclicBarrier(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("人全到了，开会...");
            }
        });
        for(int i = 0; i < 5; i++) {
            new CyclicBarrierThread(cyclicBarrier).start();
        }
    }

    /**
     * 实践中，感觉可以用 future 替代
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static int count() throws ExecutionException, InterruptedException {
        Integer sum = 0;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Integer>> allFutures = Lists.newArrayList();

        for(int i = 0; i < 20; i++) {
            Future<Integer> subFuture = executorService.submit(new countTask(cyclicBarrier, i));
            allFutures.add(subFuture);
        }

        for(Future future : allFutures) {
            sum += (Integer) future.get();
        }
        System.out.println(sum);
        executorService.shutdown();
        return sum;
    }


}


class countTask implements Callable {

    private CyclicBarrier cyclicBarrier;
    private int index;

    public countTask(CyclicBarrier cyclicBarrier, int inedx) {
        this.cyclicBarrier = cyclicBarrier;
        this.index = inedx;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        System.out.println("name= " + Thread.currentThread().getName() + ", id= " + Thread.currentThread().getId() + ", index = " + index);
        cyclicBarrier.await();
        return 10;
    }
}

class CyclicBarrierThread extends Thread {

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println("name= " + Thread.currentThread().getName() + ", id= " + Thread.currentThread().getId() + " 到了");

        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
