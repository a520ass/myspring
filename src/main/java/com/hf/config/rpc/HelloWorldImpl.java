package com.hf.config.rpc;

import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorld.Iface{

	@Override
	public String sayHello(String username) throws TException {
		return "hello world, "+username;
	}
	
}
