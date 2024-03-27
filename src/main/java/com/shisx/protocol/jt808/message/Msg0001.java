package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 终端通用应答
 *
 * @author Brook
 */
@Data
public class Msg0001 extends MessageBody {

    // 应答流水号
    private Short ackSn;
    // 应答消息ID
    private Short ackId;
    // 结果
    private Byte result;

    @Override
    public void parse(ByteBuf message) {
        ackSn = message.readShort();
        ackId = message.readShort();
        result = message.readByte();
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(5, 5);
        byteBuf.writeShort(ackSn);
        byteBuf.writeShort(ackId);
        byteBuf.writeByte(result);
        return byteBuf.array();
    }

}
