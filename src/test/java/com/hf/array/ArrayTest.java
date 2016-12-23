package com.hf.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ArrayTest {
	
	@Test
	public void stringArrayTest1(){
		String[] s = {"1","2"};//这种形式叫数组初始化式（Array Initializer），只能用在声明同时赋值的情况下。
		String[] s1 = {new String("1"),"2"};
		
		//这里编译后。变成了数组初始化式，也就是说，可以变成数组初始化式的。会自动转换成。String[] s2 = { new String("1"), "2" };
		String[] s2 = new String[]{new String("1"),"2"};
		//Integer[] i={1,2,3};
	}
	
	/*@Test
	public String[] stringArrayTest2(){
		
		//这里由于不是赋值形式。所以不能写成数组初始化式，编译后也是这样
		return new String[] {"1","2"};
	}*/
	
	@Test
	public void stringArrayTest3(){
		//这种string数组。先按照string数组的创建规则来判断，  str1和str2写法没有任何区别。
		//首先。str1创建三个string变量。由于string可以使用"str"这种方法创建对象。
		//先在常量池里创建三个string对象。并把地址指向str1
		//然后到str2的时候。由于"a","b","c"这三个已经在常量池了，这里直接把地址指向str2.
		//所以str1和str2里面的string的内存地址是一样的。但是str1数组和str2数组的内存地址是不同的
		
		//str3 。一样的。先在常量池里面找，如果找不到，就先创建。找到就不做任何操作（第一步）
		//由于string创建的过程，加了new String(String str) （除了"c"）还会在堆里面创建string对象。并把堆的内存地址指向
		//str3的数组中，(new String("b").intern()  这个调用了此方法后。会返回常量池的地址)所以str3和str2，str1里面的string的内存地址（a）是肯定不同的
		String[] str1 = new String[]{"a","b","c"};
		
        String[] str2={"a","b","c"};
 
        String[] str3={new String("a"),new String("b").intern(),"c"};
        
        System.out.println(str1[0]=="a");
        System.out.println(str2[0]=="a");
        System.out.println(str3[0]=="a");
 
        System.out.println(str1[1]=="b");
        System.out.println(str2[1]=="b");
        System.out.println(str3[1]=="b");
 
        System.out.println(str1[2]=="c");
        System.out.println(str2[2]=="c");
        System.out.println(str3[2]=="c");
        
        System.out.println(str1==str2);
	}
	
	@Test
	public void test4(){
		String[] s={"何锋","陈玲"};
		//System.out.println(s.length);
		
		//List<String> list=Arrays.asList(s);这个list不能reomve add 
		//见java.util.Arrays.ArrayList.ArrayList<T>(T[]) 这个Arraylist没有重写父类的方法
		List<String> list = new ArrayList<String>(Arrays.asList(s));
		
		list.remove(1);
		
		String[] array = list.toArray(new String[0]);
		
	}
	
	@Test
	public void test5(){
		int index=1;
		String[] s={"何锋","陈玲"};
		
		s=ArrayUtils.add(s, "小贝贝");
		
		s=ArrayUtils.add(s, 1, "index为一");
		
		s=ArrayUtils.remove(s, index+1);
		
		//s[index]=null;
		
		String[] dest=new String[s.length-1];
		
		System.arraycopy(s, index+1, dest, index, s.length-index-1);
		
		
	}
}
