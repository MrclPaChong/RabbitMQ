package com.weirdo.example.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.weirdo.example.dto.UserOrderDto;
import com.weirdo.example.entity.UserOrder;
import com.weirdo.example.mapper.UserOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener {

    private static final Logger log= LoggerFactory.getLogger(UserOrderListener.class);

    //json框架解析数据源
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserOrderDao userOrderMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag=message.getMessageProperties().getDeliveryTag();
        try {
            //接收消息
            byte[] body=message.getBody();
            //反序列化
            UserOrderDto entity=objectMapper.readValue(body, UserOrderDto.class);
            log.info("用户商城下单监听到消息： {} ",entity);

            UserOrder userOrder=new UserOrder();
            //给对象赋值  相当于set
            BeanUtils.copyProperties(entity,userOrder);
            userOrder.setStatus(1);
            userOrderMapper.insertSelective(userOrder);

            channel.basicAck(tag,true);
        }catch (Exception e){
            log.error("用户商城下单 发生异常：",e.fillInStackTrace());
            channel.basicReject(tag,false);
        }
    }
}




























