package albon.arith.nio.util;

import java.nio.ByteBuffer;

/**
 * Created by albon on 16/9/17.
 */
public class ConvertUtil {

    public static String convertByteBufferToString(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        byte[] dst = new byte[100];
        int remaining = byteBuffer.remaining();
        byteBuffer.get(dst, byteBuffer.position(), remaining);
        dst[remaining] = '\0';
        return new String(dst, 0, remaining);
    }
}
