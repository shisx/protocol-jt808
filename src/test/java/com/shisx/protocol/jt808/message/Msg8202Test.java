package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg8202Test {

    @Test
    public void testBuildAndParse() {
        Msg8202 body = new Msg8202();
        body.setInterval((short) 60);
        body.setDuration(300);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8202 body2 = (Msg8202) jt808Message.getMessageBody();
        assertEquals(body.getInterval(), body2.getInterval());
        assertEquals(body.getDuration(), body2.getDuration());
    }

}