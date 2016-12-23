package com.hf.aop.beforeafter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTestBeforeAfter {
	
	public static void main(String[] args) {
		ApplicationContext ctx =   
	            new ClassPathXmlApplicationContext("/aop/aoptest-beforeafter.xml");  
	      
	    AdvisedObj advisedObj =ctx.getBean("advised",AdvisedObj.class);  
	    advisedObj.perform();
	   // ctx.
	    Object advisedByproxybean =ctx.getBean("advisedByproxybean");
	    advisedByproxybean.getClass();
	}
	
	
}
