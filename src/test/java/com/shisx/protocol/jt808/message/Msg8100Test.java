package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg8100Test {

    @Test
    public void testBuildAndParse() {
        Msg8100 body = new Msg8100();
        body.setAckSn((short) 10);
        body.setResult((byte) 0);
        body.setAuthCode("success");

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8100 body2 = (Msg8100) jt808Message.getMessageBody();
        assertEquals(body.getAckSn(), body2.getAckSn());
        assertEquals(body.getResult(), body2.getResult());
        assertEquals(body.getAuthCode(), body2.getAuthCode());
    }
}