package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Msg8300Test {

    @Test
    public void testBuildAndParse() {
        Msg8300 body = new Msg8300();
        body.setFlag((byte) 1);
        body.setTextType((byte)1);
        body.setText("Hell World! 我是一个Box");

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8300 body2 = (Msg8300) jt808Message.getMessageBody();
        assertEquals(body.getFlag(), body2.getFlag());
        assertEquals(body.getTextType(), body2.getTextType());
        assertEquals(body.getText(), body2.getText());
    }

}