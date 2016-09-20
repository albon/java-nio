package albon.arith.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * @author albon
 *         Date : 16-9-20
 *         Time: 上午11:22
 */
public class TimeClient {

    public void connect(int port, String host) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(28));
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            channelFuture.channel().closeFuture().sync();

            System.out.println("end listening ...");
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 11000;

        new TimeClient().connect(port, "127.0.0.1");
    }
}