package com.weirdo.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.weirdo.example.dto.LogDto;
import com.weirdo.example.dto.UserOrderDto;
import com.weirdo.example.entity.UserOrder;
import com.weirdo.example.mapper.UserOrderDao;
import com.weirdo.example.response.BaseResponse;
import com.weirdo.example.response.StatusCode;
import com.weirdo.example.service.CommonLogService;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户商城下单controller
 * Created by Administrator on 2018/8/30.
 */
@RestController
public class UserOrderController {

    private static final Logger log= LoggerFactory.getLogger(UserOrderController.class);

    private static final String Prefix="user/order";

    //rabbit发送消息组件
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //反序列化
    @Autowired
    private ObjectMapper objectMapper;

    //spring缓存变量  主要为了获取application.properties 配置中的值
    @Autowired
    private Environment env;

    @Autowired
    private CommonLogService logService;

    @Autowired
    private UserOrderDao userOrderDao;



    /**
     * 用户商城下单
     * @param dto
     * @return
     */
    @RequestMapping(value = Prefix+"/push",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse pushUserOrder(@RequestBody UserOrderDto dto) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
        log.info("接收到数据：{}",dto);

            //TODO：用户下单记录-入库
            //转换为json
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("user.order.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("user.order.routing.key.name"));
            //发送消息
            Message msg=
                    MessageBuilder.withBody(objectMapper.writeValueAsBytes(dto)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            rabbitTemplate.convertAndSend(msg);



        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //TODO：系统级别-日志
            //异步方式,和用户下单入库走不同线程
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("log.system.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("log.system.routing.key.name"));

            //创建日志对象
            LogDto logDto=new LogDto("pushUserOrder",objectMapper.writeValueAsString(dto));
            rabbitTemplate.convertAndSend(logDto, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties=message.getMessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,LogDto.class);
                    return message;
                }
            });

            //TODO：还有很多业务逻辑...
            log.info("还可以配置多个业务逻辑，走不同线程，互不影响。");
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }



    /**
     * 死信队列：用户商城下单
     * @param dto
     * @return
     */
    @RequestMapping(value = Prefix+"/push/dead/queue",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse pushUserOrderV2(@RequestBody UserOrderDto dto){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        UserOrder userOrder=new UserOrder();
        try {
            BeanUtils.copyProperties(dto,userOrder);
            userOrder.setStatus(1);
            userOrderDao.insertSelective(userOrder);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Integer id=userOrder.getId();

            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("user.order.dead.produce.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("user.order.dead.produce.routing.key.name"));

            rabbitTemplate.convertAndSend(id, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties=message.getMessageProperties();
                    //消息持久化
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    //设置Header的ID：此处为用户ID
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Integer.class);
                    return message;
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }


    /**
     * 用户商城下单-动态TTL设置
     * @param dto
     * @return
     */
    @RequestMapping(value = Prefix+"/push/dead/queue/v3",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse pushUserOrderV3(@RequestBody UserOrderDto dto){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        UserOrder userOrder=new UserOrder();
        try {
            BeanUtils.copyProperties(dto,userOrder);
            userOrder.setStatus(1);
            userOrderDao.insertSelective(userOrder);
            log.info("用户商城下单成功!!");
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Integer id=userOrder.getId();

            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("dynamic.dead.produce.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("dynamic.dead.produce.routing.key.name"));

            //1.固定时间
            Long ttl=15000L; //可以用随机数替代
            //2.可以使用随机数生成时间
            rabbitTemplate.convertAndSend(id, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties=message.getMessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Integer.class);

                    properties.setExpiration(String.valueOf(ttl));
                    return message;
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }


}
















