package com.hf.thrift.server.nonblocking;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import com.hf.thrift.HelloWorld;
import com.hf.thrift.HelloWorldImpl;

/** 
 * 注册服务端 
 *  使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。 TNonblockingServer   
 */  
public class HelloTNonblockingServer {  
    // 注册端口  
    public static final int SERVER_PORT = 8080;  
  
    public static void main(String[] args) throws TException {  
        //处理器  
        TProcessor tprocessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldImpl());  
        // 传输通道 - 非阻塞方式    (服务端 传输通道 的父类是TServerTransport，客户端是TTransport)
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);  
        //异步IO，需要使用TFramedTransport，它将分块缓存读取。    
        TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);  
        tArgs.processor(tprocessor);  
        tArgs.transportFactory(new TFramedTransport.Factory());  
        //使用高密度二进制协议   
        tArgs.protocolFactory(new TCompactProtocol.Factory());  
        // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式  
        TServer server = new TNonblockingServer(tArgs);  
        System.out.println("HelloTNonblockingServer start....");  
        server.serve(); // 启动服务  
    }  
}  
