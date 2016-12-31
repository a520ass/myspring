package com.hf.http.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class BaiduTest {

	static String SendGet(String url) {
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try {
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null) {
				// 遍历抓取到的每一行并将其存储到result里面
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	static String SendGetByHttpClient(String url) throws HttpException, IOException {
		// 定义一个字符串用来存储网页内容
		String result ;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
		httpClient.executeMethod(getMethod);
		BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));  
		StringBuffer stringBuffer = new StringBuffer();  
		String str = "";  
		while((str = reader.readLine())!=null){  
		    stringBuffer.append(str);  
		}  
		result = stringBuffer.toString();
		return result;
	}

	static String RegexString(String targetStr, String patternStr) {
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容 // 相当于埋好了陷阱匹配的地方就会掉下去
		Pattern pattern = Pattern.compile(patternStr);
		// 定义一个matcher用来做匹配

		Matcher matcher = pattern.matcher(targetStr);
		// 如果找到了
		if (matcher.find()) {
			// 打印出结果
			return matcher.group(1);
		}

		return "";
	}

	public static void main(String[] args) throws HttpException, IOException {
		// 定义即将访问的链接
		String url = "http://www.baidu.com";
		// 访问链接并获取页面内容
		String result = SendGetByHttpClient(url);
		// // 使用正则匹配图片的src内容
		String imgSrc = RegexString(result, "src=\"(.+?)\""); //
		// 打印结果
		System.out.println(imgSrc);
	}
}
