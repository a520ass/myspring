package com.hf.config.jms;

import java.util.concurrent.Executor;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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

import javax.jms.DeliveryMode;
import javax.jms.Session;

//@Configuration
//@EnableJms
//@ComponentScan("spittr.config.jms")
public class ActiveMQConfig implements JmsListenerConfigurer {
	
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
			MessageConverter jmsmessageConverter, Executor taskExecutor) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(cachingConnectionFactory);
		factory.setDestinationResolver(destinationResolver);
		factory.setMessageConverter(jmsmessageConverter);
		factory.setTaskExecutor(taskExecutor);
		return factory;
	}
	
	/**
	 * 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供
	 * @return
	 */
	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory(){
		ActiveMQConnectionFactory acf=new ActiveMQConnectionFactory();
		acf.setBrokerURL("tcp://10.10.3.118:61617");
		return acf;
	}
	
	/*@Bean
	public PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
		PooledConnectionFactory pcf=new PooledConnectionFactory();
		pcf.setConnectionFactory(activeMQConnectionFactory);
		pcf.setMaxConnections(10);
		return pcf;
	}*/
	
	/**
	 * Spring用于管理真正的ConnectionFactory的ConnectionFactory
	 * @param pooledConnectionFactory
	 * @return
	 */
	@Bean
	public CachingConnectionFactory jmscachingConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
		CachingConnectionFactory ccf=new CachingConnectionFactory();
		/**
		 * 目标ConnectionFactory对应真实的可以产生JMS
		 */
		ccf.setTargetConnectionFactory(activeMQConnectionFactory);
		return ccf;
	}
	
	//jta jms start
	/*@Bean
	public ActiveMQXAConnectionFactory jmsXaConnectionFactory(){
		ActiveMQXAConnectionFactory axcf=new ActiveMQXAConnectionFactory();
		axcf.setBrokerURL("tcp://10.10.3.118:61616");
		return axcf;
	}
	
	@Bean(initMethod="init",destroyMethod="close")
	public AtomikosConnectionFactoryBean amqConnectionFactory(XAConnectionFactory xaConnectionFactory){
		AtomikosConnectionFactoryBean acfb=new AtomikosConnectionFactoryBean();
		acfb.setUniqueResourceName("XAactiveMQ");
		acfb.setXaConnectionFactory(xaConnectionFactory);
		acfb.setPoolSize(20);
		return acfb;
	}*/
	//jta jms end
	
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
		jmsTemplate.setPubSubDomain(false);			//pubSubDomain "true" for the Publish/Subscribe domain (Topics), "false" for the Point-to-Point domain (Queues)
		jmsTemplate.setExplicitQosEnabled(true);	//deliveryMode, priority, timeToLive 的开关，要生效，必须配置为true，默认false
		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);				//发送模式  DeliveryMode.NON_PERSISTENT=1:非持久 ; DeliveryMode.PERSISTENT=2:持久
		/**	消息应答方式  
		 * 	Session.AUTO_ACKNOWLEDGE  消息自动签收  
	        Session.CLIENT_ACKNOWLEDGE  客户端调用acknowledge方法手动签收  
	        Session.DUPS_OK_ACKNOWLEDGE 不必必须签收，消息可能会重复发送 
		 */
		jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);


		return jmsTemplate;
	}
	
	@Bean
	public ActiveMQQueue myDestination(){
		ActiveMQQueue queue=new ActiveMQQueue("myDestination");
		return queue;
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
