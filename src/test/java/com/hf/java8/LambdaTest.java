package com.hf.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaTest {
	
	@FunctionalInterface
	interface MyInter{
        void hello();
       default String getValue(){
    	   return null;
       }
    }
	
	public static void main(String[] args) {
		MyInter in= () -> {System.out.println("Hello World");};
		in.hello();
		
		List<String> categories = Arrays.asList("Java","Dot Net","Oracle","Excel");
        
		// Java 8 Lambda For loop
		categories.stream().forEach(categorie -> System.out.println(categorie));
	
		Collections.sort(categories, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});
		
		Collections.sort(categories,(String a, String b)->{
			return b.compareTo(a);
		});
		
		Collections.sort(categories,( a,  b)->{
			return b.compareTo(a);
		});
		
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		converter.convert("何");
		
		//::引用一个静态方法
		Converter<String, Integer> converter1=Integer::valueOf;
		
		//Converter<int, Integer> converter1=Integer::valueOf; 编译错误
		//Converter<Integer, Integer> converter1=Integer::valueOf; 正确的用法
		
		//::引用一个对象的方法
		Something
		 something = new Something();

		Converter<String, String> converter2 = something::startsWith;

		String converted = converter2.convert("Java");

		System.out.println(converted); 
	}
	
}
class Something
{

   String startsWith(String s) {

       return String.valueOf(s.charAt(0));

   }

}