package com.hf.java8;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

public class Base64Test {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s="Hello world";
		byte[] src=s.getBytes(Charset.forName("UTF-8"));
		String string = Base64.getEncoder().encodeToString(src);
		System.out.println(string);
		byte[] decode = Base64.getDecoder().decode(string);
		System.out.println(new String(decode, "UTF-8"));
	}
}
