package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 终端鉴权
 *
 * @author Brook
 */
@Data
public class Msg0102 extends MessageBody {

    // 鉴权码长度
    private Byte authCodeLength;
    // 鉴权码
    private String authCode;
    // 终端IMEI
    private String imei;
    // 软件版本号
    private String version;

    @Override
    public void parse(ByteBuf message) {
        authCodeLength = message.readByte();
        authCode = JT808Utils.readGbk(message, authCodeLength);
        imei = JT808Utils.readGbk(message, 15);
        version = JT808Utils.readGbk(message, 20);
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(100);
        byte[] authCodeBytes = JT808Utils.getBytesGBK(authCode);
        byteBuf.writeByte((byte) authCodeBytes.length);
        byteBuf.writeBytes(authCodeBytes);
        writeFixLengthBytes(byteBuf, imei.getBytes(), 15, 1);
        writeFixLengthBytes(byteBuf, version.getBytes(), 20, 1);
        return readReadableBytes(byteBuf);
    }
}
