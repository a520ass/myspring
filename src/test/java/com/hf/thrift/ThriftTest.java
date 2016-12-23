package com.hf.thrift;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.hf.config.thrif.server.ThriftServerIpLocalNetworkResolve;
import com.hf.config.thrif.server.ThriftServerIpResolve;

import sun.net.util.IPAddressUtil;

public class ThriftTest {

	public static void main(String[] args) throws Exception {
		ThriftServerIpResolve thriftServerIpResolve = new ThriftServerIpLocalNetworkResolve();
		List<String> serverIp = thriftServerIpResolve.getServerIp();
		System.out.println(serverIp);
		serverIp.sort((String a,String b)->{
			int j=a.compareTo(b);
			return j;
			
		});
		/*serverIp.sort((String a,String b)->{
			int i=resolveIsInternalIp(a)-resolveIsInternalIp(b);
			return i;
		});*/
		
		System.out.println("排序后ip"+serverIp);
		//System.out.println(getExternalIP());
	}
	
	/**
	 * 解析是否是内网ip
	 * @param ip
	 * @return 1（A类） 2（B类） 3（C类） -1（其他）
	 */
	public static int resolveIsInternalIp(String ip) {
		byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
		return doResolveIsInternalIp(addr);
	}

	public static int doResolveIsInternalIp(byte[] addr) {
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		// 10.x.x.x/8
		final byte SECTION_1 = 0x0A;
		// 172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return 1; // A类 1
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return 2; // B类 2
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return 3; // C类 3
			}
		default:
			return -1; // 其他
		}
	}
	
	/**
	 * 获取外网地址
	 * 
	 * @param strUrl
	 * @return
	 */
	public static String getExternalIP() {
		String strUrl = "http://www.ip138.com/ip2city.asp";
		try {
			// 连接网页
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			String webContent = "";
			// 读取网页信息
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			// 网页信息
			webContent = sb.toString();
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");
			// 获取网页中 当前 的 外网IP
			webContent = webContent.substring(start, end);
			return webContent;

		} catch (Exception e) {
			e.printStackTrace();
			return "error open url:" + strUrl;
		}
	}
}
