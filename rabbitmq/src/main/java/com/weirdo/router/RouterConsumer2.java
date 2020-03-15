package com.weirdo.router;

import com.rabbitmq.client.*;
import com.weirdo.hello.HelloProducer;
import com.weirdo.utils.ConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/4 13:46
 * publish模式:消费者
 */
public class RouterConsumer2 {

    //定义一个交换机名字
    private  final  static String EXCHANGE_NAME = "EXCHANGE_ROUTER_NAME";

    //队列名字
    private static final String PRODUCER_NAME = "EXCHANGE_ROUTER_RABBIT2";

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

        //绑定队列到交换机  参数3：绑定交换机指定key/标记，只有拥有相同key1，才会接收生产者的消息。
        channel.queueBind(PRODUCER_NAME,EXCHANGE_NAME,"key1");
        //如果接收多个标记只需要在接收一次key
        channel.queueBind(PRODUCER_NAME,EXCHANGE_NAME,"key3");
        //告诉服务器，在我没有确认当前消息时，不要给我新的消息
        channel.basicQos(1);

        /**
         * 先执行  //接收并消费消息 参数2：true自动确认
         * 再执行  //通过回调生成消费者消费
         */
        //通过回调生成消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {

                //获取消息内容然后处理
                String msg = new String(body, "UTF-8");
                //打印获取的消息
                System.out.println("*********** RouterConsumer2" + " get message :[" + msg +"]");

                //手动确认收到消息 参数2 false表示确认收到消息 ，true：拒绝收到消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //注册消费者，参数2  true 表示自动确认收到消息
        channel.basicConsume(PRODUCER_NAME, false, consumer);

    }
}
