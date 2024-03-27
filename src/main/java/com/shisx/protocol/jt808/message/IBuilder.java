package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;

public interface IBuilder {

    byte[] build();

    default ByteBuf allowBuffer(int initialCapacity) {
        return io.netty.buffer.Unpooled.buffer(initialCapacity, 10240);
    }

    default ByteBuf allowBuffer(int initialCapacity, int maxCapacity) {
        return io.netty.buffer.Unpooled.buffer(initialCapacity, maxCapacity);
    }

    default ByteBuf fixBuffer(int capacity) {
        return io.netty.buffer.Unpooled.buffer(capacity, capacity);
    }

    default byte[] readReadableBytes(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    default void writeFixLengthBytes(ByteBuf byteBuf, byte[] bytes, int length, int mode) {
        int zeros = bytes == null ? length : length - bytes.length;

        // 前补0x00
        if (mode == 0) {
            if (zeros > 0) {
                byteBuf.writeZero(zeros);
            }
            if (bytes != null && bytes.length > 0) {
                byteBuf.writeBytes(bytes);
            }
        }

        // 后补0x00
        if (mode == 1) {
            if (bytes != null && bytes.length > 0) {
                byteBuf.writeBytes(bytes);
            }
            if (zeros > 0) {
                byteBuf.writeZero(zeros);
            }
        }
    }

}
