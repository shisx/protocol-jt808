package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import com.shisx.protocol.jt808.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Desc
 *
 * @author Brook
 */
@Data
public class JT808Message implements IParser, IBuilder {

    // 消息头
    private MessageHead messageHead;
    // 消息体
    private MessageBody messageBody;

    @Override
    public void parse(ByteBuf message) {
        messageHead = new MessageHead();
        messageHead.parse(message);

        try {
            messageBody = MessageBody.buildByMsgId(messageHead.getMsgId());
            if (messageBody == null) {
                throw new RuntimeException("Can't parse the message: " + ProtocolUtil.short2Hex(messageHead.getMsgId()));
            }

            messageBody.parse(message);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("The message body parsing failed.", e);
        }
    }

    @Override
    public byte[] build() {
        byte[] bodyBytes = messageBody.build();
        messageHead.setBodyLength((short) bodyBytes.length);
        return _build(messageHead.build(), bodyBytes);
    }

    public static JT808Message parse(byte[] bytes) {
        // 转义还原
        byte[] finalData = JT808Utils.flagRestore(bytes);

        // 验证校验和
        if (!JT808Utils.verifyCRC(finalData)) {
            throw new RuntimeException("校验和不通过");
        }

        // 解析
        JT808Message jt808Message = new JT808Message();
        jt808Message.parse(Unpooled.wrappedBuffer(finalData));
        return jt808Message;
    }

    public static byte[] build(String terminalNumber, MessageBody messageBody) {
        JT808Message jt808Message = new JT808Message();

        byte[] bodyBytes = messageBody.build();

        MessageHead head = new MessageHead();
        head.setMsgId(MessageBody.getMsgIdByClass(messageBody.getClass()));
        head.setMsgSn(JT808Utils.genMsgSn());
        head.setProtocolVersion((byte) 0x01);
        head.setTerminalNumber(terminalNumber);
        head.setBodyLength(bodyBytes == null ? 0 : (short) bodyBytes.length);

        return jt808Message._build(head.build(), bodyBytes);
    }

    private byte[] _build(byte[] headBytes, byte[] bodyBytes) {
        ByteBuf byteBuf = allowBuffer(1024);

        // 标识位-头
        byteBuf.writeByte((byte) 0x7E);

        // 消息头
        byteBuf.writeBytes(headBytes);

        // 消息体
        if (bodyBytes != null && bodyBytes.length > 0) {
            byteBuf.writeBytes(bodyBytes);
        }

        // 校验码
        byte[] currBytes = new byte[byteBuf.readerIndex(1).readableBytes()];
        byteBuf.readBytes(currBytes);
        byteBuf.writeByte(ProtocolUtil.xor(currBytes));

        // 标识位-尾
        byteBuf.writeByte((byte) 0x7E);

        // 转义，并返回数据
        byte[] allBytes = new byte[byteBuf.readerIndex(0).readableBytes()];
        byteBuf.readBytes(allBytes);
        return JT808Utils.flagTransference(allBytes);
    }

}
