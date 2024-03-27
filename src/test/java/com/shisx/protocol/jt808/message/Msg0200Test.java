package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.model.AlarmFlag;
import com.shisx.protocol.jt808.model.PositionInfo;
import com.shisx.protocol.jt808.model.PositionStatus;
import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Msg0200Test {

    @Test
    public void testBuildAndParse() {
        PositionInfo pi = new PositionInfo();
        pi.setLongitude(123000000);
        pi.setLatitude(22000000);
        pi.setSpeed((short) 90);
        pi.setAltitude((short) 100);
        pi.setPositionStatus(PositionStatus.defaultStatus());
        pi.setAlarmFlag(new AlarmFlag());
        pi.setTime(System.currentTimeMillis());

        Msg0200 body = new Msg0200();
        body.setPositionInfo(pi);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg0200 body2 = (Msg0200) jt808Message.getMessageBody();
        PositionInfo pi2 = body2.getPositionInfo();
        assertEquals(pi.getLatitude(), pi2.getLatitude());
        assertEquals(pi.getLongitude(), pi2.getLongitude());
        assertEquals(pi.getSpeed(), pi2.getSpeed());
        assertEquals(pi.getAltitude(), pi2.getAltitude());
        assertTrue(pi.getTime() - pi2.getTime() <= 1000);
    }

}