package org.tlh.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 原样返回接受到的数据
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 接受到数据时调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);//将数据写回
        ctx.flush();//刷出数据，msg在数据刷出之后自动release掉
    }

    /**
     * 出现异常时调用ø
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //关闭连接
        ctx.close();
    }
}
