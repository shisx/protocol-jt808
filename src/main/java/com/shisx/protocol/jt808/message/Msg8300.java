package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 文本信息下发
 *
 * @author Brook
 */
@Data
public class Msg8300 extends MessageBody {

    // 标志
    private Byte flag;
    // 文本类型
    private Byte textType;
    // 文本信息（最长为 1024 字节，经 GBK 编码）
    private String text;

    @Override
    public void parse(ByteBuf message) {
        flag = message.readByte();
        textType = message.readByte();
        text = JT808Utils.readGbk(message, message.readableBytes() - 2);
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = allowBuffer(1024);
        byteBuf.writeByte(flag);
        byteBuf.writeByte(textType);
        JT808Utils.writeGbk(byteBuf, text);
        return readReadableBytes(byteBuf);
    }

}
