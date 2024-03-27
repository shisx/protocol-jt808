package com.shisx.protocol.jt808.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * J808协议工具类
 *
 * @author Brook
 */
public class JT808Utils {

    private static final byte BYTE_7E = (byte) 0x7e;
    private static final byte BYTE_7D = (byte) 0x7d;
    private static final byte BYTE_01 = (byte) 0x01;
    private static final byte BYTE_02 = (byte) 0x02;
    private static final byte[] BYTES_OF_7E_DECODE = new byte[]{0x7d, 0x02};
    private static final byte[] BYTES_OF_7D_DECODE = new byte[]{0x7d, 0x01};

    public static final Charset GBK = Charset.forName("GBK");

    private static short msgSn = 0;

    /**
     * 标识符（0x7e,0x7d）转义（除了头、尾标识符，其它内容都需要转义）
     * <p>
     * 0x7e -> 0x7d 0x02
     * 0x7d -> 0x7d 0x01
     *
     * @param from 消息（完整的消息，带头尾标识符）
     * @return
     */
    public static byte[] flagTransference(byte[] from) {
        boolean changed = false;
        ByteBuf buffer = Unpooled.buffer(from.length, from.length + 20);

        // 头
        buffer.writeByte(from[0]);

        // 中间部分（消息头 + 消息体 + 检验码），些部分需要进行转义
        for (int i = 1; i < from.length - 1; i++) {
            if (from[i] == BYTE_7E) {
                changed = true;
                buffer.writeBytes(BYTES_OF_7E_DECODE);
            } else if (from[i] == BYTE_7D) {
                changed = true;
                buffer.writeBytes(BYTES_OF_7D_DECODE);
            } else {
                buffer.writeByte(from[i]);
            }
        }

        // 尾
        buffer.writeByte(from[from.length - 1]);

        // 无变化，返回原内容
        if (!changed) {
            return from;
        }

        byte[] to = new byte[buffer.readableBytes()];
        buffer.readBytes(to);
        return to;
    }

    /**
     * 标识符还原
     * <p>
     * 0x7d 0x02 -> 0x7e
     * 0x7d 0x01 -> 0x7d
     *
     * @param from 消息（完整的消息，带头尾标识符）
     * @return
     */
    public static byte[] flagRestore(byte[] from) {
        boolean changed = false;
        ByteBuf buffer = Unpooled.buffer(from.length, from.length);

        // 头
        buffer.writeByte(from[0]);

        // 中间部分（消息头 + 消息体 + 检验码），些部分需要进行转义还原
        for (int i = 1; i < from.length - 1; i++) {
            if (from[i] == BYTE_7D) {
                if (from[i + 1] == BYTE_02) {
                    buffer.writeByte(BYTE_7E);
                    i++;
                    changed = true;
                } else if (from[i + 1] == BYTE_01) {
                    buffer.writeByte(BYTE_7D);
                    i++;
                    changed = true;
                } else {
                    buffer.writeByte(from[i]);
                }
            } else {
                buffer.writeByte(from[i]);
            }
        }

        // 尾
        buffer.writeByte(from[from.length - 1]);

        if (!changed) {
            return from;
        }

        byte[] to = new byte[buffer.readableBytes()];
        buffer.readBytes(to);
        return to;
    }

    /**
     * 验证校验码
     *
     * @param data
     * @return
     */
    public static boolean verifyCRC(byte[] data) {
        byte expectCRC = data[data.length - 2];
        int from = 1;
        int to = data.length - 3;
        byte result = 0x00;
        for (int i = from; i <= to; i++) {
            result ^= data[i];
        }
        return result == expectCRC;
    }

    /**
     * 生成消息流水号
     *
     * @return
     */
    public static short genMsgSn() {
        return (short) (msgSn++ & Short.MAX_VALUE);
    }

    /**
     * 读取ACCII字符
     *
     * @param byteBuf
     * @param size
     * @return
     */
    public static String readAscii(ByteBuf byteBuf, int size) {
        byte[] bytes = readBytesExclude00(byteBuf, size);
        return new String(bytes);
    }

    /**
     * 读取GBK编码字符串，并去掉0x00
     *
     * @param byteBuf
     * @param size
     * @return
     */
    public static String readGbk(ByteBuf byteBuf, int size) {
        byte[] bytes = readBytesExclude00(byteBuf, size);
        return new String(bytes, GBK);
    }

    /**
     * @param byteBuf
     * @param text
     */
    public static void writeGbk(ByteBuf byteBuf, String text) {
        byte[] bytes = text.getBytes(GBK);
        byteBuf.writeBytes(bytes);
    }

    public static byte[] string2bytes(String text) {
        return text.getBytes(GBK);
    }

    /**
     * 字符串转BGK编码字节
     *
     * @param string
     * @return
     */
    public static byte[] getBytesGBK(String string) {
        return string.getBytes(GBK);
    }

    /**
     * 读取字节组并排除0x00
     *
     * @param byteBuf
     * @param size
     * @return
     */
    private static byte[] readBytesExclude00(ByteBuf byteBuf, int size) {
        int readerIndex = byteBuf.readerIndex();
        int i = 0;
        for (; i < size; i++) {
            if (byteBuf.readByte() == (byte) 0x00) {
                break;
            }
        }

        // 读取可用字节
        byteBuf.readerIndex(readerIndex);
        byte[] bytes = new byte[i];
        byteBuf.readBytes(bytes);

        // 跳过剩下的字节
        byteBuf.skipBytes(size - i);

        return bytes;
    }

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static long readTime(ByteBuf byteBuf) {
        String beijingTime = ProtocolUtil.readBcd(byteBuf, 6);
        return LocalDateTime.parse(beijingTime, TIME_FORMAT).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static void writeTime(ByteBuf byteBuf, long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        byteBuf.writeBytes(ProtocolUtil.string2Bcd(localDateTime.format(TIME_FORMAT)));
    }

}
