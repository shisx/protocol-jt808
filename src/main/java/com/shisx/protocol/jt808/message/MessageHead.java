package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 消息头
 */
@Data
public class MessageHead implements IParser, IBuilder {

    // 消息ID
    private Short msgId;
    // 消息体属性 - 消息体长度
    private Short bodyLength;
    // 协议版本号
    private Byte protocolVersion;
    // 终端手机号
    private String terminalNumber;
    // 消息流水号
    private Short msgSn;

    @Override
    public void parse(ByteBuf message) {
        message.readerIndex(1);

        msgId = message.readShort();
        // 只提取消息体长度，暂不考虑数据加密、分包
        bodyLength = (short) (message.readShort() & 0x03FF);
        protocolVersion = message.readByte();
        terminalNumber = ProtocolUtil.readBcd(message, 10);
        msgSn = message.readShort();
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = allowBuffer(17 + bodyLength);
        byteBuf.writeShort(msgId);
        byteBuf.writeShort(bodyLength);
        byteBuf.writeByte(protocolVersion);
        if (terminalNumber.length() < 20) {
            terminalNumber = StringUtils.leftPad(terminalNumber, 20, "0");
        }
        byteBuf.writeBytes(ProtocolUtil.string2Bcd(terminalNumber));
        byteBuf.writeShort(msgSn);
        return readReadableBytes(byteBuf);
    }
}
