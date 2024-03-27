package com.shisx.protocol.jt808.util;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class ProtocolUtil {

    /**
     * BCD编码转ASCII字符串
     *
     * @param data
     * @return
     */
    public static String bcd2String(byte[] data) {
        byte[] dinByte = new byte[data.length * 2];
        byte index = 0;
        for (int i = 0; i < data.length; i++) {
            dinByte[index++] = (byte) (((data[i] >> 4) & 0x0f) + 0x30);
            dinByte[index++] = (byte) ((data[i] & 0x0f) + 0x30);
        }
        return new String(dinByte);
    }

    /**
     * ASCII字符串转BCD编码
     *
     * @param chars
     * @return
     */
    public static byte[] string2Bcd(String chars) {
        byte[] result = new byte[chars.length() / 2];
        char[] temp = chars.toCharArray();
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (((temp[i * 2] << 4) & 0xF0) | (temp[i * 2 + 1] & 0x0F));
        }
        return result;
    }

    /**
     * crc16 计算
     *
     * @param msg
     * @return
     */
    public static short CRC16(byte[] msg) {
        short crc = (short) 0xFFFF;
        int i, j;
        boolean c15, bit;

        for (i = 0; i < msg.length; i++) {
            for (j = 0; j < 8; j++) {
                c15 = ((crc >> 15 & 1) == 1);
                bit = ((msg[i] >> (7 - j) & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= 0x1021;
            }
        }
        return crc;
    }

    /**
     * 校验和 计算
     *
     * @param msg
     * @return
     */
    public static short checksum(byte[] msg) {
        return checksum(msg, 0, msg.length);
    }

    public static short checksum(byte[] msg, int startIndex, int length) {
        short sum = 0;
        for (int i = startIndex; i < length; i++) {
            sum += (msg[i] & 0xff);
        }
        return sum;
    }

    /**
     * 字节数组转ASCII字符串，并去掉其中的0x00字节
     *
     * @param bytes
     * @return
     */
    public static String getASCIIStr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        int index = bytes.length;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0x00) {
                index = i;
                break;
            }
        }
        return new String(bytes, 0, index).trim();
    }

    /**
     * 长整型转成无符号4字节数
     *
     * @param d
     * @return
     */
    public static byte[] longToUnsigned32(long d) {
        byte[] result = new byte[4];
        result[0] = (byte) ((d >> 24) & 0xff);
        result[1] = (byte) ((d >> 16) & 0xff);
        result[2] = (byte) ((d >> 8) & 0xff);
        result[3] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 长整型转成无符号2字节数
     *
     * @param d
     * @return
     */
    public static byte[] longToUnsigned16(long d) {
        byte[] result = new byte[2];
        result[0] = (byte) ((d >> 8) & 0xff);
        result[1] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 整型转成无符号4字节数
     *
     * @param d
     * @return
     */
    public static byte[] intToUnsigned32(int d) {
        byte[] result = new byte[4];
        result[0] = (byte) ((d >> 24) & 0xff);
        result[1] = (byte) ((d >> 16) & 0xff);
        result[2] = (byte) ((d >> 8) & 0xff);
        result[3] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 整型转成无符号2字节数
     *
     * @param d
     * @return
     */
    public static byte[] intToUnsigned16(int d) {
        byte[] result = new byte[2];
        result[0] = (byte) ((d >> 8) & 0xff);
        result[1] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 整型转成无符号1字节数
     *
     * @param d
     * @return
     */
    public static byte intToUnsigned8(int d) {
        byte result = 0;
        result = (byte) (d & 0xff);
        return result;
    }

    /**
     * 浮点型转成无符号4字节数
     *
     * @param d
     * @return
     */
    public static byte[] floatToUnsigned32(float d) {
        int intBits = Float.floatToIntBits(d);
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (intBits & 0xff);
        bytes[1] = (byte) ((intBits & 0xff00) >> 8);
        bytes[2] = (byte) ((intBits & 0xff0000) >> 16);
        bytes[3] = (byte) ((intBits & 0xff000000) >> 24);
        return bytes;
    }

    /**
     * 字节转换为浮点
     *
     * @param b     字节（至少4个字节）
     * @param index 开始位置
     * @return
     */
    public static float bytes2Float(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * 字节转long
     *
     * @param bb
     * @return
     */
    public static long bytes2Long(byte[] bb) {
        return ((((long) bb[0] & 0xff) << 56) | (((long) bb[1] & 0xff) << 48) | (((long) bb[2] & 0xff) << 40) | (((long) bb[3] & 0xff) << 32)
                | (((long) bb[4] & 0xff) << 24) | (((long) bb[5] & 0xff) << 16) | (((long) bb[6] & 0xff) << 8) | (((long) bb[7] & 0xff)));
    }

    /**
     * 字节转int
     *
     * @param bb
     * @return
     */
    public static int bytes2Int(byte[] bb) {
        return ((((int) bb[0] & 0xff) << 24) | (((int) bb[1] & 0xff) << 16) | (((int) bb[2] & 0xff) << 8) | (((int) bb[3] & 0xff)));
    }

    /**
     * 字节转short
     *
     * @param bb
     * @return
     */
    public static int bytes2Short(byte[] bb) {
        return ((((int) bb[0] & 0xff) << 8) | (((int) bb[1] & 0xff)));
    }

    /**
     * 获取字节形式的日期（年只后两位，如：17（2017））
     *
     * @return
     */
    public static byte[] getCurrDate() {
        return date2Bytes(LocalDateTime.now());
    }

    /**
     * 获取字节形式的UTC日期（年只后两位，如：17（2017））
     *
     * @return
     */
    public static byte[] getUtcTime() {
        return date2Bytes(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
    }

    /**
     * 一个日期转换成6字节
     *
     * @param date
     * @return
     */
    public static byte[] date2Bytes(Date date) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return date2Bytes(ldt);
    }

    public static byte[] date2Bytes(LocalDateTime ldt) {
        byte[] head_arr = new byte[]{(byte) (ldt.getYear() - 2000), (byte) ldt.getMonthValue(), (byte) ldt.getDayOfMonth(), (byte) ldt.getHour(),
                (byte) ldt.getMinute(), (byte) ldt.getSecond()};
        return head_arr;
    }

    /**
     * 字节数组转字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToString(byte[] bytes) {
        return new String(bytes);
    }

    /**
     * 字节数组转16进制字符串（以空格间隔）
     *
     * @param bytes
     * @return
     * @deprecated 请使用 {@link HexUtil#encodeHexStr(byte[])}
     */
    @Deprecated
    public static String byte2HexStr(byte... bytes) {
        return byte2HexStr(null, bytes);
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param delimiter
     * @param bytes
     * @return
     * @deprecated 请使用 {@link HexUtil#encodeHexStr(byte[])}
     */
    @Deprecated
    public static String byte2HexStr(String delimiter, byte... bytes) {
        if (delimiter == null || "".equals(delimiter)) {
            return HexUtil.encodeHexStr(bytes);
        } else {
            return HexUtil.encodeHexStr(bytes, false, true);
        }
    }

    /**
     * short数值转十六进制
     *
     * @param v
     * @return
     */
    public static String short2Hex(short v) {
        return StringUtils.leftPad(Integer.toHexString(v), 4, "0");
    }

    /**
     * 十六进制字符转字节数组
     *
     * @param delimiter
     * @param hexString
     * @return
     * @deprecated 请使用 {@link HexUtil#hexStringToBytes(String)}
     */
    @Deprecated
    public static byte[] hexStr2Byte(String delimiter, String hexString) {
        return HexUtil.hexStringToBytes(hexString);
    }

    /**
     * 十六进制字符串转换为int值
     *
     * @param hexStr
     * @return
     */
    public static int hexString2Int(String hexStr) {
        Long longStr = Long.parseLong(hexStr, 16);
        return longStr.intValue();
    }

    /**
     * 连接多个数组
     *
     * @param bss
     * @return
     */
    public static byte[] join(byte[]... bss) {
        int len = 0;
        for (byte[] bs : bss) {
            len += bs.length;
        }

        int i = 0;
        byte[] result = new byte[len];
        for (byte[] bs : bss) {
            for (byte b : bs) {
                result[i++] = b;
            }
        }
        return result;
    }

    /* ********************************  分割线，下面方法对传入数值进行判断    ********************************** */

    /**
     * 长整型转成无符号4字节数 并对参数进行校验
     *
     * @param d
     * @return
     */
    public static byte[] longToUnsigned32Verify(long d) {
        if (-9999 == d)
            return new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        byte[] result = new byte[4];
        result[0] = (byte) ((d >> 24) & 0xff);
        result[1] = (byte) ((d >> 16) & 0xff);
        result[2] = (byte) ((d >> 8) & 0xff);
        result[3] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 整型转成无符号4字节数 并对参数进行校验
     *
     * @param d
     * @return
     */
    public static byte[] intToUnsigned32Verify(int d) {
        if (-9999 == d)
            return new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        byte[] result = new byte[4];
        result[0] = (byte) ((d >> 24) & 0xff);
        result[1] = (byte) ((d >> 16) & 0xff);
        result[2] = (byte) ((d >> 8) & 0xff);
        result[3] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 整型转成无符号2字节数 并对参数进行校验
     *
     * @param d
     * @return
     */
    public static byte[] intToUnsigned16Verify(int d) {
        if (-9999 == d)
            return new byte[]{(byte) 0xff, (byte) 0xff};
        byte[] result = new byte[2];
        result[0] = (byte) ((d >> 8) & 0xff);
        result[1] = (byte) (d & 0xff);
        return result;
    }

    /**
     * 整型转成无符号1字节数 并对参数进行校验
     *
     * @param d
     * @return
     */
    public static byte intToUnsigned8Verify(int d) {
        if (-9999 == d)
            return (byte) 0xff;
        byte result = 0;
        result = (byte) (d & 0xff);
        return result;
    }

    /**
     * 转换经纬度
     *
     * @param value
     * @return
     */
    public static double getLatitudeOrLongitude(long value) {
        // System.out.println(value >> 31);
        if ((value >> 31) == 1) {
            value = value & 0x7FFFFFFF;
        }
        return (value * 0.000001);
    }

    /* **************************		工具方法		**************************** */


    /**
     * 总共两个参数，第一个参数1字节，第二个参数1字节 tomsg(2, 1, 1, para1, para2)：转成字节数组
     *
     * @param msg
     * @return
     */
    public static byte[] tomsg(int... msg) {
        byte length = (byte) msg[0];
        byte msglength = 0;
        for (int i = 1; i < length + 1; i++) {
            msglength += msg[i];
        }
        byte[] tempmsg = new byte[msglength];
        byte index = 0;
        for (int i = length + 1; i < msg.length; i++) {
            switch (msg[i - length]) {
                case 1: {
                    tempmsg[index++] = (byte) (msg[i] & 0xff);
                }
                break;
                case 2: {
                    tempmsg[index++] = (byte) ((msg[i] >> 8) & 0xff);
                    tempmsg[index++] = (byte) (msg[i] & 0xff);
                }
                break;
                case 4: {
                    tempmsg[index++] = (byte) ((msg[i] >> 24) & 0xff);
                    tempmsg[index++] = (byte) ((msg[i] >> 16) & 0xff);
                    tempmsg[index++] = (byte) ((msg[i] >> 8) & 0xff);
                    tempmsg[index++] = (byte) (msg[i] & 0xff);
                }
                break;
                default:
                    break;
            }
        }
        return tempmsg;
    }

    /**
     * 读取十六进制字符串
     *
     * @param buffer
     * @param byteSize 字节数
     * @return
     */
    public static String readHexString(ByteBuf buffer, int byteSize) {
        byte[] bytes = new byte[byteSize];
        buffer.readBytes(bytes);
        return ProtocolUtil.byte2HexStr(bytes);
    }

    /**
     * 读取BCD编码，并转为ASCII字符串
     *
     * @param byteBuf
     * @param size
     * @return
     */
    public static String readBcd(ByteBuf byteBuf, int size) {
        byte[] bytes = new byte[size];
        byteBuf.readBytes(bytes);
        return ProtocolUtil.bcd2String(bytes);
    }

    /**
     * 异或运算
     *
     * @param bytes 所有字节的异或结果
     * @return
     */
    public static byte xor(byte[] bytes) {
        byte result = 0x00;
        for (byte b : bytes) {
            result ^= b;
        }
        return result;
    }

}
