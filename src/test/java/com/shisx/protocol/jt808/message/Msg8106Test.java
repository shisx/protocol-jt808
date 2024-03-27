package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.HexUtil;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Msg8106Test  {

    @Test
    public void testBuildAndParse() {
        List<Integer> params = new ArrayList<>();
        params.add(1);
        params.add(10);
        params.add(2);
        params.add(30);

        Msg8106 body = new Msg8106();
        body.setParams(params);

        String hex = HexUtil.encodeHexStr(JT808Message.build("123456789123456789", body));
        System.out.println(hex);

        byte[] bytes = HexUtil.hexStringToBytes(hex);
        JT808Message jt808Message = JT808Message.parse(bytes);
        System.out.println(jt808Message);
        assertEquals(body.getClass(), jt808Message.getMessageBody().getClass());

        Msg8106 body2 = (Msg8106) jt808Message.getMessageBody();
        assertArrayEquals(body.getParams().toArray(), body2.getParams().toArray());
    }
}