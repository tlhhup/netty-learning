package org.tlh.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 解决传输过程中分包的问题,及如果收到的数据不是32位的，则说明数据有问题
 */
public class TimeClientHandlerAdvance extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;//暂存数据

    /**
     * 处理器添加是被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.buf = ctx.alloc().buffer(4);//分配4个字节数据空间
    }

    /**
     * 处理器销毁时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.buf.release();//释放资源
        this.buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        //将数据存储到buf中
        buf.writeBytes(m);
        m.release();

        if(buf.readableBytes()>=4) {
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
