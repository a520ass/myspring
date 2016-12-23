package com.hf.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hf.aop.component.Apology;
import com.hf.aop.component.Greeting;
import com.hf.aop.component.GreetingImpl;

public class Client {
	
	//这里只能使用main函数。系统变量才能生效。
	public static void main(String[] args) {
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		Greeting greeting = JDKDynamicProxy.getProxy(new GreetingImpl());
	     greeting.sayHello("testJdkProxy");
	    // ProxyUtils.generateClassFile(greeting.getClass(), greeting.getClass().getName());
	     //代理后的class大致如这$Proxy0.java
	}
	
	//测试jdk动态代理
	@Test
	public void testJdkProxy(){
		 Greeting greeting = JDKDynamicProxy.getProxy(new GreetingImpl());
	     greeting.sayHello("testJdkProxy");
	}
	
	//cglib动态代理
	@Test
	public void testCglibProxy() throws Exception{
		Greeting greeting = CGLibDynamicProxy.getInstance().getProxy(GreetingImpl.class);
        greeting.sayHello("testCglibProxy");
        
        
      /*  Field h = greeting.getClass().getDeclaredField("CGLIB$CALLBACK_0");  
        h.setAccessible(true);  
        Object dynamicAdvisedInterceptor = h.get(greeting);  
          
        Field[] advised = dynamicAdvisedInterceptor.getClass().getDeclaredFields();
        advised[0].setAccessible(true);
        Object object = advised[0].get(dynamicAdvisedInterceptor);*/
       // String name = object.getClass().getName();
       // advised.setAccessible(true);  
          
        //Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
	}
	
	@Test
	public void testAop4() {
		ApplicationContext context = new ClassPathXmlApplicationContext("aop/spring-context-aoptest.xml"); // 获取 Spring Context
        com.hf.aop.component.Greeting greeting = (com.hf.aop.component.Greeting) context.getBean("greetingProxy");//  从 Context 中根据 id 获取 Bean 对象（其实就是一个代理）
        greeting.sayHello("Jack");                             // 调用代理的方法
    }
	
	//引入增强
	@Test
	public void testAop5() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("aop/spring-context-aoptest.xml"); // 获取 Spring Context
			com.hf.aop.component.GreetingImpl greetingImpl = (com.hf.aop.component.GreetingImpl) context.getBean("greetingProxy"); // 注意：转型为目标类，而并非它的 Greeting 接口
	        greetingImpl.sayHello("Jack");
	 
	        Apology apology = (Apology) greetingImpl; // 将目标类强制向上转型为 Apology 接口（这是引入增强给我们带来的特性，也就是“接口动态实现”功能）
	        apology.saySorry("Jack");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	//引入增强
	@Test
	public void testAop6() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("aop/spring-context-aoptest.xml"); // 获取 Spring Context
			com.hf.aop.component.GreetingImpl greetingImpl = (com.hf.aop.component.GreetingImpl) context.getBean("greetingImpl");
	        greetingImpl.sayHello("Jack");
	 
	        Apology apology = (Apology) greetingImpl;
	        apology.saySorry("Jack");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
}
