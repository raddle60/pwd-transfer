package com.raddle.transfer.swing;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static final int MAX_FRAME_LENGTH = 1024 * 1024 * 10;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); //1
            b.group(group) //2
                    .channel(NioSocketChannel.class) //3
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 11222)) //4
                    .handler(new ChannelInitializer<SocketChannel>() { //5
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            NettyServer.addLastCommonHandler(ch.pipeline());
                        }
                    });

            ChannelFuture f = b.connect().sync(); //6
            f.channel().writeAndFlush("sdfsdfdsfdsfs");
            f.channel().closeFuture().sync(); //7
        } finally {
            group.shutdownGracefully().sync(); //8
        }
    }

}
