package com.raddle.transfer.swing.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * description: 
 * @author raddle
 * time : 2017年8月4日 下午7:03:09
 */
public class IntLengthFieldByteArrayEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
        out.writeInt(msg.length);
        out.writeBytes(msg);
    }

}
