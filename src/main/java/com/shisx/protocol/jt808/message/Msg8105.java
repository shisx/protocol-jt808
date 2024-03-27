package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 终端控制
 *
 * @author Brook
 */
@Data
public class Msg8105 extends MessageBody {

    // 命令参数
    private Byte cmdId;
    // 命令参数
    private String cmdParam;

    @Override
    public void parse(ByteBuf message) {
        cmdId = message.readByte();
        if (cmdId == 0x02) {
            cmdParam = JT808Utils.readGbk(message, message.readableBytes() - 2);
        }
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(100);
        byteBuf.writeByte(cmdId);
        if (cmdParam != null) {
            JT808Utils.writeGbk(byteBuf, cmdParam);
        }
        return readReadableBytes(byteBuf);
    }
}
