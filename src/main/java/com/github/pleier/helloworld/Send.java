package com.github.pleier.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息发送端
 *
 * @author : pleier
 * @date : 2018/6/4
 */
public class Send {
    /**
     * 队列名称
     */
    private final static String QUEUE_NAME = "hello_world";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        //创建链接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //为通道指明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //要发送的消息
        String message = "Hello World!";

        //发布消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        //关闭连接
        channel.close();
        connection.close();
    }
}
