package com.shisx.protocol.jt808.model;

/**
 * 字段类型
 *
 * @author Brook
 */
public enum FieldType {

    BYTE(1), INT16(2), INT32(4), INT64(8), STRING(-1), BYTES(-1);

    private final int length;

    private FieldType(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
