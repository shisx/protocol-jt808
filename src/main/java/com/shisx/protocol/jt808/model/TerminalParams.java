package com.shisx.protocol.jt808.model;

import com.shisx.protocol.jt808.util.JT808Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.shisx.protocol.jt808.model.FieldType.*;


/**
 * 终端参数
 *
 * @author Brook
 */
@Getter
@Setter
@ToString
public class TerminalParams {

    private byte total;
    private Map<PARAM, Object> params = new HashMap<>();

    /**
     * 解析
     *
     * @param byteBuf
     * @return
     */
    public static TerminalParams parse(final ByteBuf byteBuf) {
        TerminalParams tp = new TerminalParams();
        tp.total = byteBuf.readByte();
        if (tp.total == 0x00) {
            tp.params = Collections.emptyMap();
            return tp;
        }

        tp.params = new HashMap<>(tp.total);

        int paramId;
        byte paramLen;
        for (int i = 0; i < tp.total; i++) {
            paramId = byteBuf.readInt();
            paramLen = byteBuf.readByte();
            PARAM param = PARAM.from(paramId);
            if (param == null) {
                byteBuf.skipBytes(paramLen);
            } else if (param.getFieldType() == FieldType.BYTE) {
                tp.params.put(param, byteBuf.readByte());
            } else if (param.getFieldType() == FieldType.INT16) {
                tp.params.put(param, byteBuf.readShort());
            } else if (param.getFieldType() == INT32) {
                tp.params.put(param, byteBuf.readInt());
            } else if (param.getFieldType() == FieldType.INT64) {
                tp.params.put(param, byteBuf.readLong());
            } else if (param.getFieldType() == FieldType.STRING) {
                tp.params.put(param, JT808Utils.readGbk(byteBuf, paramLen));
            } else if (param.getFieldType() == FieldType.BYTES) {
                byte[] bytes = new byte[paramLen];
                byteBuf.readBytes(bytes);
                tp.params.put(param, bytes);
            }
        }

        return tp;
    }

