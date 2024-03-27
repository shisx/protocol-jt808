package com.shisx.protocol.jt808.util;

/**
 * 字节处理工具
 *
 * @author Brook
 */
public class ByteUtils {

    /**
     * 右移step位，并判断第0位是否为1
     *
     * @param val
     * @param step
     * @return
     */
    public static final boolean rightMoveReturnBool(int val, int step) {
        return ((val >> step) & 0x01) == 0x01;
    }

    /**
     * 右移step位，并返回“& 0x01”字节
     *
     * @param val
     * @param step
     * @return
     */
    public static final byte rightMoveReturnByte(int val, int step) {
        return (byte) ((val >> step) & 0x01);
    }

    /**
     * byte取高四位
     *
     * @param data
     * @return
     */
    public static final int getHeight4(byte data) {
        int height;
        height = ((data & 0xf0) >> 4);
        return height;
    }

    /**
     * byte取低四位
     *
     * @param data
     * @return
     */
    public static final int getLow4(byte data) {
        return (data & 0x0f);
    }

    /**
     * 右移step位，并返回“& ampersand”字节
     *
     * @param val
     * @param step
     * @param ampersand
     * @return
     */
    public static final byte rightMoveReturnByte(int val, int step, byte ampersand) {
        return (byte) ((val >> step) & ampersand);
    }

    /**
     * 将BYTE值左移step位
     *
     * @param b
     * @param step
     * @return
     */
    public static final int leftMoveByByte(byte b, int step) {
        return b << step;
    }

    /**
     * 根据Boolean值左移，true时0x01左移step位，false时返回0x00
     *
     * @param bool
     * @param step
     * @return
     */
    public static final int leftMoveByBool(boolean bool, int step) {
        return bool ? (0x01 << step) : (0x00);
    }

}
