package ai.demo.demoai.threads;

import java.util.concurrent.Executors;

public class ThreadsDemo {

    public static void platformThreadsDemoWithExecutorService(int threadCount, int threadPoolCount) {
        System.out.println("Starting Platform Threads Demo with Executor Service");
        try (var executor = Executors.newFixedThreadPool(threadPoolCount)) {
            for (int i = 0; i < threadCount; i++) {
                executor.submit(ThreadsDemo::doSomething);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void virtualThreadsDemoWithExecutorService(int threadsCount) {
        System.out.println("Starting Virtual Threads Demo with Executor Service");
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < threadsCount; i++) {
                executor.submit(ThreadsDemo::doSomething);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doSomething() {
        System.out.println("Doing something in thread: " + Thread.currentThread());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopped doing something in thread " + Thread.currentThread());
    }
}
