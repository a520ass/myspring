package com.hf.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * Jdk原生的动态代理只能实现对接口的代理
 * @author 520
 *
 */
public class JDKDynamicProxy implements InvocationHandler{
	
	private Object target;
	
	public JDKDynamicProxy(Object target){
		this.target=target;
	}
	
	// 生成一个目标对象的代理对象
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(T t){
		//此处类加载器为sun.misc.Launcher$AppClassLoader
		return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), new JDKDynamicProxy(t));
	}
	
	// 在执行目标对象方法前后加上自己的拦截逻辑
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		before();
		Object result=method.invoke(target, args);
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
