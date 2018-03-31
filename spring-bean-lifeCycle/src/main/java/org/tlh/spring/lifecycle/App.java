package org.tlh.spring.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tlh.spring.lifecycle.service.LifeCycleService;

@Configuration
public class App {

    @Bean(initMethod = "init",destroyMethod = "myDestroy")
    public LifeCycleService lifeCycleService(){
        LifeCycleService lifeCycleService = new LifeCycleService();
        lifeCycleService.setAge(10);
        return lifeCycleService;
    }

}
