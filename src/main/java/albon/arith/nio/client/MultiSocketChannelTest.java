package albon.arith.nio.client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by albon on 16/9/17.
 */
public class MultiSocketChannelTest {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1000, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1), new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("reject ... " + atomicInteger.incrementAndGet());
        }
    });

    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);

        long start = time();

        for (int i = 0; i < 10000; ++i) {
            executor.submit(new SocketChannelRunnable("client_" + counter.incrementAndGet()));
        }

        System.out.println("time: " + (time() - start));

    }

    private static long time() {
        return System.currentTimeMillis();
    }

}
