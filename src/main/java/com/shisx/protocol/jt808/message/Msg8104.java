package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * 查询终端参数
 *
 * @author Brook
 */
@Data
public class Msg8104 extends MessageBody {


    @Override
    public void parse(ByteBuf message) {
    }

    @Override
    public byte[] build() {
        return null;
    }

}
