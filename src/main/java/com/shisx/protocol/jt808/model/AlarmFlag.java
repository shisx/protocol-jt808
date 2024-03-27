package com.shisx.protocol.jt808.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.shisx.protocol.jt808.util.ByteUtils.*;

/**
 * 告警标志
 *
 * @author Brook
 */
@Setter
@Getter
@ToString
public class AlarmFlag {

    // 原始告警标识值
    private int raw;

    // 0  紧急报警，触动报警开关后触发，收到应答后清零
    private boolean emergencyAlarm;

    // 1  超速报警，标志维持至报警条件解除
    private boolean speeding;

    // 2  疲劳驾驶，标志维持至报警条件解除
    private boolean fatigueDriving;

    // 3  危险预警，收到应答后清零
    private boolean dangerAlarm;

    // 4  GNSS 模块发生故障，标志维持至报警条件解除
    private boolean gnssFault;

    // 5  GNSS 天线未接或被剪断，标志维持至报警条件解除
    private boolean gnssAerialDisconn;

    // 6  GNSS 天线短路，标志维持至报警条件解除
    private boolean gnssAerialShort;

    // 7  终端主电源欠压，标志维持至报警条件解除
    private boolean mpUndervoltage;

    // 8  终端主电源掉电，标志维持至报警条件解除
    private boolean mpPowerDown;

    // 9  终端 LCD 或显示器故障，标志维持至报警条件解除
    private boolean displayFault;

    // 10 TTS 模块故障，标志维持至报警条件解除
    private boolean ttsFault;

    // 11 摄像头故障，标志维持至报警条件解除
    private boolean cameraFault;

    // 12 道路运输证 IC 卡模块故障，标志维持至报警条件解除
    private boolean icCardFault;

    // 13 超速预警，标志维持至报警条件解除
    private boolean speedingWaring;

    // 14 疲劳驾驶预警，标志维持至报警条件解除
    private boolean fatigueDrivingWaring;

    // 15-17 保留

    // 18 当天累计驾驶超时，标志维持至报警条件解除
    private boolean dayDrivingOvertime;

    // 19 超时停车，标志维持至报警条件解除
    private boolean stopOvertime;

    // 20 进出区域，收到应答后清零
    private boolean entryArea;

    // 21 进出路线，收到应答后清零
    private boolean entryRoute;

    // 22 路段行驶时间不足/过长，收到应答后清零
    private boolean routeDrivingSL;

    // 23 路线偏离报警，标志维持至报警条件解除
    private boolean routeDeviate;

    // 24 车辆 VSS 故障，标志维持至报警条件解除
    private boolean vssFault;

    // 25 车辆油量异常，标志维持至报警条件解除
    private boolean oilMassException;

    // 26 车辆被盗(通过车辆防盗器)，标志维持至报警条件解除
    private boolean burglar;

    // 27 车辆非法点火，收到应答后清零
    private boolean illegalIgnition;

    // 28 车辆非法位移，收到应答后清零
    private boolean illegalMove;

    // 29 碰撞预警，标志维持至报警条件解除
    private boolean collisionWaring;

    // 30 侧翻预警，标志维持至报警条件解除
    private boolean rolloverWaring;

    // 31 非法开门报警（终端未设置区域时，不判断非法开门），收到应答后清零
    private boolean illegalOpenDoor;

