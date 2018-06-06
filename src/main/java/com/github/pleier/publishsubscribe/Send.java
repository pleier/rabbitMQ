package com.github.pleier.publishsubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 发布订阅模式
 *
 * @author : pleier
 * @date : 2018/6/6
 */
public class Send {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");

        //创建链接
        Connection connection = factory.newConnection();
        //建立通道
        Channel channel = connection.createChannel();

        //BuiltinExchangeType.FANOUT(所有订阅者都会收到消息)
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String msg = "msg....";

        //发布消息  FANOUT类型的路由 routingKey会被忽略
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + msg + "'");

        //关闭通道和链接
        channel.close();
        connection.close();
    }
}
