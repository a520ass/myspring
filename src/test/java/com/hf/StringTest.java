package com.hf;

import org.junit.Test;

public class StringTest {
	
	public static void main(String[] args) {
		String s1 = "Hello";	//这里
		String s2 = new String("Hello").intern();
		System.out.println(s1 == s2);
	}
	
	@Test
	public void test1(){
		String str="abc";
		//String a="abc";
		//String b="abc";
		String a1="a";
		String a2="b";
		String a3=a1+"b";
		String a="ab"+"cd";	//编译之后 String a = "abcd";
	}
}
 /*class Spring{
	public static void main(String[] args) {
		System.out.println("444");
	}
}
*/