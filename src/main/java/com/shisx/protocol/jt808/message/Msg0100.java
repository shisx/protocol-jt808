package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 终端注册
 *
 * @author Brook
 */
@Data
public class Msg0100 extends MessageBody {

    // 省域 ID
    private Short provinceId;
    // 市县域 ID
    private Short cityId;
    // 制造商ID
    private byte[] mfrId;
    // 终端型号
    private byte[] terminalModel;
    // 终端ID
    private byte[] terminalId;
    // 车牌颜色
    private Byte plateColor;
    // 车牌号
    private String plateNumber;

    @Override
    public void parse(ByteBuf byteBuf) {
        provinceId = byteBuf.readShort();
        cityId = byteBuf.readShort();
        byteBuf.readBytes(mfrId = new byte[11]);
        byteBuf.readBytes(terminalModel = new byte[30]);
        byteBuf.readBytes(terminalId = new byte[30]);
        plateColor = byteBuf.readByte();
        plateNumber = JT808Utils.readGbk(byteBuf, byteBuf.readableBytes() - 2);
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(80, 90);
        byteBuf.writeShort(provinceId);
        byteBuf.writeShort(cityId);
        writeFixLengthBytes(byteBuf, mfrId, 11, 0);
        writeFixLengthBytes(byteBuf, terminalModel, 30, 0);
        writeFixLengthBytes(byteBuf, terminalId, 30, 0);
        byteBuf.writeByte(plateColor);
        byteBuf.writeBytes(JT808Utils.getBytesGBK(plateNumber));
        return readReadableBytes(byteBuf);
    }

}
