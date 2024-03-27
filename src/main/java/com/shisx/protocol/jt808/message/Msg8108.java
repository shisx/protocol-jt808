package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 下发终端升级包
 *
 * @author Brook
 */
@Data
public class Msg8108 extends MessageBody {

    // 升级类型
    private Byte upgradeType;
    // 制造商 ID
    private byte[] mfrId;
    // 终端固件版本号长度
    private Byte firmwareVersionLength;
    // 终端固件版本号
    private String firmwareVersion;
    // 升级数据包长度
    private Integer upgradeDataLength;
    // 升级数据包
    private byte[] upgradeData;

    @Override
    public void parse(ByteBuf message) {
        upgradeType = message.readByte();
        message.readBytes(mfrId = new byte[5]);
        firmwareVersionLength = message.readByte();
        firmwareVersion = JT808Utils.readGbk(message, firmwareVersionLength);
        upgradeDataLength = message.readInt();
        message.readBytes(upgradeData = new byte[upgradeDataLength]);
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(1024);
        byteBuf.writeByte(upgradeType);
        writeFixLengthBytes(byteBuf, mfrId, 5, 0);

        byte[] firmwareVersionBytes = JT808Utils.getBytesGBK(firmwareVersion);
        byteBuf.writeByte((byte) firmwareVersionBytes.length);
        byteBuf.writeBytes(firmwareVersionBytes);

        byteBuf.writeInt(upgradeData.length);
        byteBuf.writeBytes(upgradeData);

        return readReadableBytes(byteBuf);
    }

}
