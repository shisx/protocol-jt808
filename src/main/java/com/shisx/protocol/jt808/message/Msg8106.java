package com.shisx.protocol.jt808.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询终端参数
 *
 * @author Brook
 */
@Data
public class Msg8106 extends MessageBody {

    // 参数总数
    private Byte count;
    // 参数ID列表
    private List<Integer> params = new ArrayList<>();

    @Override
    public void parse(ByteBuf message) {
        count = message.readByte();
        params = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            params.add(message.readInt());
        }
    }

    @Override
    public byte[] build() {
        count = (byte) params.size();
        ByteBuf byteBuf = Unpooled.buffer(1 + count * 4);
        byteBuf.writeByte(count);
        for (Integer param : params) {
            byteBuf.writeInt(param);
        }
        return readReadableBytes(byteBuf);
    }

}
