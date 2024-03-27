package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 位置信息查询
 *
 * @author Brook
 */
@Data
public class Msg8201 extends MessageBody {

    @Override
    public void parse(ByteBuf message) {
    }

    @Override
    public byte[] build() {
        return null;
    }
}
