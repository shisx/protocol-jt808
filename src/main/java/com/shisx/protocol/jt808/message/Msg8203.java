package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 人工确认报警消息
 *
 * @author Brook
 */
@Data
public class Msg8203 extends MessageBody {

    // 报警消息流水号
    private Short sn;
    // 人工报警类型
    private Integer type;

    @Override
    public void parse(ByteBuf message) {
        sn = message.readShort();
        type = message.readInt();
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = fixBuffer(6);
        byteBuf.writeShort(sn);
        byteBuf.writeInt(type);
        return byteBuf.array();
    }

}
