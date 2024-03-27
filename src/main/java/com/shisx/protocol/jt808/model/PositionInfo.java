package com.shisx.protocol.jt808.model;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 位置信息
 *
 * @author Brook
 */
@Getter
@Setter
@ToString
public class PositionInfo {

    private AlarmFlag alarmFlag;// 报警标志
    private PositionStatus positionStatus; // 位置状态
    private Integer latitude; // 纬度值（以度为单位的纬度值乘以 10 的 6 次方，精确到百万分之一度）
    private Integer longitude; // 经度值（以度为单位的经度值乘以 10 的 6 次方，精确到百万分之一度）
    private Short altitude; // 海拔高度，单位为米（m）
    private Short speed; // 速度，单位为0.1km/h
    private Short direction; // 方向0-359，正北为0，顺时针
    private Long time; // 定位时间（本地时间）
    private PositionAdditional additional; // 附加信息

    /**
     * 解析位置信息
     *
     * @param byteBuf
     * @return
     */
    public static PositionInfo parse(ByteBuf byteBuf) {
        PositionInfo positionInfo = new PositionInfo();

        // 解析告警信息
        positionInfo.alarmFlag = AlarmFlag.parse(byteBuf.readInt());

        // 解析状态信息
        positionInfo.positionStatus = PositionStatus.parse(byteBuf.readInt());

        // 位置信息
        positionInfo.latitude = byteBuf.readInt();
        positionInfo.longitude = byteBuf.readInt();
        positionInfo.altitude = byteBuf.readShort();
        positionInfo.speed = byteBuf.readShort();
        positionInfo.direction = byteBuf.readShort();
        positionInfo.time = JT808Utils.readTime(byteBuf);

        // 解析附加信息
        positionInfo.additional = PositionAdditional.parse(byteBuf);

        return positionInfo;
    }

    public byte[] toBytes() {
        ByteBuf buff = Unpooled.buffer(30, 1024);
        buff.writeInt(alarmFlag.toInt32());
        buff.writeInt(positionStatus.toInt32());
        buff.writeInt(latitude);
        buff.writeInt(longitude);
        buff.writeShort(altitude == null ? 0 : altitude);
        buff.writeShort(speed == null ? 0 : speed);
        buff.writeShort(direction == null ? 0 : direction);
        JT808Utils.writeTime(buff, time);
        if (additional != null) {
            // TODO 待实现
            // buff.writeBytes(additional.toBytes());
        }
        byte[] dst = new byte[buff.readableBytes()];
        buff.readBytes(dst);
        return dst;
    }

}
