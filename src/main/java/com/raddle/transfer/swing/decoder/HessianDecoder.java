package com.raddle.transfer.swing.decoder;

import java.util.List;

import com.caucho.hessian.io.Hessian2Input;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class HessianDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Hessian2Input ois = new Hessian2Input(new ByteBufInputStream(in, true));
        try {
            ois.getSerializerFactory().setAllowNonSerializable(true);
            out.add(ois.readObject());
        } finally {
            ois.close();
        }
    }
}
