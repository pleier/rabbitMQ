package com.github.pleier.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author : pleier
 * @date : 2018/6/8
 */
public class Send {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory  = new ConnectionFactory();

        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        //声明路由及路由的类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String routingKey  = "kern.info";
        String message = ".........A kernel info.........";

        //发布消息
        channel.basicPublish(EXCHANGE_NAME, routingKey , null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + routingKey  + "':'" + message + "'");

        channel.close();
        connection.close();
    }
}
