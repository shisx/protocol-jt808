package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg0108Test {

    @Test
    public void testBuildAndParse() {
        Msg0108 body = new Msg0108();
        body.setType((byte) 2);
        body.setResult((byte) 1);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg0108 body2 = (Msg0108) jt808Message.getMessageBody();
        assertEquals(body.getType(), body2.getType());
        assertEquals(body.getResult(), body2.getResult());
    }
}