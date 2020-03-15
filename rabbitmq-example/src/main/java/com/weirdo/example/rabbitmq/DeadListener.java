package com.weirdo.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/15 15:03
 * 死信队列监听
 */
@Component
public class DeadListener {

    private static final Logger log= LoggerFactory.getLogger(LogSystemListener.class);


    /**
     * 监听消费死信队列中的消息
     * @param message
     */
    @RabbitListener(queues = "${simple.dead.real.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeDeadQueue(@Payload byte[] message){
        try {
            log.info("监听消费死信队列中的消息： {} ",new String(message,"UTF-8"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
