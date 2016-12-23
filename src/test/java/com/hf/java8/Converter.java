package com.hf.java8;

/**
 * 函数式接口（闭包）
 * @author 520
 *
 * @param <F>
 * @param <T>
 */
@FunctionalInterface
public interface Converter<F,T> {
	
	//接口中的方法默认会加上public abstract
	T convert(F from);	
	
	boolean equals(Object obj);//这个可以存在。。（普通接口可以存在。但这个是函数式接口。特例？）
	int hashCode();				//这个也可以存在。。（特例？）
	
	//only public, abstract, default, static and strictfp are permitted
	static boolean  fun1(String a) {//接口中的静态方法,编译后会加上public，不是public abstract
		return false;
	}
	
	default void fun2(){//默认编译后会加上public，不是public abstract
		
	}
}
