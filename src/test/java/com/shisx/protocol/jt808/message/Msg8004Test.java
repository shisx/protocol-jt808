package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class Msg8004Test {

    @Test
    public void testBuildAndParse() {
        Msg8004 body = new Msg8004();
        body.setTime(LocalDateTime.of(2022, 10, 11, 8, 49, 32));

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8004 body2 = (Msg8004) jt808Message.getMessageBody();
        assertEquals(body.getTime(), body2.getTime());
    }
}