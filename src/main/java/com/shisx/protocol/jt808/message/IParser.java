package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;

public interface IParser {

    void parse(ByteBuf message);

}
