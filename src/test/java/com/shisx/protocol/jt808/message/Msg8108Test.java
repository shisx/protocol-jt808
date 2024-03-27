package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Msg8108Test {

    @Test
    public void testBuildAndParse() {
        Msg8108 body = new Msg8108();
        body.setUpgradeType((byte) 1);
        body.setMfrId(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
        body.setFirmwareVersion("F34.1948.12A");
        body.setUpgradeData(new byte[1024]);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8108 body2 = (Msg8108) jt808Message.getMessageBody();
        assertEquals(body.getUpgradeType(), body2.getUpgradeType());
        assertArrayEquals(body.getMfrId(), body2.getMfrId());
        assertEquals(body.getFirmwareVersion(), body2.getFirmwareVersion());
        assertArrayEquals(body.getUpgradeData(), body2.getUpgradeData());
    }
}