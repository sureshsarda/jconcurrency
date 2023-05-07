package jconcurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Barrier {
    
    public static void main(String[] args) {
    
        /*
         * At any point a certain number of threads should be able to perform actions in critical section
         */

        Semaphore s = new Semaphore(5);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            // Runnable runnable = new WorkThreadBarrier(s);
            
            // executorService.submit(runnable);
            String name = String.format("Thread: %d", i);
            executorService.submit(() -> {
                try {
                    s.acquire();
                    // critical section
        
                    System.out.println("Begin: Critical Section: " + name);
                    Thread.sleep(new Random().nextInt(2, 5) * 1000);
                    System.out.println("End: Critical Section: " + name);
        
                    s.release();
        
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}


class WorkThreadBarrier implements Runnable {

    private Semaphore semaphore;

    public WorkThreadBarrier(Semaphore semaphore) {
        this.semaphore = semaphore;

    }

    @Override
    public void run() {
        // System.out.println("Begin: Non Critical Section" + Thread.currentThread().getName());
        
        try {
            semaphore.acquire();
            // critical section

            System.out.println("Begin: Critical Section" + Thread.currentThread().getName());
            Thread.sleep(new Random().nextInt(2, 5) * 1000);
            System.out.println("End: Critical Section" + Thread.currentThread().getName());

            semaphore.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // System.out.println("End: Non Critical Section" + Thread.currentThread().getName());


    }




}