package org.tlh.spring.lifecycle.test;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.tlh.spring.lifecycle.beanfactory.MyBeanPostProcessor;
import org.tlh.spring.lifecycle.beanfactory.MyInstantiationAwareBeanPostProcessor;

public class Test {

    @org.junit.Test
    public void lifeCycle() {
        ClassPathResource resource = new ClassPathResource("beans.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);

        //注册后处理器
        beanFactory.addBeanPostProcessor(new MyBeanPostProcessor());
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

        //获取bean
        beanFactory.getBean("lifeCycleService");

        //关闭容器
        beanFactory.destroySingletons();
    }

}
