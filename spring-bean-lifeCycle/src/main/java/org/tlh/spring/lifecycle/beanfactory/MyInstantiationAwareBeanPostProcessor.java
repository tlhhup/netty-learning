package org.tlh.spring.lifecycle.beanfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.tlh.spring.lifecycle.service.LifeCycleService;

import java.beans.PropertyDescriptor;

public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter{

    private static final Logger LOGGER= LoggerFactory.getLogger(MyInstantiationAwareBeanPostProcessor.class);

    /** 在实例化bean之前调用
     * @param beanClass
     * @param beanName
     * @return 替换掉原始创建的对象，如果返回为null则不替换
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(beanClass== LifeCycleService.class){
            LOGGER.debug("MyInstantiationAwareBeanPostProcessor----->postProcessBeforeInstantiation");
        }
        return null;
    }

    /** 在bean实例话后调用
     * @param bean
     * @param beanName
     * @return 放回为true，表示需要给该bean设置属性，否则直接跳过
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        LOGGER.debug("MyInstantiationAwareBeanPostProcessor----->postProcessAfterInstantiation--->"+beanName);
        return true;
    }

    /** 设置属性时调用,在调用setXXX方法之前调用，该方法可以用于解析属性值，如解码
     * @param pvs
     * @param pds
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        LOGGER.debug("MyInstantiationAwareBeanPostProcessor----->postProcessPropertyValues--->"+beanName);
        return pvs;
    }
}
