package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg0102Test {

    @Test
    public void testBuildAndParse() {
        Msg0102 body = new Msg0102();
        body.setAuthCode("HelloAuthCode");
        body.setImei("12390489034");
        body.setVersion("v102.394.198");

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg0102 body2 = (Msg0102) jt808Message.getMessageBody();
        assertEquals(body.getAuthCode(), body2.getAuthCode());
        assertEquals(body.getImei(), body2.getImei());
        assertEquals(body.getVersion(), body2.getVersion());
    }
}