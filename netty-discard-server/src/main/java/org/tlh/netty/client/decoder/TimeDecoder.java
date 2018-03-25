package org.tlh.netty.client.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 通过解码处理器解决拆包的问题
 */
public class TimeDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()<4)//如果数据包小于4说明数据不完整
            return;

        //添加数据到流中
        out.add(in.readBytes(4));//读取4个字节的数据
    }

}
