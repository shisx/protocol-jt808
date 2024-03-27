package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.model.PositionInfo;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 终端位置汇报
 *
 * @author Brook
 */
@Data
public class Msg0200 extends MessageBody {

    private PositionInfo positionInfo;

    @Override
    public void parse(ByteBuf message) {
        positionInfo = PositionInfo.parse(message);
    }

    @Override
    public byte[] build() {
        return positionInfo.toBytes();
    }
}
