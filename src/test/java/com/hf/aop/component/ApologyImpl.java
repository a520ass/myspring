package com.hf.aop.component;

import org.springframework.stereotype.Component;

@Component
public class ApologyImpl implements Apology {

	@Override
	public void saySorry(String name) {
		 System.out.println("Sorry! " + name);
	}

}
