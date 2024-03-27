package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 临时位置跟踪控制
 *
 * @author Brook
 */
@Data
public class Msg8202 extends MessageBody {

    // 时间间隔，单位：秒
    private Short interval;
    // 位置跟踪有效期，单位：秒
    private Integer duration;

    @Override
    public void parse(ByteBuf message) {
        interval = message.readShort();
        duration = message.readInt();
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = fixBuffer(6);
        byteBuf.writeShort(interval);
        byteBuf.writeInt(duration);
        return byteBuf.array();
    }

}
