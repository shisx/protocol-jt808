package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.model.TerminalParams;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 设置终端参数
 *
 * @author Brook
 */
@Data
public class Msg8103 extends MessageBody {

    // 终端参数
    private TerminalParams terminalParams;

    @Override
    public void parse(ByteBuf message) {
        terminalParams = TerminalParams.parse(message);
    }

    @Override
    public byte[] build() {
        return terminalParams.toBytes();
    }
}
