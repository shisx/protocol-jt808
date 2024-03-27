package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 终端注册应答
 *
 * @author Brook
 */
@Data
public class Msg8100 extends MessageBody {

    // 应答流水号
    private Short ackSn;
    // 结果
    private Byte result;
    // 鉴权码
    private String authCode;

    @Override
    public void parse(ByteBuf message) {
        ackSn = message.readShort();
        result = message.readByte();
        if (result == 0x00) {
            authCode = JT808Utils.readGbk(message, message.readableBytes() - 2);
        }
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(20);
        byteBuf.writeShort(ackSn);
        byteBuf.writeByte(result);
        if (authCode != null) {
            JT808Utils.writeGbk(byteBuf, authCode);
        }
        return readReadableBytes(byteBuf);
    }

}
