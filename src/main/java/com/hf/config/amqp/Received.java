package com.hf.config.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class Received implements ChannelAwareMessageListener{

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		byte[] body = message.getBody();
		System.out.println("receive msg : " + new String(body));
		Thread.sleep(1000L);
		channel.basicAck(message.getMessageProperties()
				.getDeliveryTag(), false); // 确认消息成功消费
	}

}
