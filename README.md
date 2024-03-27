# protocol-jt808

`protocol-jt808` 是一个实现了对《JT/T808 2019道路运输车辆卫星定位系统终端通讯协议及数据格式》数据进行解析与拼接的工具库。

* 基于`JDK8`,`Netty`

# 1 使用准备

## 1.1 引入依赖

`Maven`

```xml

<dependency>
    <groupId>com.shisx.protocol</groupId>
    <artifactId>protocol-jt808</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 1.2 数据解析

> 将字节数组解析成`JT808Message`对象

```java
class Example {
    public static void main(String[] args) {
        byte[] bytes = ...;
        JT808Message jt808Message = JT808Message.parse(bytes);
    }
}
```

## 1.2 数据拼装

> 将`JT808Message`对象转换字节数组

示例：为手机号为`123456789123456789`的终端构建一个`0x8100`消息

```java
class Example {
    public static void main(String[] args) {
        Msg8100 body = new Msg8100();
        body.setAckSn((short) 1);
        body.setResult((byte) 0);
        body.setAuthCode("success");

        JT808Message.build("123456789123456789", body);
    }
}
```

## 1.2 作为服务

### 1.2.1 TCP服务

启动一个TCP服务，监听客户端发送的消息，并将消息解析成[JT808Message](/src/main/java/com/shisx/protocol/jt808/message/JT808Message.java)
对象，并进行业务处理

[TcpListener](/src/main/java/com/shisx/protocol/jt808/network/TcpListener.java)
