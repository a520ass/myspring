package com.hf.ws.jdk;

import javax.xml.ws.Endpoint;

import org.junit.Test;

import com.hf.ws.jdk.client.IJdkWebService;
import com.hf.ws.jdk.client.JdkWebServiceImplService;

public class JdkWsTest {
	
	/**
	 * 生成客户端代码
	 * wsimport -keep -p com.hf.ws.jdk.client http://localhost:8080/myspring/jdkWebService?wsdl
	 * 命令参数说明：
	　　-d:生成客户端执行类的class文件的存放目录
	　　-s:生成客户端执行类的源文件的存放目录
	　　-p:定义生成类的包名
	　　其他命令参数请参照：http://download-llnw.oracle.com/javase/6/docs/technotes/tools/share/wsimport.html
	 */
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/myspring/jdkWebService", new JdkWebServiceImpl());   
	}
	
	/**
	 * 测试客户端webservice调用
	 */
	@Test
	public void clientTest(){
		JdkWebServiceImplService jdkWebServiceImplService = new JdkWebServiceImplService();
		IJdkWebService iJdkWebService = jdkWebServiceImplService.getJdkWebServiceImplPort();
		String string = iJdkWebService.doSomething2("啦啦啦");
		System.out.println(string);
	}
}
