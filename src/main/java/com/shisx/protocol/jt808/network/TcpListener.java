package com.shisx.protocol.jt808.network;

import com.shisx.protocol.jt808.message.JT808Message;
import com.shisx.protocol.jt808.message.Msg8001;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TcpListener {

    /**
     * 监听
     *
     * @param port 端口
     */
    public static void listen(int port) {
        TcpListener tcpListener = new TcpListener();
        BaseNettyServerHandler handler = new BaseNettyServerHandler();
        tcpListener.listen(port, handler);
    }

    /**
     * 启动监听
     *
     * @param port    port
     * @param handler 处理器
     */
    private void listen(int port, ChannelInboundHandlerAdapter handler) {

        // 启动监听
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.option(ChannelOption.SO_BACKLOG, 2014);
        b.option(ChannelOption.SO_RCVBUF, 128 * 1024);
        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        b.childOption(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new LoggingHandler(LogLevel.DEBUG));
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 注册handler
                ch.pipeline().addLast(new ByteToMessageDecoderImpl());
                ch.pipeline().addLast(new MessageToByteEncoderImpl());
                ch.pipeline().addLast(handler);
            }
        });

        try {
            ChannelFuture f = b.bind(port).sync();
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    public static class BaseNettyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            // to do something...
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            // to do something...
        }

        @Override
        public void channelRead(ChannelHandlerContext context, Object obj) throws Exception {
            // 解析上行数据
            JT808Message jt808Message = JT808Message.parse((byte[]) obj);
            System.out.println("received a JT808Message : " + jt808Message);

            // 通用应答（演示，实际需根据消息类型回复对应消息）
            Msg8001 body = new Msg8001();
            body.setAckSn(jt808Message.getMessageHead().getMsgSn());
            body.setAckId((short) 1);
            body.setResult((byte) 2);
            byte[] response = JT808Message.build(jt808Message.getMessageHead().getTerminalNumber(), body);
            context.writeAndFlush(response);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext context) throws Exception {

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable thx) throws Exception {

        }
    }
}
