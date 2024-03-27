package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import com.shisx.protocol.jt808.util.ProtocolUtil;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Msg0107Test {

    @Test
    public void testBuildAndParse() {
        Msg0107 body = new Msg0107();
        body.setTerminalType((short) 23);
        body.setMfrId(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
        body.setTerminalModel(new byte[30]);
        body.setTerminalId(new byte[30]);
        body.setIccid("01234567890123456789");
        body.setHardwareVersion("H3.1021");
        body.setFirmwareVersion("F10.01.3");
        body.setGnssParam((byte) 0x4F);
        body.setSignalParam((byte) 0x93);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg0107 body2 = (Msg0107) jt808Message.getMessageBody();
        assertEquals(body.getTerminalType(), body2.getTerminalType());
        assertArrayEquals(body.getMfrId(), body2.getMfrId());
        assertArrayEquals(body.getTerminalModel(), body2.getTerminalModel());
        assertArrayEquals(body.getTerminalId(), body2.getTerminalId());
        assertEquals(body.getIccid(), body2.getIccid());
        assertEquals(body.getHardwareVersion(), body2.getHardwareVersion());
        assertEquals(body.getFirmwareVersion(), body2.getFirmwareVersion());
        assertEquals(body.getGnssParam(), body2.getGnssParam());
        assertEquals(body.getSignalParam(), body2.getSignalParam());
    }
}