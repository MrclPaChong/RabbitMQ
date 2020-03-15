package com.weirdo.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weirdo.example.response.BaseResponse;
import com.weirdo.example.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * Created by Administrator on 2018/8/23.
 */
@RestController
public class RabbitController {

    private static final Logger log= LoggerFactory.getLogger(RabbitController.class);

    private static final String Prefix="rabbit";

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 发送简单消息
     * @param message
     * @return
     */
    @RequestMapping(value = Prefix+"/simple/message/send",method = RequestMethod.GET)
    public BaseResponse sendSimpleMessage(@RequestParam String message){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("待发送的消息： {} ",message);

            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("basic.info.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("basic.info.mq.routing.key.name"));

            //Message msg=MessageBuilder.withBody(message.getBytes("UTF-8")).build();
            //rabbitTemplate.send(msg);

            Message msg= MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).build();
            /*rabbitTemplate.send(msg);*/
            rabbitTemplate.convertAndSend(msg);

        }catch (Exception e){
            log.error("发送简单消息发生异常： ",e.fillInStackTrace());
        }
        return response;
    }



}





























