package com.raddle.transfer.swing;

import com.raddle.transfer.swing.codec.DispatcherDecoder;
import com.raddle.transfer.swing.decoder.HessianDecoder;
import com.raddle.transfer.swing.encoder.HessianToByteArrayEncoder;
import com.raddle.transfer.swing.encoder.IntLengthFieldByteArrayEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyServer {
    public static final int MAX_FRAME_LENGTH = 1024 * 1024 * 10;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            addLastCommonHandler(ch.pipeline()).addLast(new DispatcherDecoder());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(11222).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static ChannelPipeline addLastCommonHandler(ChannelPipeline channelPipeline) {
        return channelPipeline.addLast(
                new CombinedChannelDuplexHandler<ChannelInboundHandler, ChannelOutboundHandler>(new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, 4, 0, 4), new IntLengthFieldByteArrayEncoder()),
                new CombinedChannelDuplexHandler<ChannelInboundHandler, ChannelOutboundHandler>(new HessianDecoder(), new HessianToByteArrayEncoder()));
    }
}
