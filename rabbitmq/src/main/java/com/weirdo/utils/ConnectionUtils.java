package com.weirdo.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author weirdo
 * @version 1.0
 * @date 2020/3/3 10:41
 * RabbirMQ连接工具类
 */
public class ConnectionUtils {


    public static Connection getConnection() throws Exception{
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机IP地址
        connectionFactory.setHost("127.0.0.1");
        //设置服务器端口 网页端为：15672
        connectionFactory.setPort(5672);
        //设置用户名
        connectionFactory.setUsername("test");
        //设置用户密码
        connectionFactory.setPassword("test");
        //设置虚拟主机名称
        connectionFactory.setVirtualHost("/test");
        //创建一个新的连接
        return  connectionFactory.newConnection();
    }
}
