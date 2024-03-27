package com.shisx.protocol.jt808.network;

import junit.framework.TestCase;

public class TcpListenerTest extends TestCase {

    public void testListen() {
        TcpListener.listen(8888);
    }
}