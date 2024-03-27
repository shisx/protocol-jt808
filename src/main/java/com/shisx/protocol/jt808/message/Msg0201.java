package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.model.PositionInfo;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 位置查询应答
 *
 * @author Brook
 */
@Data
public class Msg0201 extends MessageBody {

    // 应答流水号
    private Short ackSn;
    // 位置信息
    private PositionInfo positionInfo;

    @Override
    public void parse(ByteBuf message) {
        ackSn = message.readShort();
        positionInfo = PositionInfo.parse(message);
    }

    @Override
    public byte[] build() {
        byte[] bytes = positionInfo.toBytes();

        ByteBuf byteBuf = fixBuffer(bytes.length + 2);
        byteBuf.writeShort(ackSn);
        byteBuf.writeBytes(bytes);
        return byteBuf.array();
    }
}
