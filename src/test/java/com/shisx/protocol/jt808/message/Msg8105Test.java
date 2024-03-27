package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg8105Test {

    @Test
    public void testBuildAndParse() {
        Msg8105 body = new Msg8105();
        body.setCmdId((byte) 2);
        body.setCmdParam("我们一起 Jumping!");

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8105 body2 = (Msg8105) jt808Message.getMessageBody();
        assertEquals(body.getCmdId(), body2.getCmdId());
        assertEquals(body.getCmdParam(), body2.getCmdParam());
    }
}