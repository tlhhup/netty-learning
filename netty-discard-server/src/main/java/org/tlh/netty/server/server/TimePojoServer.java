package org.tlh.netty.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.tlh.netty.server.encoder.TimePojoEncoder;
import org.tlh.netty.server.handler.TimePojoServerHandler;

public class TimePojoServer {

    private int port;

    public TimePojoServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //用于接收客户端的连接，主线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();//工作线程，一旦和服务端取得连接后，该连接就转到该工作线程
        try {
            ServerBootstrap b = new ServerBootstrap(); // 工具类用于配置和启动服务端
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 设置通道的类型
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 通过ChannelInitializer来初始化通道，并在ChannelPipeline中注册处理器
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimePojoEncoder(),new TimePojoServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // 设置通道参数
                    .childOption(ChannelOption.SO_KEEPALIVE, true); //

            ChannelFuture f = b.bind(port).sync(); //

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new TimePojoServer(port).run();
    }
}
