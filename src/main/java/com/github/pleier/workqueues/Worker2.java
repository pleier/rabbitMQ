package com.github.pleier.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 工作队列2
 *
 * @author : pleier
 * @date : 2018/6/6
 */
public class Worker2 {

    private static final String QUEUE_NAME = "work_queues";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");

        //建立链接
        Connection connection  = factory.newConnection();

        //建立通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);

        new BasicConsumer().access(QUEUE_NAME,channel);

    }
}
