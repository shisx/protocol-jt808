package com.shisx.protocol.jt808.model;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 位置附加信息
 *
 * @author Brook
 */
@Getter
@Setter
@ToString
public class PositionAdditional {

    private Integer mileage; // 里程，单位：1/10km
    private Short oilMass; // 油量，单位：1/10L
    private Short speed; // 速度，单位：km/h，精度：0.1
    private Short pendingEventId; // 需要人工确认报警事件的ID
    private byte[] tirePressure; // 胎压，单位：kPa
    private Short carriageTemperature; // 车厢温度，单位：℃
    private Byte gprsSignal;
    private Byte gnssSignal;

    public static PositionAdditional parse(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() > 2) {
            PositionAdditional obj = new PositionAdditional();
            obj._parse(byteBuf);
            return obj;
        }
        return null;
    }

    private void _parse(ByteBuf byteBuf) {
        byte id;
        int len;
        while (byteBuf.readableBytes() > 2) {
            id = byteBuf.readByte();
            len = byteBuf.readUnsignedByte();

            switch (id) {
                case (byte) 0x01:
                    mileage = byteBuf.readInt();
                    break;
                case (byte) 0x02:
                    oilMass = byteBuf.readShort();
                    break;
                case (byte) 0x03:
                    speed = byteBuf.readShort();
                    break;
                case (byte) 0x04:
                    pendingEventId = byteBuf.readShort();
                    break;
                case (byte) 0x05:
                    byteBuf.readBytes(tirePressure = new byte[30]);
                    break;
                case (byte) 0x06:
                    carriageTemperature = byteBuf.readShort();
                    break;
                case (byte) 0x30:
                    gprsSignal = byteBuf.readByte();
                    break;
                case (byte) 0x31:
                    gnssSignal = byteBuf.readByte();
                    break;
                default:
                    byteBuf.skipBytes(len);
                    break;
            }
        }
    }

}
