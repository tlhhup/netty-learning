package org.tlh.netty.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.tlh.netty.client.handler.TimeClientHandler;

public class TimeClient {

    public static void main(String[] args) throws Exception{
        int port=8080;
        if(args.length>0){
            port=Integer.parseInt(args[0]);
        }

        EventLoopGroup workGroup=null;
        try {
            //创建客户端的工作线程
            workGroup = new NioEventLoopGroup();
            //创建客户端对象
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(workGroup)//
                    .channel(NioSocketChannel.class)//
                    .option(ChannelOption.SO_KEEPALIVE,true)//
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //启动客户端
            ChannelFuture future=bootstrap.connect("localhost",port);

            future.channel().closeFuture().sync();
        }finally {
            workGroup.shutdownGracefully();
        }
    }

}
