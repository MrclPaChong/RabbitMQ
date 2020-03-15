package com.weirdo.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/4 16:24
 */
public class SpringTest {

    public static void main(String[] args) {

        //获取.xml文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //获取.xml中 template模板
        RabbitTemplate template = context.getBean(RabbitTemplate.class);
        //发送消息
        template.convertAndSend("spring 的测试消息");
        //销毁
        ((ClassPathXmlApplicationContext) context).destroy();
    }
}
