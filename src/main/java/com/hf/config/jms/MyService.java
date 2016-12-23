package com.hf.config.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.hf.entity.Order;

@Component
public class MyService {
	
	@JmsListener(destination = "myDestination")
    public void processOrder(Message message) {
		if (message instanceof ObjectMessage) {  
            ObjectMessage objMessage = (ObjectMessage) message;  
            try {  
                Object obj = objMessage.getObject();  
                Order order = (Order) obj;  
                System.out.println("接收到一个ObjectMessage，包含Order对象。");  
                System.out.println(order);  
            } catch (JMSException e) {  
                e.printStackTrace();  
            }  
        } 
        //System.out.println(order.toString());
    }
	
	@JmsListener(destination = "myDestination")
    public void processOrder(Order order) {
			System.out.println("接收到一个Object，为Order对象。");  
			System.out.println(order);  
        //System.out.println(order.toString());
    }
}
