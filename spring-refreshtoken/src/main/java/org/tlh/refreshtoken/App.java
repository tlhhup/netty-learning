package org.tlh.refreshtoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tlh.refreshtoken.aspectj.RefreshTokenAspect;
import org.tlh.refreshtoken.service.PlatformService;
import org.tlh.refreshtoken.entity.AuthInfo;
import org.tlh.refreshtoken.task.RefreshTokenTask;

import java.util.concurrent.DelayQueue;

@SpringBootApplication
public class App implements CommandLineRunner{

    private static Logger logger= LoggerFactory.getLogger(App.class);

    public static DelayQueue<RefreshTokenTask> tasks=new DelayQueue<>();
    public static AuthInfo authInfo=null;//存储数据

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @Bean
    public RefreshTokenAspect refreshTokenAspect(){
        RefreshTokenAspect refreshTokenAspect=new RefreshTokenAspect();
        refreshTokenAspect.setPlatformService(platformService);
        return refreshTokenAspect;
    }

    @Autowired
    private PlatformService platformService;

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("登录获取Token");
        authInfo = this.platformService.login();
        tasks.add(new RefreshTokenTask(authInfo.getExpiredTime()));
    }

}
