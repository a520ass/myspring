package com.hf.thrift.client.nonblocking;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.hf.thrift.HelloWorld;

/** 
 * 客户端调用HelloTNonblockingServer,HelloTHsHaServer 
 * 非阻塞 
 */  
public class HelloNonblockingClient {  
    public static final String SERVER_IP = "127.0.0.1";  
    public static final int SERVER_PORT = 8080;  
    public static final int TIMEOUT = 30000;  
  
    public static void main(String[] args) throws TException {  
        //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送    
        TTransport transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));  
        // 协议要和服务端一致  
        //HelloTNonblockingServer  
        ////使用高密度二进制协议   
        TProtocol protocol = new TCompactProtocol(transport);  
        //HelloTHsHaServer  
        ////使用二进制协议   
        //TProtocol protocol = new TBinaryProtocol(transport);  
        HelloWorld.Client client = new HelloWorld.Client(protocol);  
        transport.open();  
        String result = client.sayHello("jack");  
        System.out.println("result : " + result);  
        //关闭资源  
        transport.close();  
    }  
}  
