package com.hf.ws.jdk;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface="com.hf.ws.jdk.IJdkWebService")
public class JdkWebServiceImpl implements IJdkWebService{

	@Override
	public String doSomething(@WebParam(name="value", targetNamespace = "http://hf.jdk.ws/", mode = WebParam.Mode.IN)String value) {
		return "doSomething"+value;
	}

}
