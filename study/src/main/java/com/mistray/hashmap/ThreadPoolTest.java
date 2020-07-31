package com.mistray.hashmap;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                2,
                1000,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(89));


        for (int i = 0; i < 20; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    System.out.println(Thread.currentThread().getName());

                    System.out.println(Thread.currentThread().isInterrupted());

//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        Thread.currentThread().interrupt();
//                    }

                }
            });

        }

        executor.shutdown();

        System.out.println(executor.isShutdown());
        System.out.println(executor.isTerminated());

    }
}
