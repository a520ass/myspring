package com.hf.aop;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLibDynamicProxy implements MethodInterceptor {

	private static CGLibDynamicProxy instance = new CGLibDynamicProxy();

	private CGLibDynamicProxy() {
	}

	public static CGLibDynamicProxy getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> cls) {
		return (T) Enhancer.create(cls, this);
	}

	@Override
	public Object intercept(Object target, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		before();
		// 执行原有逻辑，注意这里是invokeSuper
		Object result = proxy.invokeSuper(target, args);
		after();
		return result;
	}

	private void before() {
		System.out.println("Before");
	}

	private void after() {
		System.out.println("After");
	}

}
