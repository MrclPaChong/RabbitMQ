package com.weirdo.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weirdo.utils.ConnectionUtils;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/3 17:00
 *                              --队列    --消费者
 * publish模式 生产者 --交换机
 *                              --队列    --消费者
 */
public class PubLishProducer {

    //定义一个交换机名字
    private  final  static String EXCHANGE_NAME = "EXCHANGE_NAME";

    public static void main(String[] args) throws Exception{

        //建立连接 RabbitMQ
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明交换机 类型fanout即发布订阅模式
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //发布订阅模式，因为消息先到交换机里，而交换机没有保存功能，如果没有消费者，消息会丢失。
        channel.basicPublish(EXCHANGE_NAME,"",null,"发布订阅模式".getBytes());
        //关闭连接
        channel.close();
        connection.close();
    }
}
