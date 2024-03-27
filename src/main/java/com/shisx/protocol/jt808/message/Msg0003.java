package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 终端注销
 *
 * @author Brook
 */
@Data
public class Msg0003 extends MessageBody {

    @Override
    public void parse(ByteBuf message) {
    }

    @Override
    public byte[] build() {
        return null;
    }
}
