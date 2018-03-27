package org.tlh.netty;

import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tlh.netty.server.EchoServer;

@SpringBootApplication
public class App implements CommandLineRunner{

    private Logger logger= LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @Autowired
    private EchoServer echoServer;

    @Override
    public void run(String... strings) throws Exception {
        ChannelFuture channelFuture = echoServer.start();
        channelFuture.channel().closeFuture().sync();

        //虚拟机关闭是调用
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            logger.debug("关闭Netty");
            echoServer.destroy();
        }));
    }
}
