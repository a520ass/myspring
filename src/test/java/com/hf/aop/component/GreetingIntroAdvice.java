package com.hf.aop.component;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

//引入增强
@Component
public class GreetingIntroAdvice extends DelegatingIntroductionInterceptor implements Apology {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.invoke(invocation);
    }
 
    @Override
    public void saySorry(String name) {
        System.out.println("Sorry! " + name);
    }
    
}
