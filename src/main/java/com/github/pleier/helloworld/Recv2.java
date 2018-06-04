package com.github.pleier.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 采用多线程的方式接收消息
 *
 * @author : pleier
 * @date : 2018/6/4
 */
public class Recv2 implements Runnable, Consumer {

    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "hello_world";

    private static Channel channel;

    public static void main(String[] args) throws Exception {
        //打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq服务器地址
        factory.setHost("192.168.3.128");

        //设置rabbitmq服务器用户名
        factory.setUsername("plei");

        //设置rabbitmq服务器密码
        factory.setPassword("plei");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Recv2 recv = new Recv2();
        Thread thread = new Thread(recv);
        thread.start();
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(QUEUE_NAME, true, this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    @Override
    public void handleCancelOk(String consumerTag) {

    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

    }

    @Override
    public void handleRecoverOk(String consumerTag) {

    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("Message Number " + new String(body) + " received.");
    }

}
