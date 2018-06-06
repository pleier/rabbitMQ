package com.github.pleier.publishsubscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/6/6
 */
public class Recv2 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{

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

        //声明一个随机名字的队列
        String queueName = channel.queueDeclare().getQueue();

        //绑定队列到路由器上
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        System.out.println(Thread.currentThread()+"  [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println(Thread.currentThread()+"  [x] Received '" + msg + "'");
            }
        };

        channel.basicConsume(queueName,false,consumer);
    }
}