    /**
     * 将Int32类型值解析成AlarmFlag对象
     *
     * @param val
     * @return
     */
    public static AlarmFlag parse(int val) {
        AlarmFlag af = new AlarmFlag();
        af.setRaw(val);
        af.emergencyAlarm = rightMoveReturnBool(val, 0);  // 0
        af.speeding = rightMoveReturnBool(val, 1);   // 1
        af.fatigueDriving = rightMoveReturnBool(val, 2); // 2
        af.dangerAlarm = rightMoveReturnBool(val, 3);  // 3
        af.gnssFault = rightMoveReturnBool(val, 4); // 4
        af.gnssAerialDisconn = rightMoveReturnBool(val, 5); // 5
        af.gnssAerialShort = rightMoveReturnBool(val, 6); // 6
        af.mpUndervoltage = rightMoveReturnBool(val, 7); // 7
        af.mpPowerDown = rightMoveReturnBool(val, 8); // 8
        af.displayFault = rightMoveReturnBool(val, 9); // 9
        af.ttsFault = rightMoveReturnBool(val, 10); // 10
        af.cameraFault = rightMoveReturnBool(val, 11); // 11
        af.icCardFault = rightMoveReturnBool(val, 12); // 12
        af.speedingWaring = rightMoveReturnBool(val, 13); // 13
        af.fatigueDrivingWaring = rightMoveReturnBool(val, 14); // 14
        // 15 - 17
        af.dayDrivingOvertime = rightMoveReturnBool(val, 18); // 18
        af.stopOvertime = rightMoveReturnBool(val, 19); // 19
        af.entryArea = rightMoveReturnBool(val, 20); // 20
        af.entryRoute = rightMoveReturnBool(val, 21); // 21
        af.routeDrivingSL = rightMoveReturnBool(val, 22); // 22
        af.routeDeviate = rightMoveReturnBool(val, 23); // 23
        af.vssFault = rightMoveReturnBool(val, 24); // 24
        af.oilMassException = rightMoveReturnBool(val, 25); // 25
        af.burglar = rightMoveReturnBool(val, 26); // 26
        af.illegalIgnition = rightMoveReturnBool(val, 27); // 27
        af.illegalMove = rightMoveReturnBool(val, 28); // 28
        af.collisionWaring = rightMoveReturnBool(val, 29); // 29
        af.rolloverWaring = rightMoveReturnBool(val, 30); // 30
        af.illegalOpenDoor = rightMoveReturnBool(val, 31); // 31
        return af;
    }

    /**
     * 转Int32类型值
     *
     * @return
     */
    public int toInt32() {
        int i = 0;
        int step = 0;
        i |= leftMoveByBool(emergencyAlarm, step++);  // 0
        i |= leftMoveByBool(speeding, step++);   // 1
        i |= leftMoveByBool(fatigueDriving, step++); // 2
        i |= leftMoveByBool(dangerAlarm, step++); // 3
        i |= leftMoveByBool(gnssFault, step++); // 4
        i |= leftMoveByBool(gnssAerialDisconn, step++); // 5
        i |= leftMoveByBool(gnssAerialShort, step++); // 6
        i |= leftMoveByBool(mpUndervoltage, step++); // 7
        i |= leftMoveByBool(mpPowerDown, step++); // 8
        i |= leftMoveByBool(displayFault, step++); // 9
        i |= leftMoveByBool(ttsFault, step++); // 10
        i |= leftMoveByBool(cameraFault, step++); // 11
        i |= leftMoveByBool(icCardFault, step++); // 12
        i |= leftMoveByBool(speedingWaring, step++); // 13
        i |= leftMoveByBool(fatigueDrivingWaring, step++); // 14
        step += 3; // 15 - 17
        i |= leftMoveByBool(dayDrivingOvertime, step++); // 18
        i |= leftMoveByBool(stopOvertime, step++); // 19
        i |= leftMoveByBool(entryArea, step++); // 20
        i |= leftMoveByBool(entryRoute, step++); // 21
        i |= leftMoveByBool(routeDrivingSL, step++); // 22
        i |= leftMoveByBool(routeDeviate, step++); // 23
        i |= leftMoveByBool(vssFault, step++); // 24
        i |= leftMoveByBool(oilMassException, step++); // 25
        i |= leftMoveByBool(burglar, step++); // 26
        i |= leftMoveByBool(illegalIgnition, step++); // 27
        i |= leftMoveByBool(illegalMove, step++); // 28
        i |= leftMoveByBool(collisionWaring, step++); // 29
        i |= leftMoveByBool(rolloverWaring, step++); // 30
        i |= leftMoveByBool(illegalOpenDoor, step); // 31
        return i;
    }

}
