package com.shisx.protocol.jt808.message;

import com.shisx.protocol.jt808.util.JT808Utils;
import com.shisx.protocol.jt808.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 查询终端属性应答
 *
 * @author Brook
 */
@Data
public class Msg0107 extends MessageBody {

    // 终端类型
    private Short terminalType;
    // 制造商 ID
    private byte[] mfrId;
    // 终端型号
    private byte[] terminalModel;
    // 终端ID
    private byte[] terminalId;
    // ICCID
    private String iccid;
    // 终端硬件版本号长度
    private Byte hardwareVersionLength;
    // 终端硬件版本号
    private String hardwareVersion;
    // 终端固件版本号长度
    private Byte firmwareVersionLength;
    // 终端固件版本号
    private String firmwareVersion;
    // GNSS模块属性
    private Byte gnssParam;
    // 通信模块属性
    private Byte signalParam;

    @Override
    public void parse(ByteBuf message) {
        terminalType = message.readShort();
        message.readBytes(mfrId = new byte[5]);
        message.readBytes(terminalModel = new byte[30]);
        message.readBytes(terminalId = new byte[30]);
        iccid = ProtocolUtil.readBcd(message, 10);
        hardwareVersionLength = message.readByte();
        hardwareVersion = JT808Utils.readGbk(message, hardwareVersionLength);
        firmwareVersionLength = message.readByte();
        firmwareVersion = JT808Utils.readGbk(message, firmwareVersionLength);
        gnssParam = message.readByte();
        signalParam = message.readByte();
    }

    @Override
    public byte[] build() {
        ByteBuf byteBuf = Unpooled.buffer(200);
        byteBuf.writeShort(terminalType);
        writeFixLengthBytes(byteBuf, mfrId, 5, 0);
        writeFixLengthBytes(byteBuf, terminalModel, 30, 1);
        writeFixLengthBytes(byteBuf, terminalId, 30, 1);
        byteBuf.writeBytes(ProtocolUtil.string2Bcd(iccid));

        byte[] hardwareVersionBytes = JT808Utils.getBytesGBK(hardwareVersion);
        byteBuf.writeByte((byte) hardwareVersionBytes.length);
        byteBuf.writeBytes(hardwareVersionBytes);

        byte[] firmwareVersionBytes = JT808Utils.getBytesGBK(firmwareVersion);
        byteBuf.writeByte((byte) firmwareVersionBytes.length);
        byteBuf.writeBytes(firmwareVersionBytes);

        byteBuf.writeByte(gnssParam);
        byteBuf.writeByte(signalParam);
        return readReadableBytes(byteBuf);
    }

}
