package com.cream.warriorLegends.game.msg.base;

import com.cream.warriorLegends.game.net.MsgDispatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author cream
 * Email:837800910@qq.com
 * 2025/1/29 22:31
 */
@Component
public class MsgProcessorRegister implements BeanPostProcessor {

    private final MsgDispatcher msgDispatcher;

    @Autowired
    public MsgProcessorRegister(MsgDispatcher msgDispatcher) {
        this.msgDispatcher = msgDispatcher;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MsgProcessor<?>) {
            this.msgDispatcher.registry((MsgProcessor<?>) bean);
        }
        return bean;
    }
}
