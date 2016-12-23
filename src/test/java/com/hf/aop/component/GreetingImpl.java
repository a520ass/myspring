package com.hf.aop.component;

import org.springframework.stereotype.Component;

@Component
public class GreetingImpl implements Greeting {
	 
    @Override
    public void sayHello(String name) {
        System.out.println("Hello! " + name);
        //throw new RuntimeException("Error");
    }
    
}
