package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Msg0100Test {

    @Test
    public void testBuildAndParse() {
        Msg0100 body = new Msg0100();
        body.setProvinceId((short) 420);
        body.setCityId((short) 624);
        body.setMfrId(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x7E});
        body.setTerminalModel(new byte[30]);
        body.setTerminalId(new byte[30]);
        body.setPlateNumber("苏A12345");
        body.setPlateColor((byte) 1);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg0100 body2 = (Msg0100) jt808Message.getMessageBody();
        assertEquals(body.getProvinceId(), body2.getProvinceId());
        assertEquals(body.getCityId(), body2.getCityId());
        assertArrayEquals(body.getMfrId(), body2.getMfrId());
        assertArrayEquals(body.getTerminalModel(), body2.getTerminalModel());
        assertArrayEquals(body.getTerminalId(), body2.getTerminalId());
        assertEquals(body.getPlateNumber(), body2.getPlateNumber());
        assertEquals(body.getPlateColor(), body2.getPlateColor());

    }
}