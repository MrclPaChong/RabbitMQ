package com.weirdo.example.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weirdo.example.entity.UserLog;
import com.weirdo.example.mapper.UserLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component
public class CommonUserLogMqListener {

    private static final Logger log= LoggerFactory.getLogger(CommonUserLogMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserLogDao userLogMapper;



    /**
     * 监听消费用户日志
     * @param message
     */
    @RabbitListener(queues = "${log.user.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message){
        try {
            UserLog userLog=objectMapper.readValue(message, UserLog.class);
            log.info("监听消费用户日志 监听到消息： {} ",userLog);

            userLogMapper.insertSelective(userLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}






























