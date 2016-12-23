package org.springframework.remoting.thrift;

import java.lang.reflect.Constructor;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.apache.http.client.HttpClient;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;

public class ThriftProxyFactory implements ObjectFactory {

	private HttpClient httpClient;

	private final ClassLoader _loader;

	public ThriftProxyFactory() {
		this(Thread.currentThread().getContextClassLoader());
	}

	public ThriftProxyFactory(ClassLoader loader) {
		_loader = loader;
	}

	public Object create(Class<?> serviceInterface, String serviceUrl)
			throws Exception {
		THttpClient thc = new THttpClient(serviceUrl, httpClient);
		TProtocol loPFactory = new TCompactProtocol(thc);
		Class<?> client = Class.forName(serviceInterface.getName().replace(
				"$Iface", "$Client"));
		Constructor<?> con = client.getConstructor(TProtocol.class);
		return con.newInstance(loPFactory);
	}

	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx,
			Hashtable<?, ?> environment) throws Exception {
		Reference ref = (Reference) obj;
		String api = null;
		String url = null;
		for (int i = 0; i < ref.size(); i++) {
			RefAddr addr = ref.get(i);

			String type = addr.getType();
			String value = (String) addr.getContent();

			if (type.equals("type"))
				api = value;
			else if (type.equals("url"))
				url = value;
		}

		if (url == null)
			throw new NamingException(
					"`url' must be configured for ThriftProxyFactory.");
		// XXX: could use meta protocol to grab this
		if (api == null)
			throw new NamingException(
					"`type' must be configured for ThriftProxyFactory.");
		Class<?> apiClass = Class.forName(api, false, _loader);
		return create(apiClass, url);
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