    /**
     * 转字符数组
     *
     * @return
     */
    public byte[] toBytes() {
        ByteBuf byteBuf = Unpooled.buffer(500, 1024);
        total = (byte) params.size();
        byteBuf.writeByte(total);
        for (Map.Entry<PARAM, Object> entry : params.entrySet()) {
            writeParam(entry.getKey(), entry.getValue(), byteBuf);
        }

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    /**
     * 写参数
     *
     * @param param
     * @param value
     * @param byteBuf
     */
    private static void writeParam(PARAM param, Object value, ByteBuf byteBuf) {
        byteBuf.writeInt(param.getId());
        if (param.getFieldType() == FieldType.BYTE) {
            byteBuf.writeByte(0x01);
            byteBuf.writeByte((byte) value);
        } else if (param.getFieldType() == FieldType.INT16) {
            byteBuf.writeByte(0x02);
            byteBuf.writeShort((short) value);
        } else if (param.getFieldType() == INT32) {
            byteBuf.writeByte(0x04);
            byteBuf.writeInt((int) value);
        } else if (param.getFieldType() == FieldType.INT64) {
            byteBuf.writeByte(0x08);
            byteBuf.writeLong((long) value);
        } else if (param.getFieldType() == FieldType.STRING) {
            byte[] bytes = ((String) value).getBytes(JT808Utils.GBK);
            byteBuf.writeByte(bytes.length);
            byteBuf.writeBytes(bytes);
        } else if (param.getFieldType() == FieldType.BYTES) {
            byteBuf.writeByte(((byte[]) value).length);
            byteBuf.writeBytes((byte[]) value);
        }
    }

    /**
     * 参数
     */
    public enum PARAM {
        F_0001(0x0001, INT32, "终端心跳发送间隔，单位为秒（s）"),
        F_0002(0x0002, INT32, "TCP消息应答超时时间，单位为秒（s）"),
        F_0003(0x0003, INT32, "TCP消息重传次数"),
        F_0004(0x0004, INT32, "UDP消息应答超时时间，单位为秒（s）"),
        F_0005(0x0005, INT32, "UDP消息重传次数"),
        F_0006(0x0006, INT32, "SMS消息应答超时时间，单位为秒（s）"),
        F_0007(0x0007, INT32, "SMS消息重传次数"),
        F_0010(0x0010, STRING, "主服务器APN，无线通信拨号访问点。若网络制式为CDMA，则该处为PPP拨号号码"),
        F_0011(0x0011, STRING, "主服务器无线通信拨号用户名"),
        F_0012(0x0012, STRING, "主服务器无线通信拨号密码"),
        F_0013(0x0013, STRING, "主服务器地址,IP或域名"),
        F_0014(0x0014, STRING, "备份服务器APN，无线通信拨号访问点"),
        F_0015(0x0015, STRING, "备份服务器无线通信拨号用户名"),
        F_0016(0x0016, STRING, "备份服务器无线通信拨号密码"),
        F_0017(0x0017, STRING, "备份服务器地址,IP或域名"),
        F_001A(0x001A, STRING, "道路运输证IC卡认证主服务器IP地址或域名"),
        F_001B(0x001B, INT32, "道路运输证IC卡认证主服务器TCP端口"),
        F_001C(0x001C, INT32, "道路运输证IC卡认证主服务器UDP端口"),
        F_001D(0x001D, STRING, "道路运输证IC卡认证备份服务器IP地址或域名，端口同主服务器"),
        F_0020(0x0020, INT32, "位置汇报策略"),
        F_0021(0x0021, INT32, "位置汇报方案"),
        F_0022(0x0022, INT32, "驾驶员未登录汇报时间间隔"),
        F_0027(0x0027, INT32, "休眠时汇报时间间隔"),
        F_0028(0x0028, INT32, "紧急报警时汇报时间间隔"),
        F_0029(0x0029, INT32, "缺省时间汇报间隔"),
        F_002C(0x002C, INT32, "缺省距离汇报间隔"),
        F_002D(0x002D, INT32, "驾驶员未登录汇报距离间隔"),
        F_002E(0x002E, INT32, "休眠时汇报距离间隔"),
        F_002F(0x002F, INT32, "紧急报警时汇报距离间隔"),
        F_0030(0x0030, INT32, "拐点补传角度，<180"),
        F_0031(0x0031, INT16, "电子围栏半径（非法位移阈值）"),
        F_0040(0x0040, STRING, "监控平台电话号码"),
        F_0041(0x0041, STRING, "复位电话号码，可采用此电话号码拨打终端电话让终端复位"),
        F_0042(0x0042, STRING, "恢复出厂设置电话号码，可采用此电话号码拨打终端电话让终端恢复出厂设置"),
        F_0043(0x0043, STRING, "监控平台SMS电话号码"),
        F_0044(0x0044, STRING, "接收终端SMS文本报警号码"),
        F_0045(0x0045, INT32, "终端电话接听策略"),
        F_0046(0x0046, INT32, "每次最长通话时间"),
        F_0047(0x0047, INT32, "当月最长通话时间"),
        F_0048(0x0048, STRING, "监听电话号码"),
        F_0049(0x0049, STRING, "监管平台特权短信号码"),
        F_0050(0x0050, INT32, "报警屏蔽字"),
        F_0051(0x0051, INT32, "报警发送文本SMS开关"),
        F_0052(0x0052, INT32, "报警拍摄开关"),
        F_0053(0x0053, INT32, "报警拍摄存储标志"),
        F_0054(0x0054, INT32, "关键标志"),
        F_0055(0x0055, INT32, "最高速度，单位为公里每小时（km/h）"),
        F_0056(0x0056, INT32, "超速持续时间，单位为秒（s）"),
        F_0057(0x0057, INT32, "连续驾驶时间门限"),
        F_0058(0x0058, INT32, "当天累计驾驶时间门限"),
        F_0059(0x0059, INT32, "最小休息时间"),
        F_005A(0x005A, INT32, "最长停车时间"),
        F_005B(0x005B, INT16, "超速报警预警差值"),
        F_005C(0x005C, INT16, "疲劳驾驶预警差值"),
        F_005D(0x005D, INT16, "碰撞报警参数设置"),
        F_005E(0x005E, INT16, "侧翻报警参数设置"),
        F_0070(0x0070, INT32, "图像/视频质量"),
        F_0071(0x0071, INT32, "亮度"),
        F_0072(0x0072, INT32, "对比度"),
        F_0073(0x0073, INT32, "饱和度"),
        F_0074(0x0074, INT32, "色度"),
        F_0080(0x0080, INT32, "车辆里程表读数"),
        F_0081(0x0081, INT16, "车辆所在的省域ID"),
        F_0082(0x0082, INT16, "车辆所在的市域ID"),
        F_0083(0x0083, STRING, "公安交通管理部门颁发的机动车号牌"),
        F_0084(0x0084, BYTE, "车牌颜色"),
        F_0090(0x0090, BYTE, "GNSS定位模式"),
        F_0091(0x0091, BYTE, "GNSS波特率"),
        F_0092(0x0092, BYTE, "GNSS模块详细定位数据输出频率"),
        ;

        private final int id;
        private final FieldType fieldType;
        private final String desc;

        private PARAM(int id, FieldType fieldType, String desc) {
            this.id = id;
            this.fieldType = fieldType;
            this.desc = desc;
        }

        public static PARAM from(int id) {
            for (PARAM param : PARAM.values()) {
                if (param.getId() == id) {
                    return param;
                }
            }
            return null;
        }

        public int getId() {
            return id;
        }

        public FieldType getFieldType() {
            return fieldType;
        }

        public String getDesc() {
            return desc;
        }
    }
}
