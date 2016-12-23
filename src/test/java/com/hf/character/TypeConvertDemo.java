package com.hf.character;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class TypeConvertDemo {
	
	@Test
	public void test1(){
		char c='-';
		System.out.println((int)c);
	}
	
	@Test
	public void test2(){
		byte b1=-128;//
		Byte b2=-128;//Byte.valueOf(b)
		
		byte b3=new Byte("-128");
	}
	
	@Test
	public void test3(){
		byte b=-1;
		/**
		 * Integer.toHexString是输出一个值的十六进制字符串
		 * Integer.toBinaryString是输出一个值的二进制字符串
		 * 
		 */
		//直接由byte转int的话。由于符号位扩展。得到得字符串不是我们所要的
		String hexStringerror = Integer.toHexString(b);
		String binaryStringerror = Integer.toBinaryString(b);
		System.out.println(hexStringerror);
		System.out.println(binaryStringerror);
		
		//byte和0xff 相与操作（先转换成int，再相与）注意。相与后的值和之前的值已经不相等了
		//即	11111111111111111111111111111111 (byte的-1转成int)		&
		//	00000000000000000000000011111111 (0xff)		
		//	00000000000000000000000011111111 (int型 十进制的255)
		String hexStringright = Integer.toHexString(b&0xff);
		String binaryStringright = Integer.toBinaryString(b&0xff);
		System.out.println(hexStringright);
		System.out.println(binaryStringright);
		
	}
	
	@Test
	public void stringToBytes(){
		String mString="ﾀ";
		//1、默认使用utf-8编码
		byte[] bytes = mString.getBytes();
		System.out.println(bytes);
	}
	
	@Test
	public void bytesToString(){
		byte[] bytes={-17,-66,-128};
		//1、默认使用utf-8编码
		String String1 = new String(bytes);

		//2、指定其他编码方式
		String String2 = new String(bytes, StandardCharsets.US_ASCII);
		String String3 = new String(bytes, StandardCharsets.ISO_8859_1);
	}
	
	@Test
	public void stringToChars(){
		String s = "SSSSSSS";
		char[] ss = s.toCharArray();
		System.out.println(ss);
	}
	
	@Test
	public void charsToString(){
		//Java中，char类型虽然采用UTF16编码，但它只占据两个byte，因此无法表示一些特殊字符
		char[] chars4 = {0x0001, 0b1111111111111111, 0xf0,0x00f0, 'a', 0x4E25,0xFFFF,'\u950B'};
		String string4 = new String(chars4);
		System.out.println(string4);
	}
	
	@Test
	public void stringToInt(){
		String string9 = "严";
		int i9 = Integer.parseInt(string9);
	}
	
	
	
	@Test
	public void intToString(){
		int i = 10;
		//转化
		String string9 = String.valueOf(i);
		//2、直接使用字符串加上i，java会自动转化
		string9 = "" + i;
	}
}
