package albon.arith.nio.server;

import albon.arith.nio.util.ConvertUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by albon on 16/9/17.
 */
public class ServerSocketChannelTest {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9078));
        serverSocketChannel.configureBlocking(false);

        while (true) {
            System.out.println("accept ...");
            SocketChannel socketChannel = serverSocketChannel.accept();

            ByteBuffer buffer = ByteBuffer.allocate(100);
            buffer.clear();
            socketChannel.read(buffer);

            System.out.println(ConvertUtil.convertByteBufferToString(buffer));
            buffer.rewind();
            socketChannel.write(buffer);
            socketChannel.close();
        }
    }
}
