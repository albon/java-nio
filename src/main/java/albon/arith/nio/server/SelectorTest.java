package albon.arith.nio.server;

import albon.arith.nio.util.ConvertUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by albon on 16/9/17.
 */
public class SelectorTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1000, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1), new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("reject ...");
        }
    });

    public static void main(String[] args) throws IOException {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9078));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach("accept_channel");

        long start = System.currentTimeMillis();

        while (true) {
            int readyNum = selector.select();
            System.out.println("\nready num: " + readyNum + ", time: " + (System.currentTimeMillis() - start));

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                String tag = (String) next.attachment();

                if (next.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    String socketName = "socket_" + atomicInteger.incrementAndGet();
                    socketChannel.register(selector, SelectionKey.OP_READ, socketName);

                    System.out.println(tag + " new SocketChannel: " + socketName);
                } else if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    executor.submit(new SocketServerRunnable(socketChannel, tag));
                    next.cancel();
                }

                iterator.remove();
            }
        }


    }
}
