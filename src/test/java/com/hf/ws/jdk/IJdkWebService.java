package com.hf.ws.jdk;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 接口方法默认就是public abstract
 * @author 520
 *
 */
@WebService
public interface IJdkWebService {
	
	String doSomething(String value);
	
	/**
	 * 这里的default不是值访问修饰符  而是jdk1.8中新增的功能，用于在接口中写具体的实现
	 * @param value
	 * @return
	 */
	default String doSomething2(@WebParam(name="value", targetNamespace = "http://hf.jdk.ws/", mode = WebParam.Mode.IN)String value){
		return "doSomething2"+value;
	}
}
