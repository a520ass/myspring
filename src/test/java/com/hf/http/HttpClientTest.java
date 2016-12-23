package com.hf.http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientTest {
	
	public static void main(String[] args) throws IOException, InterruptedException      
    {      
        HttpClient client = new HttpClient();         
        //设置代理服务器地址和端口           
        //使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https      
        HttpMethod method = new GetMethod("https://item.taobao.com/item.htm?spm=a230r.1.14.113.talSpK&id=535959464911&ns=1&abbucket=18#detail");       
        for (int i = 0; i < 100; i++) {
        	client.executeMethod(method); 
        	Thread.sleep(1000L);
		}
             
        //打印服务器返回的状态      
     System.out.println(method.getStatusLine());      
       //打印返回的信息      
     //System.out.println(method.getResponseBodyAsString());      
       //释放连接      
     method.releaseConnection();      
    }   
}
