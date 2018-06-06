package com.github.pleier.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 工作队列1
 *
 * @author : pleier
 * @date : 2018/6/6
 */
public class Worker1 {
    private static final String QUEUE_NAME = "work_queues";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.128");
        factory.setUsername("plei");
        factory.setPassword("plei");
        //建立链接
        Connection connection = factory.newConnection();

        //建立通道
        Channel channel = connection.createChannel();

        //为通道指明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //一次接收多少条消息,不指时是表示一次接收完所有消息
        channel.basicQos(1);

        new BasicConsumer().access(QUEUE_NAME, channel);
    }
}
