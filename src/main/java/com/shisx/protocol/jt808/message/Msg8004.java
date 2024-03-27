package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 查询服务器时间应答
 *
 * @author Brook
 */
@Data
public class Msg8004 extends MessageBody {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    private LocalDateTime time;

    @Override
    public void parse(ByteBuf message) {
        time = LocalDateTime.parse(ProtocolUtil.readBcd(message, 6), fmt);
    }

    @Override
    public byte[] build() {
        return ProtocolUtil.string2Bcd(time.format(fmt));
    }
}
