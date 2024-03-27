package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.model.TerminalParams;
import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg0104Test {

    @Test
    public void testBuildAndParse() {
        TerminalParams terminalParams = new TerminalParams();
        terminalParams.getParams().put(TerminalParams.PARAM.F_001A, "www.abc.com");
        terminalParams.getParams().put(TerminalParams.PARAM.F_0020, 20);
        terminalParams.getParams().put(TerminalParams.PARAM.F_0031, (short) 30);
        terminalParams.getParams().put(TerminalParams.PARAM.F_0084, (byte) 5);

        Msg0104 body = new Msg0104();
        body.setAckSn((short) 1);
        body.setTerminalParams(terminalParams);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        TerminalParams terminalParams2 = ((Msg0104) jt808Message.getMessageBody()).getTerminalParams();
        assertEquals(terminalParams.getParams().get(TerminalParams.PARAM.F_001A), terminalParams2.getParams().get(TerminalParams.PARAM.F_001A));
        assertEquals(terminalParams.getParams().get(TerminalParams.PARAM.F_0020), terminalParams2.getParams().get(TerminalParams.PARAM.F_0020));
        assertEquals(terminalParams.getParams().get(TerminalParams.PARAM.F_0031), terminalParams2.getParams().get(TerminalParams.PARAM.F_0031));
        assertEquals(terminalParams.getParams().get(TerminalParams.PARAM.F_0084), terminalParams2.getParams().get(TerminalParams.PARAM.F_0084));
    }
}