package com.hf.config.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class ReceviedAnnotation {
	
	@RabbitListener(queues={RabbitMQConfig.QUEUENAME})
	public void receviedMessage(Message message, Channel channel){
		byte[] body = message.getBody();
		System.out.println("receive msg : " + new String(body));
		//return message;
	}
}
