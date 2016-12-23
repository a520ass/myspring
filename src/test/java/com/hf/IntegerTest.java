package com.hf;

public class IntegerTest {
	
	public static void main(String[] args) {
		Integer i=5;	//Integer i= Integer.valueOf(5); 编译后
		change(i);
		System.out.println(i);
	}
	
	public static void change(Integer i){
		//i=3;
		//i=Integer.valueOf(3);
		i=new Integer(3);
	}
}
