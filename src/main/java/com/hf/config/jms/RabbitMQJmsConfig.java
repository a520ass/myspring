package com.hf.config.jms;

import java.util.concurrent.Executor;

import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.transaction.PlatformTransactionManager;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

@Configuration
@EnableJms
public class RabbitMQJmsConfig implements JmsListenerConfigurer {
	
	@Autowired DefaultJmsListenerContainerFactory myJmsListenerContainerFactory;
	
	@Override
	public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
		registrar.setContainerFactory(myJmsListenerContainerFactory);
	}
	
	@Bean
	public DestinationResolver destinationResolver(){
		DynamicDestinationResolver destinationResolver=new DynamicDestinationResolver();
		return destinationResolver;
	}
	
	@Bean
	public DefaultJmsListenerContainerFactory myJmsListenerContainerFactory(
			CachingConnectionFactory cachingConnectionFactory,
			DestinationResolver destinationResolver,
			MessageConverter jmsmessageConverter, Executor taskExecutor,
			PlatformTransactionManager transactionManager) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(cachingConnectionFactory);
		factory.setDestinationResolver(destinationResolver);
		factory.setMessageConverter(jmsmessageConverter);
		factory.setTaskExecutor(taskExecutor);
		factory.setSessionTransacted(true);
		factory.setTransactionManager(transactionManager);
		return factory;
	}
	
	/**
	 * 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供
	 * @return
	 * @throws JMSException 
	 */
	@Bean
	public RMQConnectionFactory rMQConnectionFactory() throws JMSException{
		RMQConnectionFactory rcf=new RMQConnectionFactory();
		rcf.setHost("purelybeibei.com");
		rcf.setUsername("admin");
		rcf.setPassword("admin");
		rcf.setVirtualHost("/admin");
		return rcf;
	}
	
	
	/**
	 * Spring用于管理真正的ConnectionFactory的ConnectionFactory
	 * @param pooledConnectionFactory
	 * @return
	 */
	@Bean
	public CachingConnectionFactory jmscachingConnectionFactory(RMQConnectionFactory rMQConnectionFactory){
		CachingConnectionFactory ccf=new CachingConnectionFactory();
		/**
		 * 目标ConnectionFactory对应真实的可以产生JMS
		 */
		ccf.setTargetConnectionFactory(rMQConnectionFactory);
		return ccf;
	}
	
	@Bean
	public MessageConverter jmsmessageConverter(){
		MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
		messageConverter.setTypeIdPropertyName("id");
		return messageConverter;
	}
	
	/**
	 * Spring提供的JMS工具类，它可以进行消息发送、接收等
	 * @param connectionFactory
	 * @param messageConverter 
	 * @return
	 */
	@Bean
	public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory, MessageConverter jmsmessageConverter){
		JmsTemplate jmsTemplate=new JmsTemplate();
		jmsTemplate.setConnectionFactory(cachingConnectionFactory);
		jmsTemplate.setReceiveTimeout(2000);
		//jmsTemplate.setSessionTransacted(true);
		jmsTemplate.setMessageConverter(jmsmessageConverter);
		//jmsTemplate.setPubSubDomain(false);			//pubSubDomain "true" for the Publish/Subscribe domain (Topics), "false" for the Point-to-Point domain (Queues)
		//jmsTemplate.setExplicitQosEnabled(true);	//deliveryMode, priority, timeToLive 的开关，要生效，必须配置为true，默认false
		//jmsTemplate.setDeliveryMode(2);				//发送模式  DeliveryMode.NON_PERSISTENT=1:非持久 ; DeliveryMode.PERSISTENT=2:持久
		/**	消息应答方式  
		 * 	Session.AUTO_ACKNOWLEDGE  消息自动签收  
	        Session.CLIENT_ACKNOWLEDGE  客户端调用acknowledge方法手动签收  
	        Session.DUPS_OK_ACKNOWLEDGE 不必必须签收，消息可能会重复发送 
		 */
		jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        
		return jmsTemplate;
	}
	
	@Bean
	public RMQDestination myDestination(){
		RMQDestination myDestination=new RMQDestination("myDestination",true,false);
		return myDestination;
	}
	
	@Bean
	public MyService myService(){
		return new MyService();
	}
	/*
	@Bean
	public ConsumerMessageListener consumerMessageListener(){
		return new ConsumerMessageListener();
	}
	
	@Bean
	public DefaultMessageListenerContainer queueContainer(@Qualifier("amqConnectionFactory")ConnectionFactory connectionFactory, ActiveMQQueue queue, ConsumerMessageListener consumerMessageListener,@Qualifier("jtaTransactionManager") PlatformTransactionManager transactionManager){
		DefaultMessageListenerContainer container=new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setTransactionManager(transactionManager);
		container.setDestination(queue);
		container.setMessageListener(consumerMessageListener);
		return container;
	}*/

	
}
