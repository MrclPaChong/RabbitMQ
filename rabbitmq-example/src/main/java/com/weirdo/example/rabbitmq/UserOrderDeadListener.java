package com.weirdo.example.rabbitmq;

import com.weirdo.example.entity.UserOrder;
import com.weirdo.example.mapper.ProductMapper;
import com.weirdo.example.mapper.UserOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/15 15:03
 * 死信队列监听
 */
@Component
public class UserOrderDeadListener {

    private static final Logger log= LoggerFactory.getLogger(LogSystemListener.class);


    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private ProductMapper productMapper;
    /**
     * 监听消费死信队列中的消息
     * @param id
     */

    @RabbitListener(queues = "${user.order.dead.real.queue.name}",containerFactory = "multiListenerContainer")
    public void consumeMessage(@Payload Integer id){
        try {
            log.info("死信队列-用户下单超时未支付监听消息： {} ",id);

            UserOrder entity=userOrderDao.selectByPkAndStatus(id,1);
            if (entity!=null){
                entity.setStatus(3);
                entity.setUpdateTime(new Date());
                //失效超时未未付款的用户订单记录
                userOrderDao.update(entity);
            }else{
                //TODO：已支付-可能需要异步 减库存-异步发送其他日志消息

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 死信队列-动态TTL-用户下单
     * @param id
     */
    @RabbitListener(queues = "${dynamic.dead.real.queue.name}",containerFactory = "multiListenerContainer")
    public void consumeMessageDynamic(@Payload Integer id){
        try {
            log.info("死信队列-动态TTL-用户下单超时未支付监听消息： {} ",id);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
