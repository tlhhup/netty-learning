package org.tlh.spring.lifecycle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class LifeCycleService implements BeanFactoryAware,BeanNameAware,InitializingBean,DisposableBean{

    private static final Logger LOGGER= LoggerFactory.getLogger(LifeCycleService.class);

    private int age;
    private String name;

    public void setAge(int age) {
        LOGGER.debug("LifeCycleService----->setAge");
        this.age = age;
    }

    public void setName(String name) {
        LOGGER.debug("LifeCycleService----->setName");
        this.name = name;
    }

    public LifeCycleService() {
        LOGGER.debug("LifeCycleService---->实例话对象");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        LOGGER.debug("LifeCycleService---->setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        LOGGER.debug("LifeCycleService---->setBeanName");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.debug("LifeCycleService---->afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        LOGGER.debug("LifeCycleService---->destroy");
    }

    public void init(){
        LOGGER.debug("LifeCycleService---->init");
    }

    public void myDestroy(){
        LOGGER.debug("LifeCycleService---->myDestroy");
    }

}
