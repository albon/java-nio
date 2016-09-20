package albon.arith.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author albon
 *         Date : 16-9-20
 *         Time: 上午11:22
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private final byte[] req;
    private int counter = 0;

    public TimeClientHandler() {
        System.out.println("init TimeClientHandler");
        req = "QUERY TIME ORDER".getBytes();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; ++i) {
            ByteBuf firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            ctx.writeAndFlush(firstMessage);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");

        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        System.out.println("now is: " + new String(req, "UTF-8") + ", counter: " + counter++);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        ctx.close();
    }
}
