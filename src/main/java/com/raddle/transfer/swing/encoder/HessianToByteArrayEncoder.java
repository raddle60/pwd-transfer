package com.raddle.transfer.swing.encoder;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.caucho.hessian.io.Hessian2Output;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * description: 
 * @author raddle
 * time : 2017年7月7日 下午6:39:58
 */
public class HessianToByteArrayEncoder extends MessageToMessageEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output oout = new Hessian2Output(os);
        try {
            oout.getSerializerFactory().setAllowNonSerializable(true);
            oout.writeObject(msg);
            oout.flush();
        } finally {
            oout.close();
        }
        out.add(os.toByteArray());
    }
}
