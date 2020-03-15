package com.weirdo.work;

import com.rabbitmq.client.*;
import com.weirdo.hello.HelloProducer;
import com.weirdo.utils.ConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/3 11:21
 * Hello版本：消费者
 */
public class WorkConsumer1 {

    //队列名字
    private static final String PRODUCER_NAME = "WORK_RABBITMQ";

    public static void main(String[] args) throws Exception{


        //引入日志
        Logger log= LoggerFactory.getLogger(HelloProducer.class);

        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列,存在什么都不做，不存在创建。
        /**
         * 1.第一个参数队列的名称
         * 2.第二个参数是否持久化队列：设置flase默认在内存中，重启会丢失。设置true会保存到Erlang中,重启会从Erlang取出。
         * 3.第三个参数是否排除在外：设置flase默认关闭连接自动删除队列。设置true私有化，其他通道不可访问，适用于一个消费者时。
         * 4.第四个参数：是否自动删除
         * 5.其他参数
         */
        channel.queueDeclare(PRODUCER_NAME,false,false,false,null);

        /**
         * 先执行  //接收并消费消息 参数2：true自动确认
         * 再执行  //通过回调生成消费者消费
         */
        //通过回调生成消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {

                //获取消息内容然后处理
                String msg = new String(body, "UTF-8");
                //打印获取的消息
                System.out.println("*********** WorkConsumer1" + " get message :[" + msg +"]");

                try {
                    //模拟时延 耗时
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //手动确认收到消息 参数2 false表示确认收到消息 ，true：拒绝收到消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };


            //注册消费者，参数2  false手动确认，代表我们收到消息后需要手动告诉服务器，我们收到了。
           channel.basicConsume(PRODUCER_NAME, false, consumer);


    }
}
