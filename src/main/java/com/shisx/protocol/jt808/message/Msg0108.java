package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 终端升级结果应答
 *
 * @author Brook
 */
@Data
public class Msg0108 extends MessageBody {

    // 升级类型
    private Byte type;
    // 升级结果
    private Byte result;

    @Override
    public void parse(ByteBuf message) {
        type = message.readByte();
        result = message.readByte();
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = fixBuffer(2);
        byteBuf.writeByte(type);
        byteBuf.writeByte(result);
        return byteBuf.array();
    }

}
