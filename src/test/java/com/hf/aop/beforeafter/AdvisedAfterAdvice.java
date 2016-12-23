package com.hf.aop.beforeafter;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

/**
 * 基于aop接口的方式
 * @author 520
 *
 */
//此种方式的通知。适合使用proxybean方式。见xml配置文件
@Component
public class AdvisedAfterAdvice implements AfterReturningAdvice {
	 
    @Override
    public void afterReturning(Object result, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("After");
    }
}
