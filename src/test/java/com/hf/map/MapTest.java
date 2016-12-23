package com.hf.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {
	
	public static void main(String[] args) {
		new MapTest().mapTest1();
	}
	
	@Test
	public void mapTest1(){
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("1", "何锋");
		String hf="何锋";
		System.out.println(hf==hashMap.get("1"));//true
	}
}
