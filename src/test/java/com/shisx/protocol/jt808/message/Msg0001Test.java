package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import junit.framework.TestCase;

public class Msg0001Test extends TestCase {

    public void testBuildAndParse() {
        Msg0001 body = new Msg0001();
        body.setAckId((short) 1);
        body.setAckSn((short) 2);
        body.setResult((byte) 3);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg0001 body2 = (Msg0001) jt808Message.getMessageBody();
        assertEquals(body.getAckId(), body2.getAckId());
        assertEquals(body.getAckSn(), body2.getAckSn());
        assertEquals(body.getResult(), body2.getResult());
    }

}