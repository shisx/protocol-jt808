package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 链路检测
 *
 * @author Brook
 */
@Data
public class Msg8204 extends MessageBody {

    @Override
    public void parse(ByteBuf message) {
    }

    @Override
    public byte[] build() {
        return null;
    }
}
