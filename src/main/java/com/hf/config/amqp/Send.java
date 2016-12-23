package com.hf.config.amqp;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

public class Send{

	private RabbitTemplate rabbitTemplate;

	/**
	 * 构造方法注入
	 */
	public Send(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		//确认消息是否到达broker服务器，也就是只确认是否正确到达exchange中即可，只要正确的到达exchange中，broker即可确认该消息返回给客户端ack。
		this.rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
			
			@Override
			public void confirm(CorrelationData correlationData, boolean ack,
					String cause) {
				System.out.print(" 回调id:" + correlationData);
				if (ack) {
					System.out.println("消息成功消费");
				} else {
					System.out.println("消息消费失败:" + cause);
				}
				
			}
		}); // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
		this.rabbitTemplate.setMandatory(true);
		this.rabbitTemplate.setReturnCallback(new ReturnCallback() {
			
			@Override
			public void returnedMessage(Message message, int replyCode,
					String replyText, String exchange, String routingKey) {
				System.out.println("返回消息。。"+message.toString());
			}
		});
	}

	public void sendMsg(String content) {
		CorrelationData correlationId = new CorrelationData(UUID.randomUUID()
				.toString());
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,
				RabbitMQConfig.ROUTINGKEY, content, correlationId);
	}

	

	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

}
