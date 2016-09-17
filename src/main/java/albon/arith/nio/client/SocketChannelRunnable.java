package albon.arith.nio.client;

import albon.arith.nio.util.ConvertUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static albon.arith.nio.client.SocketChannelTest.helloString;

/**
 * Created by albon on 16/9/17.
 */
public class SocketChannelRunnable implements Runnable {
    private String tag = "default";

    public SocketChannelRunnable(){}

    public SocketChannelRunnable(String tag) {
        this.tag = tag;
    }

    public void run() {
        long start = System.currentTimeMillis();
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9078));

            System.out.println(tag + " connect success ......");

            ByteBuffer byteBuffer = helloString();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }

            byteBuffer.clear();
            socketChannel.read(byteBuffer);

            System.out.println(tag + " receive: " + ConvertUtil.convertByteBufferToString(byteBuffer));

            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(tag + " time: " + (System.currentTimeMillis() - start));
        }
    }
}
