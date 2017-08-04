package com.raddle.transfer.swing.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * description: 
 * @author raddle
 * time : 2017年8月4日 下午7:22:26
 */
public class DispatcherDecoder extends MessageToMessageDecoder<Object> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        // TODO Auto-generated method stub
        System.out.println(this.getClass().getName() + msg + "");
    }

}
