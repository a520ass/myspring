package com.hf.config.amqp;

import java.util.concurrent.Executor;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer{
	
	@Autowired Environment environment;
	
	@Autowired SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory;

	public static final String EXCHANGE = "myspring-exchange";
	public static final String ROUTINGKEY = "myspring-routingKey";
	public static final String QUEUENAME = "myspring-queue";

	@Bean
	public CachingConnectionFactory amqpcachingConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(environment.getProperty("mq.host"));
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/admin");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setPublisherConfirms(true);	// 发布确认消息
		connectionFactory.setPublisherReturns(true);	// 发布返回消息
		return connectionFactory;
	}
	
	@Bean
	public MessageConverter amqpMessageConverter(){
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory(
			CachingConnectionFactory cachingConnectionFactory,
			/*@Qualifier("jtaTransactionManager") PlatformTransactionManager transactionManager,*/
			Executor taskExecutor,
			MessageConverter amqpMessageConverter) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(cachingConnectionFactory);
		//factory.setTransactionManager(transactionManager);
		factory.setMessageConverter(amqpMessageConverter);
		factory.setConcurrentConsumers(3);
		factory.setMaxConcurrentConsumers(10);
		factory.setTaskExecutor(taskExecutor);
		//factory.setChannelTransacted(true);
		factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		return factory;
	}
	
	@Override
	public void configureRabbitListeners(
			RabbitListenerEndpointRegistrar registrar) {
		//how to specify an explicit default RabbitListenerContainerFactory 
		registrar.setContainerFactory(myRabbitListenerContainerFactory);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(
			CachingConnectionFactory cachingConnectionFactory) {
		return new RabbitAdmin(cachingConnectionFactory);
	}
	
	@Bean
	public RetryTemplate retryTemplate(){
		RetryTemplate retryTemplate = new RetryTemplate();
	    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
	    backOffPolicy.setInitialInterval(500);
	    backOffPolicy.setMultiplier(10.0);
	    backOffPolicy.setMaxInterval(10000);
	    retryTemplate.setBackOffPolicy(backOffPolicy);
	    return retryTemplate;
	}

	@Bean
	@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate(
			CachingConnectionFactory cachingConnectionFactory, RetryTemplate retryTemplate, MessageConverter amqpMessageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(
				cachingConnectionFactory);
		rabbitTemplate.setMessageConverter(amqpMessageConverter);
		//rabbitTemplate.setMandatory(true);	//发布返回消息，模板的mandatory属性必须被设定为true
	    rabbitTemplate.setRetryTemplate(retryTemplate);
		return rabbitTemplate;
	}

	/**
	 * 针对消费者配置 1. 设置交换机类型 2. 将队列绑定到交换机
	 * 
	 * DirectExchange: 按照routingkey分发到指定队列
	 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
	 * HeadersExchange: 通过添加属性key-value匹配 
	 * TopicExchange: 多关键字匹配
	 */
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	public Queue queueR() {
		return new Queue(QUEUENAME, true); // 队列持久

	}

	@Bean
	public Binding binding(Queue queueR, DirectExchange defaultExchange) {
		return BindingBuilder.bind(queueR).to(defaultExchange)
				.with(ROUTINGKEY);
	}

	/*@Bean
	public SimpleMessageListenerContainer messageListenerContainer(
			CachingConnectionFactory cachingConnectionFactory,Queue queueR) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
				cachingConnectionFactory);
		container.setQueues(queueR);
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new Received());
		return container;
	}*/

	

}
