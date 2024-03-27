package com.shisx.protocol.jt808.message;

import java.util.HashMap;
import java.util.Map;

public abstract class MessageBody implements IParser, IBuilder {

    public static MessageBody buildByMsgId(short msgId) throws IllegalAccessException, InstantiationException {
        Class<? extends MessageBody> clazz = BODY_MAP.get(msgId);
        return clazz == null ? null : clazz.newInstance();
    }

    public static short getMsgIdByClass(Class<? extends MessageBody> clazz) {
        return BODY_MAP2.get(clazz);
    }

    private static final Map<Short, Class<? extends MessageBody>> BODY_MAP = new HashMap<>();
    private static final Map<Class<? extends MessageBody>, Short> BODY_MAP2 = new HashMap<>();

    static {
        register((short) 0x0001, Msg0001.class);
        register((short) 0x0002, Msg0002.class);
        register((short) 0x0003, Msg0003.class);
        register((short) 0x0004, Msg0004.class);
        register((short) 0x0100, Msg0100.class);
        register((short) 0x0102, Msg0102.class);
        register((short) 0x0104, Msg0104.class);
        register((short) 0x0107, Msg0107.class);
        register((short) 0x0108, Msg0108.class);
        register((short) 0x0200, Msg0200.class);
        register((short) 0x0201, Msg0201.class);
        register((short) 0x8001, Msg8001.class);
        register((short) 0x8004, Msg8004.class);
        register((short) 0x8100, Msg8100.class);
        register((short) 0x8103, Msg8103.class);
        register((short) 0x8104, Msg8104.class);
        register((short) 0x8105, Msg8105.class);
        register((short) 0x8106, Msg8106.class);
        register((short) 0x8107, Msg8107.class);
        register((short) 0x8108, Msg8108.class);
        register((short) 0x8201, Msg8201.class);
        register((short) 0x8202, Msg8202.class);
        register((short) 0x8203, Msg8203.class);
        register((short) 0x8204, Msg8204.class);
        register((short) 0x8300, Msg8300.class);
    }

    private static void register(short msgId, Class<? extends MessageBody> clazz) {
        BODY_MAP.put(msgId, clazz);
        BODY_MAP2.put(clazz, msgId);
    }
}
