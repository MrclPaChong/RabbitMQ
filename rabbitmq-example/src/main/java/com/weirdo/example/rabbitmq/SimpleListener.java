package com.weirdo.example.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.weirdo.example.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/29.
 * ChannelAwareMessageListener手动触发确认机制
 * MessageListener 自动重发
 */
@Component("simpleListener")
public class SimpleListener implements ChannelAwareMessageListener {

    private static final Logger log= LoggerFactory.getLogger(SimpleListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag=message.getMessageProperties().getDeliveryTag();

        try {
            byte[] msg=message.getBody();
            User user=objectMapper.readValue(msg,User.class);
            log.info("简单消息监听确认机制监听到消息： {} ",user);

            //int i=1/0;

            //手动确认  不然一直在rabbitMQ 队列中
            channel.basicAck(tag,true);
        }catch (Exception e){
            log.error("简单消息监听确认机制发生异常：",e.fillInStackTrace());

            channel.basicReject(tag,false);
        }
    }
}
































