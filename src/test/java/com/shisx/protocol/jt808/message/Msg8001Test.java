package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg8001Test {

    @Test
    public void testBuildAndParse() {
        Msg8001 body = new Msg8001();
        body.setAckSn((short) 10);
        body.setAckId((short) 1);
        body.setResult((byte) 2);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8001 body2 = (Msg8001) jt808Message.getMessageBody();
        assertEquals(body.getAckSn(), body2.getAckSn());
        assertEquals(body.getAckId(), body2.getAckId());
        assertEquals(body.getResult(), body2.getResult());
    }
}