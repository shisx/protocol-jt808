package com.shisx.protocol.jt808.util;

public class HexUtil {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, false);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase, false);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase, boolean addSpace) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER, addSpace);
    }

    private static char[] encodeHex(byte[] data, char[] toDigits, boolean addSpace) {
        if (data == null)
            return null;
        int len = data.length;
        char[] out = new char[(len << 1) + (addSpace ? len - 1 : 0)];
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
            if (addSpace && i != len - 1) {
                out[j++] = ' ';
            }
        }
        return out;
    }


    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, false);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase, false);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase, boolean addSpace) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER, addSpace);
    }

    private static String encodeHexStr(byte[] data, char[] toDigits, boolean addSpace) {
        return new String(encodeHex(data, toDigits, addSpace));
    }

    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }


    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        if (hexString.contains(" ")) {
            hexString = hexString.replace(" ", "");
        }
        hexString = hexString.trim();
        hexString = hexString.toUpperCase();
        int length = hexString.length() >> 1;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String extractData(byte[] data, int position) {
        return encodeHexStr(new byte[]{data[position]});
    }

}