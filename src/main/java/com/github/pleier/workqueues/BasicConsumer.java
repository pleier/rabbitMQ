package com.github.pleier.workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author : pleier
 * @date : 2018/6/6
 */
public class BasicConsumer {

    /**
     * 接收消息
     *
     * @param channel
     */
    public void access(String queueName,final Channel channel) throws IOException{
        //回调消费消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] Received '" + message + "'");
                try {
                   doWork(message);
                    /**
                     * deliveryTag:该消息的index
                     * multiple：是否批量.（true:将一次性ack所有小于deliveryTag的消息）
                     */
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[X] done");
                }
            }
        };
        //是否自动应答
        channel.basicConsume(queueName, false, consumer);
    }

    private void doWork(String message) throws InterruptedException {
        for (char ch : message.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
