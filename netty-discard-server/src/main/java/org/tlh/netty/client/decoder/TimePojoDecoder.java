package org.tlh.netty.client.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.tlh.netty.entity.UnixTime;

import java.util.List;

/**
 * 通过解码处理器将数据解析为实体对象
 */
public class TimePojoDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()<4)//如果数据包小于4说明数据不完整
            return;

        //添加数据到流中，将数据添加到out中标示解码成功
        out.add(new UnixTime(in.readUnsignedInt()));
    }

}
