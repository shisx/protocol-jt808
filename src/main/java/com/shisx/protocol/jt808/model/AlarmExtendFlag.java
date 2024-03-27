package com.shisx.protocol.jt808.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 告警扩展标识
 */
@Setter
@Getter
@ToString
public class AlarmExtendFlag {


    // 碰撞报警
    private boolean collision;
    // 拖车报警
    private boolean trailer;
    // 翻车报警
    private boolean rollover;
    // 低电压报警（汽车）
    private boolean lowVoltage;

    // 汽车点火上报
    private boolean ignition;
    // 汽车熄火上报
    private boolean flameout;
    // 车机拔出报警(带电池情况下)
    private boolean pullout;
    // 车机插上报警(带电池情况下)
    private boolean insert;
    // 定位过长报警
    private boolean overlongLocation;
    // 怠速过长报警
    private boolean idle;
    // 水温报警
    private boolean waterTemperature;
    // 急加速
    private boolean rapidAcc;
    // 急减速
    private boolean rapidDec;
    // 急转弯
    private boolean rapidTurn;

    // GID FLASH故障报警
    private boolean gidFlashFault;
    // GID CAN模块故障报警
    private boolean gidCanFault;
    // GID 3D传感器故障报警
    private boolean gid3DSensorFault;
    // GID GPS模块故障报警
    private boolean gidGPSFault;
}
