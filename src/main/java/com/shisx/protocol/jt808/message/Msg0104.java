package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.model.TerminalParams;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 查询终端参数应答
 *
 * @author Brook
 */
@Data
public class Msg0104 extends MessageBody {

    // 应答流水号
    private Short ackSn;
    // 参数项列表
    private TerminalParams terminalParams;

    @Override
    public void parse(ByteBuf message) {
        ackSn = message.readShort();
        terminalParams = TerminalParams.parse(message);
    }

    @Override
    public byte[] build() {
        byte[] bytes = terminalParams.toBytes();
        ByteBuf byteBuf = Unpooled.buffer(2 + bytes.length, 2 + bytes.length);
        byteBuf.writeShort(ackSn);
        byteBuf.writeBytes(bytes);
        return byteBuf.array();
    }
}
