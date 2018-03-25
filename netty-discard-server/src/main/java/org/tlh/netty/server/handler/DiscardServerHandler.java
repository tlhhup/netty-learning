package org.tlh.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 丢弃的Server  只接收请求但不给出响应
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 接受到数据时调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            //do something here
            ByteBuf in =(ByteBuf) msg;
            while (in.isReadable()){
                System.out.print((char)in.readByte());
                System.out.flush();
            }
        }finally {
            //每个handler都需要调用该方法用于释放ByteBuf这个引用计数对象,释放资源
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 出现异常时调用ø
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        //关闭连接
        ctx.close();
    }
}
