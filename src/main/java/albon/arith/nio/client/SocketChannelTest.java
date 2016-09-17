package albon.arith.nio.client;

import albon.arith.nio.util.ConvertUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by albon on 16/9/17.
 */
public class SocketChannelTest {

    public static ByteBuffer helloString() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.clear();
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();
        return byteBuffer;
    }

    public static void main(String[] args) throws IOException {
        new SocketChannelRunnable().run();
    }
}
