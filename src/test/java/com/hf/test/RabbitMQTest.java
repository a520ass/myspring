package com.hf.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.hf.config.amqp.RabbitMQConfig;
import com.hf.config.amqp.Send;
import com.hf.config.jpa.JpaConfig;
import com.hf.config.scheduling.SchedulingTaskConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RabbitMQConfig.class,SchedulingTaskConfig.class})
@TestPropertySource({"classpath:rabbitmq-test.conf","classpath:jpa-test.conf"})
public class RabbitMQTest {
	
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired RetryTemplate retryTemplate;
	@Autowired RabbitAdmin rabbitAdmin;
	
	@Autowired
	private GenericApplicationContext applicationContext;
	
	@Test//debug 调试看结果
	public void test() throws InterruptedException{
		for(int i=0;i<5;i++){
			
			Send send =new Send(applicationContext.getBean(RabbitTemplate.class)) ;
			send.sendMsg("send发送的消息。。。 "+i);
			//
			System.out.println(send.getRabbitTemplate().hashCode()+"....."+send.hashCode());
			
		}
		Thread.sleep(50000L);
	}
	
	@Test
	public void test1() throws InterruptedException{
	/*	RetryTemplate retryTemplate = applicationContext.getBean(RetryTemplate.class);
		System.out.println(amqpTemplate.getClass().getName());
		System.err.println(this.retryTemplate==retryTemplate);*/
		/*Exchange exchange = ExchangeBuilder.directExchange(RabbitMQConfig.QUEUENAME).build();
		rabbitAdmin.declareExchange(exchange);
		
		Queue queue = QueueBuilder.durable(RabbitMQConfig.EXCHANGE).build();
		rabbitAdmin.declareQueue(queue);
		
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange)
				.with(RabbitMQConfig.ROUTINGKEY).noargs());*/
		amqpTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,
				RabbitMQConfig.ROUTINGKEY, "測試發送消息");
		Thread.sleep(50000L);
		
	}

}

