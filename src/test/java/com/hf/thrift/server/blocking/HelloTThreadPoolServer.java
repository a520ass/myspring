package com.hf.thrift.server.blocking;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.hf.thrift.HelloWorld;
import com.hf.thrift.HelloWorldImpl;

/** 
 * 注册服务端 
 *  线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。 TThreadPoolServer  
 */  
public class HelloTThreadPoolServer {  
    // 注册端口  
    public static final int SERVER_PORT = 8080;  
  
    public static void main(String[] args) throws TException {  
        TProcessor tprocessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldImpl());  
        // 阻塞IO  
        TServerSocket serverTransport = new TServerSocket(SERVER_PORT);  
        //多线程服务模型  
        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);  
        tArgs.processor(tprocessor);  
        //客户端协议要一致  
        tArgs.protocolFactory(new TBinaryProtocol.Factory());  
         // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。  
        TServer server = new TThreadPoolServer(tArgs);  
        System.out.println("HelloTThreadPoolServer start....");  
        server.serve(); // 启动服务  
    }  
}  
