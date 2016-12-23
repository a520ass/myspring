package com.hf.thrift.server.nonblocking;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import com.hf.thrift.HelloWorld;
import com.hf.thrift.HelloWorldImpl;

/** 
 * 注册服务端 半同步半异步的服务端模型，需要指定为： TFramedTransport 数据传输的方式。 THsHaServer 
 * 非阻塞 
 */  
public class HelloTHsHaServer {  
    // 注册端口  
    public static final int SERVER_PORT = 8080;  
    
    public static void main(String[] args) throws TException {  
        TProcessor tprocessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldImpl());  
        // 传输通道 - 非阻塞方式    
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);  
        //半同步半异步  
        THsHaServer.Args tArgs = new THsHaServer.Args(serverTransport);  
        tArgs.processor(tprocessor);  
        tArgs.transportFactory(new TFramedTransport.Factory());  
        //二进制协议  
        tArgs.protocolFactory(new TBinaryProtocol.Factory());  
        // 半同步半异步的服务模型  
        TServer server = new THsHaServer(tArgs);  
        System.out.println("HelloTHsHaServer start....");  
        server.serve(); // 启动服务  
        
       /* //SSL服务端  
        TSSLTransportParameters parameters = new TSSLTransportParameters();  
        //keystore文件  密码  
        parameters.setKeyStore("../../.keystore", "thrift");  
        TServerTransport serverTransport = TSSLTransportFactory.getServerSocket(8080, 3000, null, parameters);*/
    }  
}  
