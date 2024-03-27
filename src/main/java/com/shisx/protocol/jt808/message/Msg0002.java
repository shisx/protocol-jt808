package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 终端通用应答
 *
 * @author Brook
 */
@Data
public class Msg0002 extends MessageBody {

    @Override
    public void parse(ByteBuf message) {
    }

    @Override
    public byte[] build() {
        return null;
    }
}
