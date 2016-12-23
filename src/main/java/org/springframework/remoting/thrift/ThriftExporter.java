package org.springframework.remoting.thrift;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.util.Assert;

import com.hf.config.thrif.ThriftException;

public class ThriftExporter extends RemoteExporter implements InitializingBean {

	public static final String CONTENT_TYPE_THRIFT = "application/x-thrift";
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private TProcessor processor;
	private TProtocolFactory inProtocolFactory;
	private TProtocolFactory outProtocolFactory;
	private Collection<Map.Entry<String, String>> customHeaders;

	@Override
	public void afterPropertiesSet() throws Exception {
		Class<?> Processor = Class.forName(getServiceInterface().getName()
				.replace("$Iface", "$Processor"));
		Constructor<?> con = Processor.getConstructor(getServiceInterface());
		TProcessor processor = (TProcessor) con.newInstance(getService());

		this.processor = processor;
		this.inProtocolFactory = new TCompactProtocol.Factory();
		this.outProtocolFactory = new TCompactProtocol.Factory();
		this.customHeaders = new ArrayList<Map.Entry<String, String>>();
		/*CuratorFramework zkClient = null;
		//注册当前节点的ip信息
		ThriftServerAddressRegister register=new ThriftServerAddressRegisterZookeeper(zkClient);
		ThriftServerIpResolve resolve = new ThriftServerIpLocalNetworkResolve();
		String ip = selectOptimalIP(resolve.getServerIp());
		String hostname = ip + ":httpport:1";
		register.register(getServiceInterface().getName(), "1.0.0", hostname);*/
	}
	
	private String selectOptimalIP(List<String> serverIPlist) {
    	serverIPlist.sort((String a,String b)->{
			int j=a.compareTo(b);
			return j;
		});
    	return serverIPlist.get(0);
	}

	public void invoke(InputStream inputStream, OutputStream outputStream) {
		Assert.notNull(this.processor,
				"Thrift processor has not been initialized");
		Assert.notNull(this.inProtocolFactory,
				"Thrift inProtocolFactory has not been initialized");
		Assert.notNull(this.outProtocolFactory,
				"Thrift outProtocolFactory has not been initialized");
		doInvoke(inputStream, outputStream);
	}

	protected void doInvoke(InputStream inputStream, OutputStream outputStream) {
		try {
			TTransport transport = new TIOStreamTransport(inputStream,
					outputStream);
			TProtocol inProtocol = inProtocolFactory.getProtocol(transport);
			TProtocol outProtocol = outProtocolFactory.getProtocol(transport);
			processor.process(inProtocol, outProtocol);
			outputStream.flush();
		} catch (TException e) {
			logger.error("error:{}", e);
			throw new ThriftException("thrift processor error : {}", e);
		} catch (IOException e) {
			logger.error("error:{}", e);
			throw new ThriftException("io error : {}", e);
		}
	}

	public void addCustomHeader(final String key, final String value) {
		this.customHeaders.add(new Map.Entry<String, String>() {
			public String getKey() {
				return key;
			}

			public String getValue() {
				return value;
			}

			public String setValue(String value) {
				return null;
			}
		});
	}

	public void setCustomHeaders(Collection<Map.Entry<String, String>> headers) {
		this.customHeaders.clear();
		this.customHeaders.addAll(headers);
	}

}
