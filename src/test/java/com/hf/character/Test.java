package com.hf.character;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		/*byte b = -1;
		//char c = (char) b;
		System.out.println((int) (char) b);
		System.out.println((int) (char) (b & 0xff));*/
		
		test1();
	}
	
	public static  void test1() throws UnsupportedEncodingException{
		final String a="%E8%A3%99%E5%AD%90";
		String decode = URLDecoder.decode(a, "utf-8");
		System.out.println(decode);
	}
}
