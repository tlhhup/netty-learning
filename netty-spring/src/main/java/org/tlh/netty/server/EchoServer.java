package org.tlh.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tlh.netty.handler.EchoServerHandler;

@Component
public class EchoServer {

    @Value("${netty.port:8889}")
    private int port;

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();

    public ChannelFuture start() throws Exception{
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)//
                .channel(NioServerSocketChannel.class)//
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                })//
                .option(ChannelOption.SO_BACKLOG,128)//
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture future = bootstrap.bind(port).sync();
        return future;
    }

    public void destroy() {
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
