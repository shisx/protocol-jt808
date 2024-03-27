package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg8203Test {

    @Test
    public void testBuildAndParse() {
        Msg8203 body = new Msg8203();
        body.setSn((short) 11);
        body.setType(2);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8203 body2 = (Msg8203) jt808Message.getMessageBody();
        assertEquals(body.getSn(), body2.getSn());
        assertEquals(body.getType(), body2.getType());
    }

}