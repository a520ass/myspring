package com.hf.web;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.thrift.ThriftHttpProxyFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hf.config.rpc.Account;
import com.hf.config.rpc.AccountService;
import com.hf.thrift.HelloWorld;

@RestController
@RequestMapping("/rpc")
public class RpcController {
	
	@Autowired HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean;
	@Autowired RmiProxyFactoryBean rmiProxyFactoryBean;
	@Autowired ThriftHttpProxyFactoryBean thriftHttpProxyFactoryBean;
	
	@RequestMapping("/getByHttpInvoker")
	public List<Account> getByHttpInvoker(){
		AccountService a=(AccountService)httpInvokerProxyFactoryBean.getObject();
		List<Account> list = a.getAccounts("hefeng-httpinvoker");
		return list;
	}
	
	@RequestMapping("/getByRmi")
	public List<Account> getByRmi(){
		AccountService a=(AccountService)rmiProxyFactoryBean.getObject();
		List<Account> list = a.getAccounts("hefeng-rmi");
		return list;
	}
	
	@RequestMapping("/getByThrift")
	public String getByThrift() throws TException{
		HelloWorld.Iface a=(HelloWorld.Iface)thriftHttpProxyFactoryBean.getObject();
		String string = a.sayHello("hefeng-thrift");
		return string;
	}
}
