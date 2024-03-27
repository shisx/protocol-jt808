package com.shisx.protocol.jt808.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.shisx.protocol.jt808.util.ByteUtils.*;

/**
 * 位置状态信息
 *
 * @author Brook
 */
@Getter
@Setter
@ToString
public class PositionStatus {

    /**
     * 车辆负载状态 - 空载
     */
    public static final byte LOAD_STATUS_EMPTY = 0x00;
    /**
     * 车辆负载状态 - 半载
     */
    public static final byte LOAD_STATUS_HALF = 0x01;
    /**
     * 车辆负载状态 - 保留
     */
    public static final byte LOAD_STATUS_RETAIN = 0x02;
    /**
     * 车辆负载状态 - 满载
     */
    public static final byte LOAD_STATUS_FULL = 0x03;

    public static final byte LAT_N = 0x00;
    public static final byte LAT_S = 0x01;
    public static final byte LNG_E = 0x00;
    public static final byte LNG_W = 0x01;

    /**
     * 油路状态 - 正常
     */
    public static final byte OILWAY_NORMAL = 0x00;
    /**
     * 油路状态 - 断开
     */
    public static final byte OILWAY_DISCONN = 0x01;

    /**
     * 电路状态 - 正常
     */
    public static final byte CIRCUIT_NORMAL = 0x00;
    /**
     * 电路状态 - 断开
     */
    public static final byte CIRCUIT_DISCONN = 0x01;

    /**
     * 门解锁
     */
    public static final byte DOOR_UNLOCK = 0x00;
    /**
     * 门加锁
     */
    public static final byte DOOR_LOCK = 0x01;

    /**
     * 门关
     */
    public static final byte DOOR_CLOSE = 0x00;
    /**
     * 门开
     */
    public static final byte DOOR_OPPEN = 0x01;

    // 原始状态标识值
    private int raw;

    // 0   0：ACC 关；1： ACC 开
    private boolean accOpen;
    // 1   0：未定位；1：定位
    private boolean validPosition;
    // 2   0：北纬；1：南纬
    private byte latFlag;
    // 3   0：东经；1：西经
    private byte lngFlag;
    // 4   0：运营状态；1：停运状态
    private boolean stopping;
    // 5   0：经纬度未经保密插件加密；1：经纬度已经保密插件加密
    private boolean encryptedPosition;
    // 6-7 保留
    // 8-9 00：空车；01：半载；10：保留；11：满载
    private byte loadStatus;
    // 10  0：车辆油路正常；1：车辆油路断开
    private byte oilwayStatus;
    // 11  0：车辆电路正常；1：车辆电路断开
    private byte circuitStatus;
    // 12  0：车门解锁；1：车门加锁
    private byte doorLock;
    // 13  0：门1关；1：门1开（前门）
    private byte door1;
    // 14  0：门2关；1：门2开（中门）
    private byte door2;
    // 15  0：门3关；1：门3开（后门）
    private byte door3;
    // 16  0：门4关；1：门4开（驾驶席门）
    private byte door4;
    // 17  0：门5关；1：门5开（自定义）
    private byte door5;
    // 18  0：未使用 GPS 卫星进行定位；1：使用 GPS 卫星进行定位
    private boolean useGPS;
    // 19  0：未使用北斗卫星进行定位；1：使用北斗卫星进行定位
    private boolean useBeidou;
    // 20  0：未使用 GLONASS 卫星进行定位；1：使用 GLONASS 卫星进行定位
    private boolean useGlonass;
    // 21  0：未使用 Galileo 卫星进行定位；1：使用 Galileo 卫星进行定位
    private boolean useGalileo;
    // 22-31 保留

    public static PositionStatus defaultStatus() {
        PositionStatus ps = new PositionStatus();
        ps.accOpen = false;
        ps.validPosition = true;
        ps.useGPS = true;
        ps.latFlag = 0x00;
        ps.lngFlag = 0x00;
        return ps;
    }

    /**
     * 将Int32类型值解析成AlarmFlag对象
     *
     * @param val
     * @return
     */
    public static PositionStatus parse(int val) {
        PositionStatus ps = new PositionStatus();
        ps.setRaw(val);
        ps.accOpen = rightMoveReturnBool(val, 0);  // 0
        ps.validPosition = rightMoveReturnBool(val, 1);   // 1
        ps.latFlag = rightMoveReturnByte(val, 2); // 2
        ps.lngFlag = rightMoveReturnByte(val, 3); // 3
        ps.stopping = rightMoveReturnBool(val, 4); // 4
        ps.encryptedPosition = rightMoveReturnBool(val, 5); // 5
        // 6-7
        ps.loadStatus = rightMoveReturnByte(val, 8, (byte) 0x03); // 8-9
        ps.oilwayStatus = rightMoveReturnByte(val, 10); // 10
        ps.circuitStatus = rightMoveReturnByte(val, 11); // 11
        ps.doorLock = rightMoveReturnByte(val, 12); // 12
        ps.door1 = rightMoveReturnByte(val, 13); // 13
        ps.door2 = rightMoveReturnByte(val, 14); // 14
        ps.door3 = rightMoveReturnByte(val, 15); // 15
        ps.door4 = rightMoveReturnByte(val, 16); // 16
        ps.door5 = rightMoveReturnByte(val, 17); // 17
        ps.useGPS = rightMoveReturnBool(val, 18); // 18
        ps.useBeidou = rightMoveReturnBool(val, 19); // 19
        ps.useGlonass = rightMoveReturnBool(val, 20); // 20
        ps.useGalileo = rightMoveReturnBool(val, 21); // 21

        return ps;
    }

    /**
     * 转Int32类型值
     *
     * @return
     */
    public int toInt32() {
        int i = 0;
        int step = 0;
        i |= leftMoveByBool(accOpen, step++);  // 0
        i |= leftMoveByBool(validPosition, step++);   // 1
        i |= leftMoveByByte(latFlag, step++);   // 2
        i |= leftMoveByByte(lngFlag, step++);   // 3
        i |= leftMoveByBool(stopping, step++);   // 4
        i |= leftMoveByBool(encryptedPosition, step++);   // 5
        step += 2;  // 6-7
        i |= leftMoveByByte(loadStatus, step++);   // 8-9
        step++;
        i |= leftMoveByByte(oilwayStatus, step++);   // 10
        i |= leftMoveByByte(circuitStatus, step++);   // 11
        i |= leftMoveByByte(doorLock, step++);   // 12
        i |= leftMoveByByte(door1, step++);   // 13
        i |= leftMoveByByte(door2, step++);   // 14
        i |= leftMoveByByte(door3, step++);   // 15
        i |= leftMoveByByte(door4, step++);   // 16
        i |= leftMoveByByte(door5, step++);   // 17
        i |= leftMoveByBool(useGPS, step++);   // 18
        i |= leftMoveByBool(useBeidou, step++);   // 19
        i |= leftMoveByBool(useGlonass, step++);   // 20
        i |= leftMoveByBool(useGalileo, step);   // 21

        return i;
    }

}
