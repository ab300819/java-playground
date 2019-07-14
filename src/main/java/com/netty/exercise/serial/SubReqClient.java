package com.netty.exercise.serial;

import com.netty.exercise.decode.TimeClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SubReqClient {
    private static final Logger log = LoggerFactory.getLogger(TimeClient.class);

    public static void main(String[] args) throws Exception {
        new TimeClient().connect(9090, "localhost");
    }


    public void connect(int port, String host) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClient.TimeClientHandler());
                        }

                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        private static final Logger log = LoggerFactory.getLogger(TimeClient.TimeClientHandler.class);


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            try {
                long currentMills = msg.readUnsignedInt() * 1000L;
                log.info("server time {}", new Date(currentMills));
                ctx.close();
            } finally {
                msg.release();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
