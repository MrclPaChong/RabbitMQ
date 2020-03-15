package com.weirdo.work;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.weirdo.utils.ConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/3 10:18
 * Hello版本：生产者
 */
public class WorkProducer {


    //队列名字
    private static final String PRODUCER_NAME = "WORK_RABBITMQ";

    public static void main(String[] args) throws Exception{

        //引入日志
        Logger log=LoggerFactory.getLogger(WorkProducer.class);

        //获取连接
        Connection connection =ConnectionUtils.getConnection();
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

       for (int i= 0;i<=100;i++){

           //发送内容
           channel.basicPublish("",PRODUCER_NAME,null,("WORK模式：1个生产者2个消费者"+i).getBytes());

           //打印日志
           log.info(i+"--------------------时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }


        //关闭连接
        channel.close();
        connection.close();
    }

}
