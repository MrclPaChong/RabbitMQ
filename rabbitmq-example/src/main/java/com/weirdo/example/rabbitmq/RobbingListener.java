package com.weirdo.example.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.weirdo.example.service.ConcurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


/**
 * Created by Administrator on 2018/8/23.
 */
@Component
public class RobbingListener {

    private static final Logger log= LoggerFactory.getLogger(RobbingListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConcurrencyService concurrencyService;


    /**
     * 监听抢单消息
     * @param message
     */
    @RabbitListener(queues = "${product.robbing.mq.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMessage(@Payload String message){
        try {

            log.info("监听到抢单手机号： {} ",message);

            concurrencyService.manageRobbing(String.valueOf(message));
        }catch (Exception e){
            log.error("监听抢单消息 发生异常： ",e.fillInStackTrace());
        }
    }

}

















