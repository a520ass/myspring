package com.hf.test;


import java.util.ArrayList;
import java.util.Collection;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.hf.config.amqp.RabbitMQConfig;
import com.hf.config.amqp.Send;
import com.hf.config.jms.ActiveMQConfig;
import com.hf.config.jms.RabbitMQJmsConfig;
import com.hf.config.jpa.DataSourceConfig;
import com.hf.config.jpa.JpaConfig;
import com.hf.config.scheduling.SchedulingTaskConfig;
import com.hf.config.transaction.DataSourceTransactionConfig;
import com.hf.entity.Item;
import com.hf.entity.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RabbitMQJmsConfig.class,DataSourceTransactionConfig.class,DataSourceConfig.class,SchedulingTaskConfig.class})
@TestPropertySource("classpath:app.conf")
public class RabbitMQJmsTest {
	

	@Autowired JmsTemplate jmsTemplate;
	
	@Autowired 
	@Qualifier("myDestination")
	Destination myDestination;
	
	@Test
	public void test1() throws InterruptedException{
		try {
			Collection<Item> collection=new ArrayList<Item>();
			collection.add(new Item());
			Order entity=new Order("1","hefeng","document",collection);
			jmsTemplate.convertAndSend(myDestination, entity);
			
			/*jmsTemplate.send(myDestination, new MessageCreator() { 
				 
		        public Message createMessage(Session session) throws JMSException { 
		            ObjectMessage objMessage = session.createObjectMessage(entity); 
		            return objMessage; 
		        } 
		         
		    });*/
			
			Thread.sleep(60*1000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

