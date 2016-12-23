package com.hf.thrift.client.nonblocking;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import com.hf.thrift.HelloWorld;
import com.hf.thrift.HelloWorld.AsyncClient.sayHello_call;

/** 
 * 客户端异步调用,服务端需使用TNonblockingServer ，THsHaServer 
 */  
public class HelloAsyncClient {  
    public static final String SERVER_IP = "127.0.0.1";  
    public static final int SERVER_PORT = 8080;  
    public static final int TIMEOUT = 30000;  
  
    public static void main(String[] args) throws TException, IOException, InterruptedException {  
        //异步调用管理器  
        TAsyncClientManager clientManager = new TAsyncClientManager();  
        //设置传输通道，调用非阻塞IO  
        TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);  
        // 协议要和服务端一致  
        //TProtocolFactory tprotocol = new TCompactProtocol.Factory();  
        TProtocolFactory tprotocol = new TBinaryProtocol.Factory();  
          
        HelloWorld.AsyncClient asyncClient = new HelloWorld.AsyncClient(tprotocol, clientManager, transport);  
        CountDownLatch latch = new CountDownLatch(1);  
        AsynCallback callBack = new AsynCallback(latch);  
        System.out.println("call method sayHello start ...");  
        // 调用服务  
        asyncClient.sayHello("jack", callBack);  
        System.out.println("call method sayHello .... end");  
        //等待完成异步调用  
        boolean wait = latch.await(30, TimeUnit.SECONDS);  
        System.out.println("latch.await =:" + wait);  
        
      /*//SSL客户端  
        TSSLTransportParameters parameters = new TSSLTransportParameters();  
        parameters.setTrustStore("../../.trustore", "thrift", "SunX509", "JKS");  
        TTransport tTransport = TSSLTransportFactory.getClientSocket("127.0.0.1", 8080, 3000, parameters); */
    }  
}
class AsynCallback implements AsyncMethodCallback<sayHello_call> {  
    private CountDownLatch latch;  
  
    public AsynCallback(CountDownLatch latch) {  
        this.latch = latch;  
    }  
  
    @Override  
    public void onComplete(sayHello_call response) {  
        System.out.println("onComplete");  
        try {  
            System.out.println("AsynCall result :" + response.getResult().toString());  
        } catch (TException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            latch.countDown();  
        }  
    }  
    @Override  
    public void onError(Exception exception) {  
        System.out.println("onError :" + exception.getMessage());  
        latch.countDown();  
    }  
}  
