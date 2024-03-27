package com.shisx.protocol.jt808.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码
 */
public class ByteToMessageDecoderImpl extends ByteToMessageDecoder {
    private static final byte HEAD = (byte) 0x7E;
    private static final int LENGTH_MIN = 18;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        if (buffer.readableBytes() < LENGTH_MIN) {
            return;
        }

        int readIndexMark = buffer.readerIndex();
        boolean beging = false;
        while (buffer.isReadable()) {
            if (buffer.readByte() == HEAD) {
                if (beging) {
                    byte[] bytes = new byte[buffer.readerIndex() - readIndexMark];
                    buffer.readerIndex(readIndexMark);
                    buffer.readBytes(bytes);
                    out.add(bytes);
                    beging = false;
                    readIndexMark = buffer.readerIndex();
                } else {
                    readIndexMark = buffer.readerIndex() - 1;
                    beging = true;
                }
            }
        }

        buffer.readerIndex(readIndexMark);

        if (buffer.readableBytes() > 512) {
            // 如果超过N个字节仍无法读取，说明数据可能存在问题，需要过滤掉
            buffer.clear();
        }
    }
}